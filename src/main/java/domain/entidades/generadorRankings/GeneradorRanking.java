package domain.entidades.generadorRankings;

import domain.Repositorios.Entidad.RepositorioEntidad;
import domain.Repositorios.Incidente.RepositorioIncidente;
import domain.entidades.Rankings.MayorCantidadReportes;
import domain.entidades.Rankings.MayorGradoImpacto;
import domain.entidades.Rankings.MayorPromedioCierre;
import domain.entidades.Rankings.Ranking;
import domain.entidades.servicios.*;
import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.EntidadPrestadora;
import domain.entidades.servicios.Incidente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
public class GeneradorRanking {
  @Getter
   private List<Entidad> rankingMayorCantidadReportes; // de esta semana
   private List<Entidad> rankingMayorPromedioCierre;
   private List<Incidente> rankingMayorGradoImpacto;
   private List<Incidente> incidentes;
   private ExportadorInforme exportadorInforme;
   private Timer timer;
   private static GeneradorRanking instancia = null;

  LocalTime horaDeInicio = LocalTime.of(0, 0, 0);
  // LA CLASE SE DEBE EJECUTAR POR PRIMERA VEZ UN DOMINGO

  public GeneradorRanking(RepositorioIncidente repositorioIncidente) {
    rankingMayorCantidadReportes = new ArrayList<>();
    rankingMayorPromedioCierre = new ArrayList<>();
    rankingMayorGradoImpacto = new ArrayList<>();
    incidentes = repositorioIncidente.buscarTodos();
  }

  public static GeneradorRanking getInstance(RepositorioIncidente repositorioIncidente) {
    if (instancia == null) {
      instancia = new GeneradorRanking(repositorioIncidente);
    }
    return instancia;
  }
  public  void main(String[] args) {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    // Calcula el retraso hasta la hora exacta
    LocalTime horaActual = LocalTime.now();
    long delayInicial = horaActual.until(horaDeInicio, ChronoUnit.SECONDS);

    if (delayInicial < 0) {
      delayInicial += 24 * 60 * 60; // Agrega 24 horas si la hora deseada ya pasó hoy
    }
    // Programa la tarea para que se ejecute en la hora exacta
    executor.schedule(() -> {
      timer = new Timer();
      timer.schedule(task, 0, 7);
    }, delayInicial, TimeUnit.DAYS);

    // Cierra el executor después de ejecutar la tarea
    executor.shutdown();
  }

  TimerTask task = new TimerTask() {
    public void run() {
      Ranking criterio1 = new MayorCantidadReportes();
      Ranking criterio2 = new MayorGradoImpacto();
      Ranking criterio3 = new MayorPromedioCierre();

      rankingMayorCantidadReportes = criterio1.generar(incidentes);
      rankingMayorGradoImpacto =  criterio2.generarGradoImpacto(incidentes);
      rankingMayorPromedioCierre = criterio3.generar(incidentes);

      incidentes.clear();
    }
  };

  public void agregarIncidente(Incidente unIncidente) {
    incidentes.add(unIncidente);
  }


  public void devolverInformeEntidadPrestadora(EntidadPrestadora entidadPrestadora, Ranking criterio) {
    List<Entidad> entidadesDeEmpresas = entidadPrestadora.getEntidades();
    entidadesDeEmpresas.retainAll(rankingSegunCriterio(criterio)); // Intersecta las listas
    exportadorInforme.exportarInforme(entidadesDeEmpresas);
  }

  public List rankingSegunCriterio(Ranking criterio) {
    if (criterio instanceof MayorPromedioCierre) {
      return rankingMayorPromedioCierre;
    }
    else if (criterio instanceof MayorCantidadReportes) {
      return rankingMayorCantidadReportes;
    }
    else if (criterio instanceof MayorGradoImpacto) {
      return rankingMayorGradoImpacto;
    }
  throw new RuntimeException();
  }

  //metodos para probar
  public List<Entidad> generarRanking1(){
    Ranking criterio1 = new MayorCantidadReportes();
    rankingMayorCantidadReportes =  criterio1.generar(incidentes);
    return rankingMayorCantidadReportes;
  }
  public List<Incidente> generarRanking2(){
    Ranking criterio2 = new MayorGradoImpacto();
    rankingMayorGradoImpacto = criterio2.generarGradoImpacto(incidentes);
    return rankingMayorGradoImpacto;
  }
  public List<Entidad> generarRanking3(){
    Ranking criterio3 = new MayorPromedioCierre();
    rankingMayorPromedioCierre = criterio3.generar(incidentes);
    return rankingMayorPromedioCierre;
  }
}


