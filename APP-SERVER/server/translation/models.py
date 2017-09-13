class TranslatedElement(object):
    """Translate result object

    :param original_language: source langauge
    :param final_language: destination language
    :param original_text: original text
    :param translated_text: translated text
    """

    def __init__(self, original_text, original_language, final_language, translated_text):
        self.original_language = original_language
        self.final_language = final_language
        self.original_text = original_text
        self.translated_text = translated_text
    
def as_translated_element(dct):
    return TranslatedElement(dct['original_text'], dct['original_language'], dct['final_language'], "")
