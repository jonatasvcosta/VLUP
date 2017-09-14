from django.http import HttpResponse
from translation.services.translation import TranslationService
from translation.serializers import TranslatedElementSerializer
from rest_framework.renderers import JSONRenderer
from rest_framework.decorators import api_view
from django.utils.six import BytesIO
from rest_framework.parsers import JSONParser
import json      
from translation.models import *

@api_view(['POST'])
def index(request):
    print_request(request)
    if request.method == 'POST':
        body_dict = json.loads(request.body)
        translated_element = as_translated_element(body_dict)
        original_text, original_language, final_language = translated_element.original_text, translated_element.original_language, translated_element.final_language
        translation_service = TranslationService()
        translated_element = translation_service.translate(original_text, original_language, final_language)    
        serializer = TranslatedElementSerializer(translated_element)
        json_final = JSONRenderer().render(serializer.data)

    return HttpResponse(json_final)

def print_request(req):
    print('METODO {method} BODY {body}'.format(
        method=req.method,
        body=req.body,  
    ))

def dumpclean(obj):
    if type(obj) == dict:
        for k, v in obj.items():
            if hasattr(v, '__iter__'):
                print (k)
                dumpclean(v)
            else:
                print('%s : %s' % (k, v))
    elif type(obj) == list:
        for v in obj:
            if hasattr(v, '__iter__'):
                dumpclean(v)
            else:
                print(v)
    else:
        print(obj)