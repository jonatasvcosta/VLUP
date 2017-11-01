import requests
import coreapi
import coreschema
from rest_framework import viewsets, authentication
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.schemas import ManualSchema
from .serializer import WebsiteSerializer, ArticleResultSerializer
from .models import Article, Website


class WebsiteViewSet(viewsets.ModelViewSet):
    queryset = Website.objects.all()
    serializer_class = WebsiteSerializer
    filter_fields = ("language", "name")


class ArticleSimilarity(APIView):
    """
    Faz uma query de similaridade
    """
    schema = ManualSchema(fields=[
        coreapi.Field(
            "query",
            required=True,
            location="query",
            schema=coreschema.String(min_length=3)
        ),
        coreapi.Field(
            "language",
            required=True,
            location="query",
            schema=coreschema.String()
        ),
        coreapi.Field(
            "limit",
            required=False,
            location="query",
            schema=coreschema.Integer(minimum=1)
        ),
    ])
    endpoint = "http://core:8000/similarity"

    def get(self, request, format=None):
        r = requests.get(self.endpoint, params=request.query_params)

        if r.status_code != 200:
            try:
                data = r.json()
            except:
                data = r.text
            return Response(data, status==r.status_code)

        try:
            result = r.json()['result']

            queryset = Article.objects.filter(pk__in=[article_id for article_id, score in result]).defer('text')

            serializer = ArticleResultSerializer(dict(result), queryset, many=True)
            serializer.is_valid(raise_exception=False)

            return Response(sorted(serializer.data, key=lambda x: x['score'], reverse=True))

        except Exception as e:
            return Response({'message': str(e), 'description': 'Internal Error'}, status=500)
