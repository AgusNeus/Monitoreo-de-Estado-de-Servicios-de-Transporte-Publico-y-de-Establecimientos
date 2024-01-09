package domain.entidades.Rankings;

import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.Incidente;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.Duration;
import java.time.LocalDateTime;


  public class MayorCantidadReportes extends Ranking {
  @Override
  public List<Entidad> generar(List<Incidente> listaIncidentes) {
    Map<Entidad, Integer> cantidadIncidentes = new HashMap<>();

    listaIncidentes = listaIncidentes.stream()
            .filter(unincidente -> esIncidenteDeEstaSemana(unincidente))
            .collect(Collectors.toList());

    for (Incidente incidente : listaIncidentes) {
      Entidad entidad = incidente.getEntidad();
      if (existeIncidenteReportado(entidad, incidente, listaIncidentes)) {
        continue; // si existe no lo agrega
      }
      cantidadIncidentes.put(entidad, cantidadIncidentes.getOrDefault(entidad, 0) + 1);
    }

    List<Entidad> entidadesOrdenadas = new ArrayList<>(cantidadIncidentes.keySet());
    entidadesOrdenadas.sort((e1, e2) -> Integer.compare(cantidadIncidentes.get(e2), cantidadIncidentes.get(e1)));
    return entidadesOrdenadas;
  }

    public boolean existeIncidenteReportado(Entidad entidad, Incidente incidente, List<Incidente> listaIncidentes) {
      if (incidente.estadoAbierto()) {
        for (Incidente incidenteLista : listaIncidentes) {
          if (menosDe24Horas(incidente, incidenteLista) && esElMismoIncidente(incidente, incidenteLista)) {
            return true;
          }
        }
      }
      return false;
    }

    public boolean esElMismoIncidente(Incidente incidente1, Incidente incidente2) {
      return incidente1.getEstablecimiento() == incidente2.getEstablecimiento() &&
              incidente1.getServicio() == incidente2.getServicio() &&
              incidente1.getEntidad() == incidente2.getEntidad();
    }

    private boolean menosDe24Horas(Incidente incidente1, Incidente incidente2) {
      LocalDateTime fechaApertura1 = incidente1.getFechaHoraApertura();
      LocalDateTime fechaApertura2 = incidente2.getFechaHoraApertura();

      Duration duracionEntreIncidentes = Duration.between(fechaApertura1, fechaApertura2);
      long horasEntreIncidentes = duracionEntreIncidentes.toHours();

      return horasEntreIncidentes < 24;
    }
    public List<Incidente> generarGradoImpacto(List<Incidente> listaIncidentes) {return null;}
  }
