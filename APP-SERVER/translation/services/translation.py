from translation.models import TranslatedElement
from googletrans import Translator
from googletrans.models import Translated

class TranslationService():

    def __init__(self):
        self.translator = Translator()

    def translate(self, original_text, original_language, final_language):
        translated = self.translator.translate(original_text, src=original_language, dest=final_language)
        
        return TranslatedElement(original_text, original_language, final_language, translated.text)

