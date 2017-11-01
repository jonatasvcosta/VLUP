import gensim


class PreProcessor(object):
    iterator = None

    def __init__(self, iterator):
        self.iterator = iterator

    def __iter__(self):
        for item in self.iterator:
            yield PreProcessor.run(item)

    @staticmethod
    def run(text):
        return gensim.utils.simple_preprocess(text,
                                              deacc=True,
                                              min_len=2,
                                              max_len=25)
