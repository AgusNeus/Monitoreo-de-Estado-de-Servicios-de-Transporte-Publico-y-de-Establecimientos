package domain.entidades.Rankings;

import domain.entidades.comunidad.Comunidad;
import domain.entidades.comunidad.Miembro;
import domain.entidades.comunidad.RolServicio;
import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.TipoDeServicio;

import java.util.*;
import java.util.stream.Collectors;

public class MayorGradoImpacto extends Ranking{

  @Override
  public List<Incidente> generarGradoImpacto(List<Incidente> incidentes) {

    incidentes = incidentes.stream()
            .filter(unincidente -> esIncidenteAbiertoDeEstaSemana(unincidente))
            .collect(Collectors.toList());

    List<Incidente> finalIncidentes = incidentes;

    List<Integer> listaGradoImpacto = incidentes.stream().map(Incidente::getComunidad)
            .map(comunidad -> gradoDeImpacto(comunidad,finalIncidentes)).collect(Collectors.toList());

    Comparator<Incidente> comparator = Comparator.comparingInt(incidente -> listaGradoImpacto.get(finalIncidentes.indexOf(incidente)));

    List<Incidente> finalIncidentesOrdenados = finalIncidentes.stream()
            .sorted(comparator.reversed())
            .collect(Collectors.toList());


    return finalIncidentesOrdenados;
  }

  public Integer gradoDeImpacto(Comunidad comunidad, List<Incidente> incidentes){

    List<Miembro> listaMiembros = new ArrayList<>();

    for (Miembro unMiembro : comunidad.getMiembrosNuestro().keySet()) {
      incidentes.forEach(unIncidente -> {
        TipoDeServicio tipoDeServicio = unIncidente.getServicio().getTipoServicio();
        if(unMiembro.getRolesServicios().get(tipoDeServicio.ordinal()) == RolServicio.AFECTADO) {
          listaMiembros.add(unMiembro);
        }
      });
    }
    return listaMiembros.size();
  }
  public List<Entidad> generar(List<Incidente> listaIncidentes) {return null;}
}

