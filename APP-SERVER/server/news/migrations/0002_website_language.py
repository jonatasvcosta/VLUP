# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-09-21 21:25
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('news', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='website',
            name='language',
            field=models.CharField(default='', max_length=15),
        ),
    ]
