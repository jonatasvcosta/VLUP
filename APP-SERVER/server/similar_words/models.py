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
    
def as_similar_words_element(dct):
    return SimilarWordsElement(dct['original_text'], dct['original_language'], dct['final_language'], "", "")