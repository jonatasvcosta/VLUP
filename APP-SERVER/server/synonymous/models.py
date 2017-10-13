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


def as_synonymous_element(dct):
    return SynonymousElement(dct['original_text'], dct['original_language'], dct['final_language'], "", "")
