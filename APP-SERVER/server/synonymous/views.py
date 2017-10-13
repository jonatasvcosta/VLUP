from django.http import HttpResponse
from rest_framework.renderers import JSONRenderer
from rest_framework.decorators import api_view
import json

from synonymous.services.synonymous import SynonymousService
from synonymous.serializers import SynonymousElementSerializer
from synonymous.models import as_synonymous_element


@api_view(['POST'])
def index(request):
    if request.method == 'POST':
        body_dict = json.loads(request.body)
        synonymous_element = as_synonymous_element(body_dict)
        original_text, original_language, final_language = synonymous_element.original_text, synonymous_element.original_language, synonymous_element.final_language
        synonymous_service = SynonymousService()
        synonymous_element = synonymous_service.get_synonymous(original_text, original_language, final_language)    
        serializer = SynonymousElementSerializer(synonymous_element)
        json_final = JSONRenderer().render(serializer.data)

    return HttpResponse(json_final)