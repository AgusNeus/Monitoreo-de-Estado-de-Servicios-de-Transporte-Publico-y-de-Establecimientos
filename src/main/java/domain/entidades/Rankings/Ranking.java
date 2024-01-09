package domain.entidades.Rankings;

import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.Incidente;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import lombok.Getter;

@Getter
public abstract class Ranking {

  public abstract List<Entidad> generar(List<Incidente> incidentes);

  public abstract List<Incidente> generarGradoImpacto(List<Incidente> incidentes);

  public Boolean esIncidenteDeEstaSemana(Incidente incidente) {
    LocalDateTime fechaActual = LocalDateTime.now();
    LocalDateTime inicioSemana = fechaActual.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); //para que sea lunes el inicio de semana
    LocalDateTime finSemana = fechaActual.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)); //para obtener el domingo siguiente o el mismo día si es que ya es domingo. De esta manera, finSemana representa el domingo de la semana actual.
    LocalDateTime fechaCierre = incidente.getFechaHoraCierre();
    return fechaCierre.isAfter(inicioSemana) && fechaCierre.isBefore(finSemana);
  }

  public Boolean esIncidenteAbiertoDeEstaSemana(Incidente incidente) {
    LocalDateTime fechaActual = LocalDateTime.now();
    LocalDateTime inicioSemana = fechaActual.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDateTime finSemana = fechaActual.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    LocalDateTime fechaApertura = incidente.getFechaHoraApertura(); // Cambio aquí
    return fechaApertura.isAfter(inicioSemana) && fechaApertura.isBefore(finSemana);
  }

}
 
