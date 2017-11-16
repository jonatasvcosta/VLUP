import werkzeug.exceptions as exceptions
from flask import Blueprint, request, jsonify
from common.database import DBSession

from indexing import available_languages
from indexing.corpus import DbCorpus
from indexing.similarity import SimilarityLSA
from indexing.preprocessing import PreProcessor

bp = Blueprint('synonymous', __name__)


@bp.route('/')
def query():
    synonymous_element = as_synonymous_element(request.args)
    original_text, original_language, final_language = synonymous_element.original_text, synonymous_element.original_language, synonymous_element.final_language

    """
    Algumas libs do Android enviam pt-br ao invés de pt
    """
    if(original_language == 'pt-br'): original_language = 'pt'
    if(final_language == 'pt-br'): final_language = 'pt'

    synonymous_service = SynonymousService()
    synonymous_element = synonymous_service.get_synonymous(original_text, original_language, final_language)
    return jsonify(synonymous_element.serialize())


from itertools import chain
from .translation import TranslatedElement, TranslationService
from nltk.corpus import wordnet

class SynonymousService():
    def get_synonymous(self, original_text, original_language, final_language):
        """"
        A lib que gera os sinônimos funciona a partir da wordnet, portanto apenas em inglês.
        Portanto obtemos os sinônimos em inglês e depois os convertermos para o idioma fim.
        """
        translation_service = TranslationService()
        if(original_language == 'en'):
             synonymous_list_original_english = wordnet.synsets(original_text)
        else:
            translated_element = translation_service.translate(original_text, original_language, 'en')
            synonymous_list_original_english = wordnet.synsets(translated_element.translated_text)

        synonymous_list_original_english = list(set(chain.from_iterable([word.lemma_names() for word in synonymous_list_original_english])))

        for i in range(len(synonymous_list_original_english)):
            synonymous_list_original_english[i] = " ".join(str(synonymous_list_original_english[i]).split('_'))

        synonymous_list_original_language = []
        synonymous_list_final_language = []

        if(original_language == 'en'):
            synonymous_list_original_language = synonymous_list_original_english
        else:
            for word in synonymous_list_original_english:
                translated_element = translation_service.translate(" ".join(str(item) for item in word.split('_')), 'en', original_language)
                synonymous_list_original_language.append(translated_element.translated_text)

        if(final_language == 'en'):
            synonymous_list_final_language = synonymous_list_original_english
        else:
            for word in synonymous_list_original_english:
                translated_element = translation_service.translate(" ".join(str(item) for item in word.split('_')), 'en', final_language)
                synonymous_list_final_language.append(translated_element.translated_text)

        return SynonymousElement(original_text, original_language, final_language, synonymous_list_original_language, synonymous_list_final_language)


class SynonymousElement(object):
    """Synonymous result object

    :param original_language: source language
    :param final_language: destination language
    :param original_text: original text
    :param synonymous_list_original_language: list of synonymous written in source language
    :param synonymous_list_final_language: list of synonymous written in destination language
    """

    def __init__(self, original_text, original_language, final_language,
                 synonymous_list_original_language, synonymous_list_final_language):
        self.original_language = original_language
        self.final_language = final_language
        self.original_text = original_text
        self.synonymous_list_original_language = synonymous_list_original_language
        self.synonymous_list_final_language = synonymous_list_final_language

    def serialize(self):
        return {
            "original_text": self.original_text,
            "original_language": self.original_language,
            "final_language": self.final_language,
            "synonymous_list_original_language": self.synonymous_list_original_language,
            "synonymous_list_final_language": self.synonymous_list_final_language,
        }


def as_synonymous_element(dct):
    return SynonymousElement(dct['original_text'], dct['original_language'], dct['final_language'], "", "")
