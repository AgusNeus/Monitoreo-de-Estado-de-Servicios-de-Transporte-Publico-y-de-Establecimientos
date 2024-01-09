package db;


import domain.Repositorios.Comunidad.RepositorioComunidad;
import domain.Repositorios.Entidad.RepositorioEntidad;
import domain.Repositorios.Establecimiento.RepositorioEstablecimiento;
import domain.Repositorios.Incidente.RepositorioIncidente;
import domain.Repositorios.Miembro.RepositorioMiembro;
import domain.Repositorios.Servicio.RepositorioServicio;
import domain.Repositorios.Usuario.RepositorioDeUsuarios;
import domain.entidades.comunidad.Comunidad;
import domain.entidades.comunidad.Miembro;
import domain.entidades.comunidad.RolComunidad;
import domain.entidades.notificaciones.formaDeNotificacion.AlertarSinApuro;
import domain.entidades.notificaciones.medioDeNotificaciones.AdapterWhatsApp;
import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.Establecimiento;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.Servicio;
import domain.entidades.signin.RolUsuario;
import domain.entidades.signin.Usuario;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContextTest implements SimplePersistenceTest {

  @Test
  void contextUp() {
    assertNotNull(entityManager());
  }

  @Test
  void contextUpWithTransaction() throws Exception {
    AlertarSinApuro alertarSinApuro = new AlertarSinApuro();

    AdapterWhatsApp adapterWhatsApp = new AdapterWhatsApp();

    Miembro miembro1 = new Miembro();
    miembro1.setNombre("Carlos");
    miembro1.setFormaNotificacion(alertarSinApuro);
    miembro1.setMedioDeNotificacion(adapterWhatsApp);

    Miembro miembro2 = new Miembro();
    miembro2.setNombre("Pablo");
    miembro2.setFormaNotificacion(alertarSinApuro);
    miembro2.setMedioDeNotificacion(adapterWhatsApp);

    Comunidad comunidad1 = new Comunidad();
    comunidad1.setNombre("Amigos sobre ruedas");
    comunidad1.setDescripcion("Comunidad de amigos en sillas de ruedas");
    comunidad1.agregarMiembro(miembro1, RolComunidad.AFECTADO);
    comunidad1.agregarMiembro(miembro2, RolComunidad.ADMINISTRADOR);

    Usuario usuario1 = new Usuario();
    usuario1.setNombreUsuario("carlitos11");
    usuario1.setNombre("Carlos");
    usuario1.setApellido("Carranza");
    usuario1.setEmail("carloscarranza@gmail.com");
    String hashedPassword = BCrypt.hashpw("Carlos", BCrypt.gensalt());
    usuario1.setContrasenia(hashedPassword);

    Usuario usuario2 = new Usuario();
    usuario2.setNombreUsuario("admin");
    usuario2.setNombre("Pablo");
    usuario2.setApellido("Mendez");
    usuario2.setEmail("pablomendez@gmail.com");
    String hashedPassword2 = BCrypt.hashpw("Admin", BCrypt.gensalt());
    usuario2.setContrasenia(hashedPassword2);
    usuario2.setRolUsuario(RolUsuario.ADMINISTRADOR_PLATAFORMA);

    Establecimiento establecimiento1 = new Establecimiento();
    establecimiento1.setNombre("Coto Palermo");

    Establecimiento establecimiento2 = new Establecimiento();
    establecimiento2.setNombre("Banco Santander Haedo");

    Establecimiento establecimiento3 = new Establecimiento();
    establecimiento3.setNombre("Carrefour Recoleta");

    Establecimiento establecimiento4 = new Establecimiento();
    establecimiento4.setNombre("Jumbo Palermo");

    Entidad entidad1 = new Entidad();
    entidad1.setNombre("Coto");

    Entidad entidad2 = new Entidad();
    entidad2.setNombre("Banco Santander");

    Entidad entidad3 = new Entidad();
    entidad3.setNombre("Carrefour");

    Entidad entidad4 = new Entidad();
    entidad4.setNombre("Jumbo");

    Servicio servicio1 = new Servicio();
    servicio1.setNombre("Escalera");

    Servicio servicio2 = new Servicio();
    servicio2.setNombre("Ascensor");

    Servicio servicio3 = new Servicio();
    servicio3.setNombre("Rampa");


    RepositorioComunidad repositorioComunidad = new RepositorioComunidad();
    RepositorioMiembro repositorioMiembro = new RepositorioMiembro();
    RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();

    RepositorioServicio repositorioServicio = new RepositorioServicio();
    RepositorioEstablecimiento repositorioEstablecimiento = new RepositorioEstablecimiento();

    RepositorioEntidad repositorioEntidad = new RepositorioEntidad();

    withTransaction(() -> {
      Miembro miembroGuardado = repositorioMiembro.guardar(miembro1);
      Miembro miembroGuardado2 = repositorioMiembro.guardar(miembro2);

      Usuario usuarioGuardado = repositorioDeUsuarios.guardar(usuario1);

      Comunidad comunidadGuardada1 = repositorioComunidad.guardar(comunidad1);

      Servicio servicioGuardado = repositorioServicio.guardar(servicio1);
      Servicio servicioGuardado2 = repositorioServicio.guardar(servicio2);
      Servicio servicioGuardado3 = repositorioServicio.guardar(servicio3);

      Establecimiento establecimientoGuardado = repositorioEstablecimiento.guardar(establecimiento1);
      Establecimiento establecimientoGuardado2 = repositorioEstablecimiento.guardar(establecimiento2);
      Establecimiento establecimientoGuardado3= repositorioEstablecimiento.guardar(establecimiento3);
      Establecimiento establecimientoGuardado4 = repositorioEstablecimiento.guardar(establecimiento4);

      Entidad entidadGuardada = repositorioEntidad.guardar(entidad1);
      Entidad entidadGuardada2 = repositorioEntidad.guardar(entidad2);
      Entidad entidadGuardada3 = repositorioEntidad.guardar(entidad3);
      Entidad entidadGuardada4 = repositorioEntidad.guardar(entidad4);

      Usuario usuarioGuardado2 = repositorioDeUsuarios.guardar(usuario2);

    });
  }
}