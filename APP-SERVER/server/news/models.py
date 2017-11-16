from django.db import models


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
    website = models.ForeignKey(Website, on_delete=models.CASCADE)

    def __str__(self):
    	return str(self.title)

