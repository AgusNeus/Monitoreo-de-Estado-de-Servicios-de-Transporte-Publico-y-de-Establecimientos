from django.urls import path
from . import views

urlpatterns = [
    path('', views.incidente, name="Incidentes"),
]