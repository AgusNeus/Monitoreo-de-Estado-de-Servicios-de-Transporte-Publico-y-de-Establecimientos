package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import componentesExternos.geoRef.entidades.*;
import componentesExternos.geoRef.interfaces.GeorefService;
import componentesExternos.geoRef.interfaces.ServicioGeoRef;
import domain.Persistencia.FormaNotificacionConverter;
import domain.Persistencia.MedioNotificacionConverter;
import domain.Repositorios.Comunidad.RepositorioComunidad;
import domain.Repositorios.Miembro.RepositorioMiembro;
import domain.Repositorios.Usuario.RepositorioDeUsuarios;
import domain.entidades.comunidad.Comunidad;
import domain.entidades.comunidad.Miembro;
import domain.entidades.comunidad.Miembro;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.Servicio;
import domain.entidades.signin.RolUsuario;
import domain.entidades.signin.Usuario;
import domain.entidades.signin.UsuarioModificado;
import io.javalin.config.JavalinConfig;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.jetbrains.annotations.NotNull;
import server.exceptions.AccessDeniedException;
import server.middleware.AuthMiddleware;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import server.utils.ICrudViewsHandler;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MiembrosController implements ICrudViewsHandler{
    private RepositorioMiembro repositorioMiembro;
    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioComunidad repositorioComunidad;
    public MiembrosController(RepositorioMiembro repositorio, RepositorioDeUsuarios repo, RepositorioComunidad repoComunidad){
        this.repositorioMiembro = repositorio;
        this.repositorioDeUsuarios = repo;
        this.repositorioComunidad = repoComunidad;
    }

    @Override
    public void index(Context context){
        Integer id2 = context.sessionAttribute("id");
        if (id2 == null) {
            context.redirect("/inicio");
            return;
        }
        Map<String, Object> model = new HashMap<>();
        RolUsuario userRole = context.sessionAttribute("tipo_rol");
        if (userRole == RolUsuario.ADMINISTRADOR_PLATAFORMA) {
            model.put("es_admin", context.sessionAttribute("es_admin"));
        }
        List<Comunidad> comunidades = this.repositorioComunidad.buscarTodos();

        List<Miembro> miembros = this.repositorioMiembro.buscarTodos();
        model.put("comunidades", comunidades);
        model.put("miembros", miembros);
        context.render("AgregarUsuario.hbs", model);
    }
    @Override

    public void show(Context context) {
        Integer id2 = context.sessionAttribute("id");
        if (id2 == null) {
            context.redirect("/inicio");
            return;  // Asegúrate de salir del método después de redirigir
        }
        RolUsuario userRole = context.sessionAttribute("tipo_rol");

        if (userRole == RolUsuario.ADMINISTRADOR_PLATAFORMA) {
            List<Usuario> usuarios = this.repositorioDeUsuarios.buscarTodos();
            Map<String, Object> model = new HashMap<>();
            model.put("es_admin", context.sessionAttribute("es_admin"));
            model.put("usuarios", usuarios);
            context.render("filtroUsuarios.hbs", model);
        } else {
            context.render("401.hbs");
            context.status(401);
        }
    }


    @Override
    public void create(Context context) {
        Miembro miembro= null;
        Map<String, Object> model = new HashMap<>();
        model.put("miembro", miembro);
        context.render("miembro.hbs", model);
    }
    @Override
    public void save(@NotNull Context context) {
        //-------------------------------------
        RolUsuario rolUsuario = AuthMiddleware.getUserRoleType(context);
        //
        if(rolUsuario != RolUsuario.ADMINISTRADOR_PLATAFORMA) {
            context.render("401.hbs");
            context.status(403);
        }
        String miembroId = context.formParam("miembroId");
        String comunidadId = context.formParam("comunidadId");
        String funcion = context.formParam("funcion");

        int comunidadIdInt = Integer.parseInt(comunidadId);
        int miembroIdInt = Integer.parseInt(miembroId);

        Comunidad comunidad = repositorioComunidad.buscarPorId2(comunidadIdInt);
        Miembro miembro = repositorioMiembro.buscarPorId2(miembroIdInt);

        repositorioComunidad.agregarMiembro(comunidad, miembro, funcion);


        //-------------------------------------

    }

    @Override
    public void edit(Context context) {
        Miembro miembro = (Miembro) this.repositorioMiembro.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("miembro", miembro);
        context.render("miembro/miembro.hbs", model);
    }

    @Override
    public void update(Context context) {
        Miembro miembro = (Miembro) this.repositorioMiembro.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(miembro, context);
        this.repositorioMiembro.actualizar(miembro);
        context.redirect("/miembro");
    }

    @Override
    public void delete(Context context) {
        Miembro miembro = (Miembro) this.repositorioMiembro.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.repositorioMiembro.eliminar(Long.parseLong(context.pathParam("id")));
        context.redirect("/miembro");
    }

    public void recibirUsuariosValidados(Context context) throws JsonProcessingException {
        String json = context.body();
        ObjectMapper objectMapper = new ObjectMapper();

        List<UsuarioModificado> usuariosModificados = objectMapper.readValue(json, new TypeReference<List<UsuarioModificado>>() {});
        for (UsuarioModificado usuarioModificado : usuariosModificados) {
            System.out.println("ID: " + usuarioModificado.id);
            System.out.println("Rol: " + usuarioModificado.rol);
            System.out.println("Validado: " + usuarioModificado.validado);
        }
        for (UsuarioModificado usuarioModificado : usuariosModificados) {
            System.out.println(usuarioModificado.id);
            System.out.println(usuarioModificado.rol);
            System.out.println(usuarioModificado.validado);
            Usuario usuario = repositorioDeUsuarios.buscarPorId2(usuarioModificado.id);
            System.out.println(usuario.getNombreUsuario());
            if(usuarioModificado.validado || usuarioModificado.validado == false){
                usuario.setValidado(usuarioModificado.validado);
            }
            if(usuarioModificado.rol != null){
                usuario.setRolUsuario(usuarioModificado.rol);
            }
            repositorioDeUsuarios.actualizar(usuario);
        }
    }

    public void guardarDatosExtra(Context context) throws IOException {

        Miembro miembro = repositorioMiembro.buscarPorId2(context.sessionAttribute("id"));
        ServicioGeoRef servicioGeoref = ServicioGeoRef.instancia();
        String localidad = context.formParam("localidad");
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        logger.info("LA LOCALIDAD ID ES "+ localidad);
        long municipioId = Long.parseLong(context.formParam("municipio"));
        long provinciaId = Long.parseLong(context.formParam("provincia"));

        //String localidad = servicioGeoref.obtenerNombreLocalidad(localidadId);
        String municipio = servicioGeoref.obtenerNombreMunicipio(municipioId);
        String provincia = servicioGeoref.obtenerNombreProvincia(provinciaId);
        miembro.setLocalizacionProvincia(provincia);
        miembro.setLocalizacionDepartamento(localidad);
        miembro.setLocalizacionMunicipio(municipio);
        miembro.setTelefono(context.formParam("telefono"));
        FormaNotificacionConverter formaNotificacionConverter = new FormaNotificacionConverter();
        MedioNotificacionConverter medioNotificacionConverter = new MedioNotificacionConverter();
        miembro.setFormaNotificacion(formaNotificacionConverter.convertToEntityAttribute(context.formParam("forma-notificacion")));
        miembro.setMedioDeNotificacion(medioNotificacionConverter.convertToEntityAttribute(context.formParam("medio-notificacion")));

        String horarioString = context.formParam("horario");
        if (horarioString != null && !horarioString.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime horario = LocalTime.parse(horarioString, formatter);
                miembro.setHorarioElegido(horario);
        }
        this.repositorioMiembro.guardar(miembro);
        context.redirect("/incidentes");

    }

    public void agregarUsuario(Context context) {
        context.render("/agregar-usuario");
    }

    public void mostrarVistaDatosExtra(Context context) throws IOException {
        Integer id2 = context.sessionAttribute("id");
        if (id2 == null) {
            context.redirect("/inicio");
            return;  // Asegúrate de salir del método después de redirigir
        }

        Map<String, Object> model = new HashMap<>();
        ServicioGeoRef servicioGeoref = ServicioGeoRef.instancia();
        ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();
        listadoDeProvinciasArgentinas.provincias.sort((p1, p2) -> p1.id >= p2.id? 1 : -1);
        model.put("es_admin", context.sessionAttribute("es_admin"));
        model.put("provincias", listadoDeProvinciasArgentinas.provincias); //el de municipios y localidades los obtengo con ajax
        context.render("datosExtraUsuario.hbs", model);
    }

    private void asignarParametros(Miembro miembro, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            miembro.setNombre(context.formParam("nombre"));
        }
    }
}

