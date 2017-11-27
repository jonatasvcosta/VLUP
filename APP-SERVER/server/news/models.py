from django.db import models
from django.utils.timezone import now
from django.contrib.auth import get_user_model


class Website(models.Model):
    name = models.CharField(max_length=255)
    language = models.CharField(max_length=15, default="")
    description = models.CharField(max_length=300, default="")
    image_url = models.URLField(max_length=500, default="")
    url = models.URLField(max_length=500)

    def __str__(self):
    	return str(self.name)


class Article(models.Model):
    title = models.CharField(max_length=300)
    text = models.TextField()
    url = models.URLField(max_length=500, unique=True)

    description = models.CharField(max_length=500, default='')
    image_url = models.URLField(max_length=500, null=True)
    publish_date = models.DateTimeField(default=now)

    website = models.ForeignKey(Website, on_delete=models.CASCADE)

    rating = models.IntegerField(default=0)

    def __str__(self):
    	return str(self.title)


class ArticleRating(models.Model):
    user = models.ForeignKey(get_user_model(), on_delete=models.CASCADE)
    article = models.ForeignKey(Article, on_delete=models.CASCADE)
    value = models.IntegerField(default=0)
    time = models.IntegerField(default=0)
