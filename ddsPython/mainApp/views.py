from django.shortcuts import render
from .models import Incidente

def incidente(request):

    incidentes = Incidente.objects.all()
        
    return render(request, "main/incidentes.html", {'incidentes':incidentes})