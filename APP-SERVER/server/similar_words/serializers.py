from rest_framework import serializers
from similar_words.models import SimilarWordsElement

class SimilarWordsElementSerializer(serializers.Serializer):
    original_text = serializers.CharField(max_length=200)
    original_language = serializers.CharField(max_length=5)
    final_language = serializers.CharField(max_length=5)
    similar_words_english = serializers.CharField(max_length=200)
    similar_words_final_language = serializers.CharField(max_length=200)
