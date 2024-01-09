package componentesExternos.microservicioG16.interfaces;

import domain.entidades.comunidad.*;
import domain.entidades.servicios.Establecimiento;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.Servicio;

import java.util.ArrayList;
import java.util.List;

public class Test3 {
    public static void main(String[] args) {
        // Crear objetos con datos reales
        Comunidad comunidad = new Comunidad();
        comunidad.setNombre("Comunidad 1");

        Miembro miembro1 = new Miembro();
        miembro1.setNombre("Carlos");

        Miembro miembro2 = new Miembro();
        miembro2.setNombre("Jose");

        comunidad.agregarMiembro(miembro1, RolComunidad.AFECTADO);
        comunidad.agregarMiembro(miembro2, RolComunidad.AFECTADO);

        comunidad.setGradoDeConfianza(0.5f);

        Establecimiento establecimiento1 = new Establecimiento();
        establecimiento1.setNombre("Establecimiento 1");

        Establecimiento establecimiento2 = new Establecimiento();
        establecimiento2.setNombre("Establecimiento 2");

        Servicio servicio1 = new Servicio();
        servicio1.setNombre("Servicio 1");

        Servicio servicio2 = new Servicio();
        servicio2.setNombre("Servicio 2");

        // Crear lista de comunidades con datos reales
        List<Comunidad> listaComunidades = new ArrayList<>();
        Comunidad comunidad2 = new Comunidad();
        comunidad2.setNombre("Comunidad 2");

        Miembro miembro3 = new Miembro();
        miembro3.setNombre("Luis");

        Incidente incidente1 = new Incidente();
        incidente1.setNombre("incidente 1");
        incidente1.setEstablecimiento(establecimiento1);
        incidente1.setServicio(servicio1);

        Incidente incidente2 = new Incidente();
        incidente2.setNombre("incidente 2");
        incidente2.setEstablecimiento(establecimiento2);
        incidente2.setServicio(servicio2);

        comunidad.agregarEstablecimientosParaAPI();
        comunidad2.agregarServiciosParaAPI();

        comunidad2.agregarMiembro(miembro3, RolComunidad.AFECTADO);
        comunidad2.setGradoDeConfianza(0.6f);
        comunidad2.agregarServiciosParaAPI();
        comunidad2.agregarEstablecimientosParaAPI();


        listaComunidades.add(comunidad);
        listaComunidades.add(comunidad2);

        // Enviar solicitud al servidor
        MicroservicioG16 fusionComunidades = new MicroservicioG16();
        List<Comunidad> comunidadesRetornadas = fusionComunidades.sendCommunitiesToApi(comunidad, listaComunidades);

        if (comunidadesRetornadas != null) {
            System.out.println("Comunidades retornadas por el servidor:");
            for (Comunidad c : comunidadesRetornadas) {
                System.out.println(c.getNombre());
            }
        }
    }
}
