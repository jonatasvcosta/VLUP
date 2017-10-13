from rest_framework import serializers


class SynonymousElementSerializer(serializers.Serializer):
    original_text = serializers.CharField(max_length=200)
    original_language = serializers.CharField(max_length=4)
    final_language = serializers.CharField(max_length=4)
    synonymous_list_original_language = serializers.CharField(max_length=200)
    synonymous_list_final_language = serializers.CharField(max_length=200)

