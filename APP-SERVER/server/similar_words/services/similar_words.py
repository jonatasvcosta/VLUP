from itertools import chain
from similar_words.models import SimilarWordsElement
from translation.services.translation import TranslationService
from word_forms.word_forms import get_word_forms
import json


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
