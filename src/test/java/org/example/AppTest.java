//package org.example;
//
//import domain.servicios.*;
//import domain.comunidad.Comunidad;
//import domain.comunidad.Miembro;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//
//import static domain.servicios.Estado.DENEGADO;
//import static domain.servicios.Estado.DISPONIBLE;
//import static domain.servicios.TipoDeServicio.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//
//class ServicioTest {
//
//    @Test
//    void testServiciosDeInteres() {
//        // Crear una instancia del objeto bajo prueba.java
//        Comunidad neus = new Comunidad();
//        Comunidad lucas = new Comunidad();
//      //  Localizacion neusLoca = new Localizacion();
//
//        Ubicacion ubicacion = new Ubicacion(1F,1F);
//
//        Servicio.java servicio1 = new Servicio.java(BANIO, DENEGADO, "un serivicio muy lindo");
//        Servicio.java servicio2 = new Servicio.java(ESCALERA_MECANICA, DISPONIBLE, "un servico muy fachero");
//        Servicio.java servicio3 = new Servicio.java(RAMPA,DENEGADO,"rampa muy alta");
//
//        List <Servicio.java> servicios1 = List.of(servicio1,servicio2);
//        List <Servicio.java> servicios2 = List.of(servicio1,servicio3);
//        List <Servicio.java> servicios3 = List.of(servicio2,servicio3);
//        List <Servicio.java> servicios4 = List.of(servicio1,servicio2);
//
//        Establecimiento establecimiento1 =new Establecimiento("establecimiento1", ubicacion, servicios1);
//        Establecimiento establecimiento2= new Establecimiento("establecimiento2", ubicacion, servicios2);
//        Establecimiento establecimiento3 = new Establecimiento("establecimiento3", ubicacion, servicios3);
//        Establecimiento establecimiento4= new Establecimiento("establecimiento4", ubicacion, servicios4);
//
//        List<Establecimiento> establecimientos = List.of(establecimiento1,establecimiento2);
//
//
//        List<Establecimiento> establecimientos2 = List.of(establecimiento3,establecimiento4);
//
//        Entidad entidad1 = new Entidad("entidad1", establecimientos);
//        Entidad entidad2 = new Entidad("entidad2", establecimientos );
//
//        List<Comunidad> comunidades = List.of(neus, lucas);
//        List<Entidad> entidades = List.of(entidad1, entidad2);
//        List<TipoDeServicio> tiposDeServicio = List.of(RAMPA,BANIO);
//
//        //Miembro miembro = new Miembro("lucas", "deferrari", "neus@gmail.com", comunidades, entidades, tiposDeServicio);
//
//        // Llamar al método bajo prueba.java
//        //List<Servicio.java> resultado = miembro.serviciosDeInteres();
//
//        // Verificar el resultado esperado
//        //List<Servicio.java> resultadoEsperado = List.of(servicio1, servicio3);
//       // assertEquals(resultadoEsperado, resultado);
//    }
//}
//
