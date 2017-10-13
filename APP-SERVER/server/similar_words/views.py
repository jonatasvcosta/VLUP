from django.http import HttpResponse
from rest_framework.renderers import JSONRenderer
from rest_framework.decorators import api_view
from django.utils.six import BytesIO
from rest_framework.parsers import JSONParser
import json

from similar_words.services.similar_words import SimilarWordsService
from similar_words.serializers import SimilarWordsElementSerializer
from similar_words.models import *

@api_view(['POST'])
def index(request):
    if request.method == 'POST':
        body_dict = json.loads(request.body)
        similar_words_element = as_similar_words_element(body_dict)
        original_text, original_language, final_language = similar_words_element.original_text, similar_words_element.original_language, similar_words_element.final_language
        
        """
        Algumas libs do Android enviam pt-br ao invés de pt
        """
        if(original_language == 'pt-br'): original_language = 'pt'
        if(final_language == 'pt-br'): final_language  = 'pt'
        
        similar_words_service = SimilarWordsService()
        similar_words_element = similar_words_service.get_similar_words(original_text, original_language, final_language)    
        serializer = SimilarWordsElementSerializer(similar_words_element)
        json_final = JSONRenderer().render(serializer.data)

    return HttpResponse(json_final)