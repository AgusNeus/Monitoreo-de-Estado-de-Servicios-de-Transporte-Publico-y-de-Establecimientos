# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Comunidad(models.Model):
    descripcion = models.CharField(max_length=255, blank=True, null=True)
    nombre = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'comunidad'


class ComunidadMiembros(models.Model):
    comunidad = models.OneToOneField(Comunidad, models.DO_NOTHING, primary_key=True)  # The composite primary key (comunidad_id, miembro_id) found, that is not supported. The first column is selected.
    rol_comunidad = models.CharField(max_length=255, blank=True, null=True)
    miembro = models.ForeignKey('Miembro', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'comunidad_miembros'
        unique_together = (('comunidad', 'miembro'),)


class Entidad(models.Model):
    localidad = models.CharField(max_length=255, blank=True, null=True)
    municipio = models.CharField(max_length=255, blank=True, null=True)
    provincia = models.CharField(max_length=255, blank=True, null=True)
    nombre = models.CharField(max_length=255, blank=True, null=True)
    tipoentidad = models.CharField(db_column='tipoEntidad', max_length=255, blank=True, null=True)  # Field name made lowercase.
    entidadprestadora = models.ForeignKey('Entidadprestadora', models.DO_NOTHING, db_column='entidadPrestadora_id', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'entidad'


class Entidadprestadora(models.Model):
    criterio = models.CharField(max_length=35, blank=True, null=True)
    nombre = models.CharField(max_length=255, blank=True, null=True)
    organismo = models.ForeignKey('Organismodecontrol', models.DO_NOTHING, blank=True, null=True)
    

    class Meta:
        managed = False
        db_table = 'entidadprestadora'


class Establecimiento(models.Model):
    nombre = models.CharField(max_length=255, blank=True, null=True)
    tipoestablecimiento = models.CharField(db_column='tipoEstablecimiento', max_length=255, blank=True, null=True)  # Field name made lowercase.
    latitud = models.FloatField(blank=True, null=True)
    longitud = models.FloatField(blank=True, null=True)
    entidad = models.ForeignKey(Entidad, models.DO_NOTHING, blank=True, null=True)


    class Meta:
        managed = False
        db_table = 'establecimiento'


class EstablecimientoServicio(models.Model):
    establecimiento = models.ForeignKey(Establecimiento, models.DO_NOTHING, db_column='Establecimiento_id')  # Field name made lowercase.
    servicios = models.ForeignKey('Servicio', models.DO_NOTHING)


    class Meta:
        managed = False
        db_table = 'establecimiento_servicio'


class Incidente(models.Model):
    fechahoraapertura = models.DateTimeField(db_column='fechaHoraApertura', blank=True, null=True)  # Field name made lowercase.
    fechahoracierre = models.DateTimeField(db_column='fechaHoraCierre', blank=True, null=True)  # Field name made lowercase.
    nombre = models.CharField(max_length=255, blank=True, null=True)
    observaciones = models.CharField(max_length=255, blank=True, null=True)
    token = models.CharField(max_length=255, blank=True, null=True)
    comunidad = models.ForeignKey(Comunidad, models.DO_NOTHING, blank=True, null=True)
    entidad = models.ForeignKey(Entidad, models.DO_NOTHING, blank=True, null=True)
    establecimiento = models.ForeignKey(Establecimiento, models.DO_NOTHING, blank=True, null=True)
    quienabrio = models.ForeignKey('Miembro', models.DO_NOTHING, blank=True, null=True)
    quiencerro = models.ForeignKey('Miembro', models.DO_NOTHING, related_name='incidente_quiencerro_set', blank=True, null=True)
    servicio = models.ForeignKey('Servicio', models.DO_NOTHING, blank=True, null=True)


    class Meta:
        managed = False
        db_table = 'incidente'


class Miembro(models.Model):
    apellido = models.CharField(max_length=255, blank=True, null=True)
    correoelectronico = models.CharField(db_column='correoElectronico', max_length=255, blank=True, null=True)  # Field name made lowercase.
    formanotificacion = models.CharField(db_column='formaNotificacion', max_length=20, blank=True, null=True)  # Field name made lowercase.
    horarioelegido = models.TimeField(db_column='horarioElegido', blank=True, null=True)  # Field name made lowercase.
    localidad = models.CharField(max_length=255, blank=True, null=True)
    municipio = models.CharField(max_length=255, blank=True, null=True)
    provincia = models.CharField(max_length=255, blank=True, null=True)
    mediodenotificacion = models.CharField(db_column='medioDeNotificacion', max_length=20, blank=True, null=True)  # Field name made lowercase.
    nombre = models.CharField(max_length=255, blank=True, null=True)
    rol = models.IntegerField(blank=True, null=True)
    telefono = models.CharField(max_length=255, blank=True, null=True)
    latitud = models.FloatField(blank=True, null=True)
    longitud = models.FloatField(blank=True, null=True)


    class Meta:
        managed = False
        db_table = 'miembro'


class MiembroEntidad(models.Model):
    miembro = models.ForeignKey(Miembro, models.DO_NOTHING, db_column='Miembro_id')  # Field name made lowercase.
    entidadesasociadas = models.ForeignKey(Entidad, models.DO_NOTHING, db_column='entidadesAsociadas_id')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'miembro_entidad'


class Organismodecontrol(models.Model):
    nombre = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'organismodecontrol'


class Servicio(models.Model):
    descripcion = models.CharField(max_length=255, blank=True, null=True)
    estado = models.CharField(max_length=255, blank=True, null=True)
    nombre = models.CharField(max_length=255, blank=True, null=True)
    tiposervicio = models.CharField(db_column='tipoServicio', max_length=255, blank=True, null=True)  # Field name made lowercase.


    class Meta:
        managed = False
        db_table = 'servicio'


class ServiciosMiembro(models.Model):
    miembro = models.ForeignKey(Miembro, models.DO_NOTHING)
    serviciosasociados = models.CharField(db_column='serviciosAsociados', unique=True, max_length=255, blank=True, null=True)  # Field name made lowercase.


    class Meta:
        managed = False
        db_table = 'servicios_miembro'


class TiposerviciosOrganismodecontrol(models.Model):
    organismodecontrol = models.ForeignKey(Organismodecontrol, models.DO_NOTHING, db_column='organismoDeControl_id')  # Field name made lowercase.
    servicioacontrolar = models.CharField(db_column='servicioAControlar', unique=True, max_length=255, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'tiposervicios_organismodecontrol'


class Usuario(models.Model):
    apellido = models.CharField(max_length=255, blank=True, null=True)
    contrasenia = models.CharField(max_length=255, blank=True, null=True)
    email = models.CharField(max_length=255, blank=True, null=True)
    nombre = models.CharField(max_length=255, blank=True, null=True)
    nombreusuario = models.CharField(db_column='nombreUsuario', max_length=255, blank=True, null=True)  # Field name made lowercase.
    rolusuario = models.CharField(db_column='rolUsuario', max_length=255, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'usuario'
