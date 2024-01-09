package domain.entidades.Rankings;
import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.Incidente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MayorPromedioCierre extends Ranking {
  @Override
  public List<Entidad> generar(List<Incidente> incidentes) {
    Map<Entidad, Double> promedioTiempoCierre = new HashMap<>();
    Map<Entidad, Integer> cantidadIncidentes = new HashMap<>();

    List<Incidente> incidentesCerrados = incidentes.stream()
            .filter(incidente -> incidente.estadoAbierto() == false)
            .collect(Collectors.toList());// hace cualquier cosa

    incidentesCerrados = incidentesCerrados.stream().filter(incidente ->this.esIncidenteDeEstaSemana(incidente))
        .collect(Collectors.toList()); //hago que sean de esta semana

    for (Incidente incidente : incidentesCerrados) {
      Entidad entidad_incidente = incidente.getEntidad();
      long tiempoCierre = incidente.duracion();

      if (promedioTiempoCierre.containsKey(entidad_incidente)) {
        double promedioActual = promedioTiempoCierre.get(entidad_incidente); //consigo el promedio de la entidad
        int cantidadActual = cantidadIncidentes.get(entidad_incidente); //consigo la cantidad de incidentes

        double nuevoPromedio = (promedioActual * cantidadActual + tiempoCierre) / (cantidadActual + 1);
        promedioTiempoCierre.put(entidad_incidente, nuevoPromedio);
        cantidadIncidentes.put(entidad_incidente, cantidadActual + 1);
      } else {
        promedioTiempoCierre.put(entidad_incidente, (double) tiempoCierre); //si no encuentra el incidente
        cantidadIncidentes.put(entidad_incidente, 1);
      }
    }

    // Ordenar las entidades por su promedio de tiempo de cierre en orden descendente
    List<Entidad> entidadesOrdenadas = new ArrayList<>(promedioTiempoCierre.keySet()); //agarro las entidades del map
    entidadesOrdenadas.sort((e1, e2) -> Double.compare(promedioTiempoCierre.get(e2), promedioTiempoCierre.get(e1))); //las ordeno por las que mas tardan a menos

    return entidadesOrdenadas;
  }

  public List<Incidente> generarGradoImpacto(List<Incidente> listaIncidentes) {return null;}
}

// aca
