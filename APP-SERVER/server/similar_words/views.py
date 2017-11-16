import requests
import coreapi
import coreschema
from rest_framework import viewsets, authentication
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.schemas import ManualSchema


class SimilarWordsCore(APIView):
    """
    Faz uma query de similar_words
    """
    schema = ManualSchema(fields=[
        coreapi.Field(
            "original_text",
            required=True,
            location="query",
            schema=coreschema.String(min_length=3)
        ),
        coreapi.Field(
            "original_language",
            required=True,
            location="query",
            schema=coreschema.String()
        ),
        coreapi.Field(
            "final_language",
            required=True,
            location="query",
            schema=coreschema.String()
        ),
    ])
    endpoint = "http://core:8000/similar_words"

    def get(self, request, format=None):
        r = requests.get(self.endpoint, params=request.query_params)

        try:
            data = r.json()
        except:
            data = r.text
        finally:
            return Response(data, status=r.status_code)
