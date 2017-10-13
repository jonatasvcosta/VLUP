from itertools import chain
from similar_words.models import SimilarWordsElement
from translation.services.translation import TranslationService
from word_forms.word_forms import get_word_forms


class SimilarWordsService():

    def get_similar_words(self, original_text, original_language, final_language):
        '''
        A lib que gera as palavras parecidas funciona a partir da wordnet, portanto apenas em inglês.
        Portanto obtemos as palavras parecidas em inglês e depois as convertermos para o idioma fim.
        '''
        if(final_language == 'en'):
            similar_words_english = get_word_forms(original_text)
        else:        
            translation_service = TranslationService()
            translated_element = translation_service.translate(original_text, original_language, 'en')
            similar_words_english = get_word_forms(translated_element.translated_text)  

        similar_words_final_language = []

        for word in similar_words_english:
            translated_element = translation_service.translate(word, 'en', final_language)
            similar_words_final_language.append(translated_element.translated_text)

        return SimilarWordsElement(original_text, original_language, final_language, similar_words_english, similar_words_final_language)
