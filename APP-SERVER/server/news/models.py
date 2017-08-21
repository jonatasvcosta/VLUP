from django.db import models


class Website(models.Model):
    name = models.CharField(max_length=255)
    language = models.CharField(max_length=15, default="")
    url = models.CharField(max_length=255)


class Article(models.Model):
    title = models.CharField(max_length=300)
    text = models.TextField()
    url = models.URLField(max_length=500)
    website = models.ForeignKey(Website, on_delete=models.CASCADE)
