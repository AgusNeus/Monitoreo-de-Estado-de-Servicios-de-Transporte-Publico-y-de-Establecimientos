from django.contrib import admin
from .models import Incidente, Servicio, Comunidad, Miembro, Establecimiento

# Register your models here.
class IncidenteAdmin(admin.ModelAdmin):
    pass

class ComunidadAdmin(admin.ModelAdmin):
    pass

class EstablecimientoAdmin(admin.ModelAdmin):
    pass

class ServicioAdmin(admin.ModelAdmin):
    pass

class MiembroAdmin(admin.ModelAdmin):
    pass



admin.site.register(Miembro, MiembroAdmin)
admin.site.register(Servicio, ServicioAdmin)
admin.site.register(Establecimiento, EstablecimientoAdmin)
admin.site.register(Incidente, IncidenteAdmin)
admin.site.register(Comunidad, ComunidadAdmin)