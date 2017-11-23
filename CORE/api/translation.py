import werkzeug.exceptions as exceptions
from flask import Blueprint, request, jsonify
from common.database import DBSession

bp = Blueprint('translation', __name__)


@bp.route('/')
def query():
    translated_element = as_translated_element(request.args)
    original_text, original_language, final_language = translated_element.original_text, translated_element.original_language, translated_element.final_language

    """
    Algumas libs do Android enviam pt-br ao invés de pt
    """
    if(original_language == 'pt-br'): original_language = 'pt'
    if(final_language == 'pt-br'): final_language = 'pt'

    translation_service = TranslationService()
    translated_element = translation_service.translate(original_text, original_language, final_language)
    return jsonify(translated_element.serialize())


from googletrans import Translator
from googletrans.models import Translated

class TranslationService():

    def __init__(self):
        self.translator = Translator()

    def translate(self, original_text, original_language, final_language):
        translated = self.translator.translate(original_text, src=original_language, dest=final_language)

        return TranslatedElement(original_text, original_language, final_language, translated.text)


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

    def serialize(self):
        return {
            "original_text": self.original_text,
            "original_language": self.original_language,
            "final_language": self.final_language,
            "translated_text": self.translated_text,
        }

def as_translated_element(dct):
    return TranslatedElement(dct['original_text'], dct['original_language'], dct['final_language'], "")
