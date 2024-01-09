package domain.entidades.notificaciones.formaDeNotificacion;

import domain.entidades.comunidad.Miembro;
import domain.entidades.notificaciones.notificacion.NotificacionApertura;
import domain.entidades.notificaciones.notificacion.Notificacion;
import org.apache.commons.mail.EmailException;

import java.time.LocalTime;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AlertarSinApuro implements FormaNotificacion{
    private Timer timer;
    private Map<Miembro, List<Notificacion>> diccionarioNotificaciones = new HashMap<>();
    private LocalTime horarioDeNotificacion;

    public void notificar(Notificacion unaNotificacion, Miembro unMiembro) {
        List<Notificacion> listaNotificaciones = diccionarioNotificaciones.get(unMiembro);

        if (listaNotificaciones == null) {
            listaNotificaciones = new ArrayList<>();
            diccionarioNotificaciones.put(unMiembro, listaNotificaciones);
        }

        listaNotificaciones.add(unaNotificacion);
    }

    LocalTime horaDeInicio = LocalTime.of(0,0,0);

    public void main() {
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
            timer.schedule(task, 0, 30);
        }, delayInicial, TimeUnit.MINUTES);

        // Cierra el executor después de ejecutar la tarea
        executor.shutdown();
    }

    TimerTask task = new TimerTask() {
        public void run() {
    Set<Miembro> miembros = diccionarioNotificaciones.keySet();

    Set<Miembro> miembrosAdecuados = miembros.stream().filter(unMiembro -> unMiembro.esLaHora()).collect(Collectors.toSet());

    miembrosAdecuados.forEach(unMiembro -> {
        List<Notificacion> notificaciones = diccionarioNotificaciones.get(unMiembro);

        notificaciones = notificaciones.stream().filter(unaNotificacion-> unaNotificacion.getIncidenteAsociado().estadoAbierto())
                .collect(Collectors.toList());

        String nuevoCuerpo = "";
        notificaciones.forEach(unaNotificacion -> {nuevoCuerpo.concat(unaNotificacion.cuerpo); nuevoCuerpo.concat(" \n");});

        NotificacionApertura notificacion = new NotificacionApertura(null);   //
        notificacion.setAsunto("Esta listo su nuevo resumen de notificaciones.");
        notificacion.setCuerpo(nuevoCuerpo);

        try {
            unMiembro.getMedioDeNotificacion().notificar(notificacion, unMiembro);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }

    });
           
        }
    };


    private Boolean esLaHora(){
        LocalTime horarioActual = LocalTime.now();
        return horarioActual == horarioDeNotificacion;
    }

    public List<NotificacionApertura> filtrarAlertarSinApuro(List<Notificacion> listaNotificaciones) {
        List<NotificacionApertura> listaFiltrada = new ArrayList<>();
        for (Notificacion notificacion : listaNotificaciones) {
            if (notificacion instanceof NotificacionApertura) {
                listaFiltrada.add((NotificacionApertura) notificacion);
            }
        }

        return listaFiltrada;
    }
}

