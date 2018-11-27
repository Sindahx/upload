
from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^$', views.index),
    url(r'pro_list$', views.pro_list),
    url(r'save_list$', views.saveProlist),
]