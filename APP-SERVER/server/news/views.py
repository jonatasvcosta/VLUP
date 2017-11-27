import requests
import coreapi
import coreschema
from collections import defaultdict
from rest_framework import viewsets, authentication
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.schemas import ManualSchema
from rest_framework.permissions import IsAuthenticated
from rest_framework.decorators import detail_route, list_route
from .serializer import WebsiteSerializer, ArticleSerializer, ArticleRatingSerializer, ArticleResultSerializer
from .models import Article, Website, ArticleRating
from .keywords import get_keywords


class WebsiteViewSet(viewsets.ModelViewSet):
    queryset = Website.objects.all()
    serializer_class = WebsiteSerializer
    filter_fields = ("language", "name")


class ArticleViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Article.objects.all()
    serializer_class = ArticleSerializer
    filter_fields = ('website__language',)

    @list_route(url_path='home', schema = ManualSchema(fields=[
        coreapi.Field(
            "website__language",
            required=True,
            location="query",
            schema=coreschema.String()
        )
    ]))
    def home(self, request):
        queryset = self.filter_queryset(self.get_queryset()).order_by('-publish_date')

        page = self.paginate_queryset(queryset)

        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)

    @list_route(url_path='trending_topics', schema = ManualSchema(fields=[
        coreapi.Field(
            "website__language",
            required=True,
            location="query",
            schema=coreschema.String()
        )
    ]))
    def trending_topics(self, request):
        queryset = self.filter_queryset(self.get_queryset()).order_by('-publish_date')[:20]

        language = queryset.first().website.language

        trending_keywords = get_keywords(queryset.values_list('id', flat=True), language, limit=20)

        keywords_frequency = defaultdict(int)
        for article in trending_keywords:
            for keyword in article['keywords']:
                keywords_frequency[keyword] += 1

        sorted_keywords = [keyword for keyword, _ in sorted(keywords_frequency.items(), key=lambda x: x[1], reverse=True)]

        return Response(sorted_keywords)

    @detail_route(methods=['post'], permission_classes=[IsAuthenticated], url_path='rate', serializer_class=ArticleRatingSerializer)
    def rate_article(self, request, pk=None):
        article = Article.objects.get(pk=pk)
        rating = ArticleRating.objects.filter(user=request.user, article__pk=pk).first()

        value = int(request.data.get("value", None))
        if value is None:
            return Response("'value' has to be an integer", status=400)
        time = int(request.data.get("time", 0))

        if rating:
            article.rating = article.rating - rating.value
            rating.value = value
            rating.time = time
            rating.save()
        else:
            rating = ArticleRating.objects.create(article=article, user=request.user, value=value, time=time)

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

            queryset = Article.objects.filter(pk__in=[article_id for article_id, score in result])

            serializer = ArticleResultSerializer(dict(result), queryset, many=True)
            serializer.is_valid(raise_exception=False)

            return Response(sorted(serializer.data, key=lambda x: x['score'], reverse=True))

        except Exception as e:
            return Response({'message': str(e), 'description': 'Internal Error'}, status=500)


class Vocabulary(APIView):
    """
    Busca keywords a partir de uma query de similaridade
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
    similarity_endpoint = "http://core:8000/similarity"

    def get(self, request, format=None):
        params = request.query_params

        keywords_limit = int(params.get('limit', 100))
        language = params.get('language', None)

        article_limit = max(10, int(int(keywords_limit) * 2 / 20))
        r = requests.get(self.similarity_endpoint, params={"query": params.get("query"), "language": language, "limit": article_limit})

        if r.status_code != 200:
            try:
                data = r.json()
            except:
                data = r.text
            return Response(data, status=r.status_code)

        try:
            result = r.json()['result']

            article_ids = [article_id for article_id, score in result]


            article_keywords = get_keywords(article_ids, language, limit=20)

            keywords_frequency = defaultdict(int)
            for article in article_keywords:
                for keyword in article['keywords']:
                    keywords_frequency[keyword] += 1

            sorted_keywords = [keyword for keyword, _ in sorted(keywords_frequency.items(), key=lambda x: x[1], reverse=True)]

            return Response(sorted_keywords[:keywords_limit])

        except Exception as e:
            return Response({'message': str(e), 'description': 'Internal Error'}, status=500)
