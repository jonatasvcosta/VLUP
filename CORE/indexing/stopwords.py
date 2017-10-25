import os
from . import PathUtility

class StopWords(object):
	language = None
	path = None

	def __init__(self, language):
		self.language = language
		self.path = PathUtility.STOP_WORDS(language)

	def __iter__(self):
		with open(self.path, 'r') as file:
			for line in file:
				yield line.strip()
