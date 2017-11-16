import werkzeug.exceptions as exceptions
from flask import Blueprint, request, jsonify
from common.database import DBSession



bp = Blueprint('similar_words', __name__)


@bp.route('/')
def query():
    similar_words_element = as_similar_words_element(request.args)
    original_text, original_language, final_language = similar_words_element.original_text, similar_words_element.original_language, similar_words_element.final_language

    """
    Algumas libs do Android enviam pt-br ao invés de pt
    """
    if(original_language == 'pt-br'): original_language = 'pt'
    if(final_language == 'pt-br'): final_language  = 'pt'

    similar_words_service = SimilarWordsService()
    similar_words_element = similar_words_service.get_similar_words(original_text, original_language, final_language)

    return jsonify(similar_words_element.serialize())


from .translation import TranslationService
from word_forms.word_forms import get_word_forms


class SimilarWordsService():

    def get_similar_words(self, original_text, original_language, final_language):
        '''
        A lib que gera as palavras parecidas funciona a partir da wordnet, portanto apenas em inglês.
        Portanto obtemos as palavras parecidas em inglês e depois as convertermos para o idioma fim.
        '''
        translation_service = TranslationService()
        if(original_language == 'en'):
            similar_words_english = get_word_forms(original_text)
        else:
            translated_element = translation_service.translate(original_text, original_language, 'en')
            similar_words_english = get_word_forms(translated_element.translated_text)

        similar_words_list_original_language, similar_words_list_final_language= {}, {}
        for type_word, words in similar_words_english.items():
            temp_original_language, temp_final_language= [], []
            for word in words:
                if(original_language != 'en'):
                    original_element_translated = translation_service.translate(word, 'en', original_language)
                    temp_original_language.append(original_element_translated.translated_text)
                else:
                    temp_original_language.append(word)

                if(final_language != 'en'):
                    final_element_translated = translation_service.translate(word, 'en', final_language)
                    temp_final_language.append(final_element_translated.translated_text)
                else:
                    temp_final_language.append(word)

            if(temp_original_language):
                similar_words_list_original_language[type_word] = temp_original_language
            if(temp_final_language):
                similar_words_list_final_language[type_word] = temp_final_language

        return SimilarWordsElement(original_text, original_language, final_language, similar_words_list_original_language, similar_words_list_final_language)


class SimilarWordsElement(object):
    """Similar words result object

    :param original_language: source language
    :param final_language: destination language
    :param original_text: original text
    :param similar_words_english: list of similar words written in english
    :param similar_words_final_language: list of similar words written in destination language
    """

    def __init__(self, original_text, original_language, final_language, similar_words_english, similar_words_final_language):
        self.original_language = original_language
        self.final_language = final_language
        self.original_text = original_text
        self.similar_words_english = similar_words_english
        self.similar_words_final_language = similar_words_final_language

    def serialize(self):
        return {
            "original_text": self.original_text,
            "original_language": self.original_language,
            "final_language": self.final_language,
            "similar_words_english": self.similar_words_english,
            "similar_words_final_language": self.similar_words_final_language,
        }



def as_similar_words_element(dct):
    return SimilarWordsElement(dct['original_text'], dct['original_language'], dct['final_language'], "", "")
