from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^register$', views.register, name='register'),
    url(r'^login$', views.login, name='login'),
    url(r'^getprofile$', views.getprofile, name='getprofile'),
    url(r'^search$', views.search, name='search'),
    url(r'^getallusers$', views.getallusers, name='getallusers'),
    url(r'^addfriend$', views.addfriend, name='addfriend'),
    url(r'^getallfriends$', views.getallfriends, name='getallfriends'),
]   