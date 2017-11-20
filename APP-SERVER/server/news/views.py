import requests
import coreapi
import coreschema
from rest_framework import viewsets, authentication
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.schemas import ManualSchema
from rest_framework.permissions import IsAuthenticated
from rest_framework.decorators import detail_route
from .serializer import WebsiteSerializer, ArticleSerializer, ArticleRatingSerializer, ArticleResultSerializer
from .models import Article, Website, ArticleRating


class WebsiteViewSet(viewsets.ModelViewSet):
    queryset = Website.objects.all()
    serializer_class = WebsiteSerializer
    filter_fields = ("language", "name")


class ArticleViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Article.objects.all()
    serializer_class = ArticleSerializer

    @detail_route(methods=['post'], permission_classes=[IsAuthenticated], url_path='rate', serializer_class=ArticleRatingSerializer)
    def rate_article(self, request, pk=None):
        article = Article.objects.get(pk=pk)
        rating = ArticleRating.objects.filter(user=request.user, article__pk=pk).first()

        value = int(request.data.get("value", None))
        if value is None:
            return Response("'value' has to be an integer", status=400)

        if rating:
            article.rating = article.rating - rating.value
            rating.value = value
            rating.save()
        else:
            rating = ArticleRating.objects.create(article=article, user=request.user, value=value)

        article.rating += value
        article.save()

        serializer = ArticleSerializer(article)

        return Response(serializer.data)



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
            return Response(data, status=r.status_code)

        try:
            result = r.json()['result']

            queryset = Article.objects.filter(pk__in=[article_id for article_id, score in result]).defer('text')

            serializer = ArticleResultSerializer(dict(result), queryset, many=True)
            serializer.is_valid(raise_exception=False)

            return Response(sorted(serializer.data, key=lambda x: x['score'], reverse=True))

        except Exception as e:
            return Response({'message': str(e), 'description': 'Internal Error'}, status=500)
