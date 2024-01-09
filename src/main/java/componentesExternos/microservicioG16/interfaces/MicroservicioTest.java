package componentesExternos.microservicioG16.interfaces;

import domain.entidades.comunidad.Comunidad;
import domain.entidades.comunidad.Miembro;
import domain.entidades.comunidad.RolComunidad;
import domain.entidades.servicios.Establecimiento;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.Servicio;
import domain.entidades.servicios.TipoDeServicio;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MicroservicioTest {

    @Test
    public void mainTest() {

        MicroservicioAdapter fusionComunidades = new MicroservicioG16();

        Miembro miembro1 = new Miembro();
        miembro1.setNombre("Juan");
        miembro1.setGradoDeConfianza(2);

        Miembro miembro2 = new Miembro();
        miembro2.setGradoDeConfianza(5);
        miembro2.setNombre("Ricardo");
        
        Miembro miembro3 = new Miembro();
        miembro3.setGradoDeConfianza(10);
        miembro3.setNombre("PEPE");

        Servicio servicio1 = new Servicio();
        servicio1.setTipoServicio(TipoDeServicio.BANIO);

        Servicio servicio2 = new Servicio();
        servicio2.setTipoServicio(TipoDeServicio.ESCALERA_MECANICA);

        Servicio servicio3 = new Servicio();
        servicio3.setTipoServicio(TipoDeServicio.RAMPA);


        Establecimiento establecimiento1 = new Establecimiento();
        establecimiento1.setNombre("e1");
        Establecimiento establecimiento2 = new Establecimiento();
        establecimiento2.setNombre("e2");
        Establecimiento establecimiento3 = new Establecimiento();
        establecimiento3.setNombre("e3");

        Incidente incidente1 = new Incidente();
        incidente1.setNombre("incidente 1");
        incidente1.setEstablecimiento(establecimiento1);
        incidente1.setServicio(servicio1);

        Incidente incidente2 = new Incidente();
        incidente2.setNombre("incidente 2");
        incidente2.setEstablecimiento(establecimiento2);
        incidente2.setServicio(servicio2);

        Incidente incidente3 = new Incidente();
        incidente3.setNombre("incidente 3");
        incidente1.setEstablecimiento(establecimiento3);
        incidente3.setServicio(servicio3);

        Incidente incidente4 = new Incidente();
        incidente4.setNombre("incidente 4");
        incidente1.setEstablecimiento(establecimiento1);
        incidente4.setServicio(servicio1);

        Comunidad comunidad = new Comunidad();
        comunidad.setNombre("comunidad1");
        comunidad.agregarMiembro(miembro1, RolComunidad.AFECTADO);
        //comunidad.setGradoDeConfianza(3);
        comunidad.agregarIncidente(incidente1,miembro1);
        comunidad.agregarIncidente(incidente2,miembro2);
        comunidad.agregarIncidente(incidente3,miembro3);
        comunidad.agregarIncidente(incidente4,miembro1);
        comunidad.agregarServiciosParaAPI();
        comunidad.agregarEstablecimientosParaAPI();

        Comunidad comunidad2 = new Comunidad();
        comunidad2.setNombre("comunidad2");
        comunidad2.agregarMiembro(miembro2,RolComunidad.AFECTADO);
        comunidad2.agregarMiembro(miembro3,RolComunidad.ADMINISTRADOR);
        //comunidad.setGradoDeConfianza(2);
        comunidad.agregarIncidente(incidente2,miembro3);
        comunidad.agregarIncidente(incidente3,miembro1);
        comunidad.agregarIncidente(incidente1,miembro3);
        comunidad.agregarIncidente(incidente4,miembro1);
        comunidad2.agregarServiciosParaAPI();
        comunidad2.agregarEstablecimientosParaAPI();


        List<Comunidad> comunidades = new ArrayList<>();
        comunidades.add(comunidad);
        comunidades.add(comunidad2);

        List<Comunidad> comunidadesRetornadas = fusionComunidades.sendCommunitiesToApi(comunidad, comunidades);

        if (comunidadesRetornadas != null) {
            System.out.println("Comunidades retornadas por el servidor:");
            for (Comunidad c : comunidadesRetornadas) {
                System.out.println(c.getNombre());
            }
        }

    }

}
