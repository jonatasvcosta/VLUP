from rest_framework import serializers
from .models import Article, ArticleRating, Website


class WebsiteSerializer(serializers.ModelSerializer):
    class Meta:
        model = Website
        fields = '__all__'


class ArticleSerializer(serializers.ModelSerializer):
    class Meta:
        model = Article
        fields = '__all__'
        depth = 1


class ArticleRatingSerializer(serializers.ModelSerializer):
    class Meta:
        model = ArticleRating
        fields = ('value', 'time')

class ArticleResultSerializer(serializers.BaseSerializer):
    scores = None

    def __init__(self, scores, data, many=False):
        self.scores = scores
        super().__init__(data, many=many)

    def to_representation(self, obj):
        rep = ArticleSerializer(obj).data
        rep['score'] = self.scores[obj.id]
        return rep
