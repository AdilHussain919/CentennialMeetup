from __future__ import unicode_literals

from django.db import models


class DjangoMigrations(models.Model):
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class Friends(models.Model):
    user_id = models.IntegerField(blank=True, null=True)
    user_id1 = models.IntegerField(blank=True, null=True)
    id = models.IntegerField(primary_key=True)
    class Meta:
        managed = False
        db_table = 'friends'


class User(models.Model):
    user_id = models.AutoField(primary_key=True)
    centennialid = models.IntegerField(blank=True, null=True)
    firstname = models.CharField(max_length=45, blank=True, null=True)
    lastname = models.CharField(max_length=45, blank=True, null=True)
    emailid = models.CharField(max_length=45, blank=True, null=True)
    dateofbirth = models.CharField(max_length=45, blank=True, null=True)
    hobby = models.CharField(max_length=45, blank=True, null=True)
    password = models.CharField(max_length=45, blank=True, null=True)
    program = models.CharField(max_length=100, blank=True, null=True)
    department = models.CharField(max_length=100, blank=True, null=True)
    campus = models.CharField(max_length=45, blank=True, null=True)
    phone = models.CharField(max_length=45, blank=True, null=True)
    profession = models.CharField(max_length=45, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user'