from rest_framework import serializers
from translation.models import TranslatedElement

class TranslatedElementSerializer(serializers.Serializer):
    original_text = serializers.CharField(max_length=200)
    original_language = serializers.CharField(max_length=2)
    final_language = serializers.CharField(max_length=2)
    translated_text = serializers.CharField(max_length=500)
