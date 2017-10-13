# -*- coding: utf-8 -*-

from itertools import chain
from translation.models import TranslatedElement
from translation.services.translation import TranslationService
from nltk.corpus import wordnet
from synonymous.models import SynonymousElement

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
