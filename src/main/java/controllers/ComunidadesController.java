package controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Repositorios.Comunidad.RepositorioComunidad;
import domain.Repositorios.Miembro.RepositorioMiembro;
import domain.entidades.comunidad.Comunidad;
import domain.entidades.comunidad.Miembro;
import domain.entidades.comunidad.RolComunidad;
import domain.entidades.servicios.Incidente;
import domain.entidades.signin.Usuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.utils.ICrudViewsHandler;
import domain.entidades.signin.RolUsuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class    ComunidadesController implements ICrudViewsHandler {
    private RepositorioComunidad repositorioComunidad;
    private RepositorioMiembro repositorioMiembro;
    public ComunidadesController(RepositorioComunidad repositorioDeComunidades, RepositorioMiembro repositorioMiembro) {
        this.repositorioComunidad = repositorioDeComunidades;
        this.repositorioMiembro = repositorioMiembro;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Comunidad> comunidades = this.repositorioComunidad.buscarTodos();
        Integer idMiembro = context.sessionAttribute("id");
        Miembro miembro = repositorioMiembro.buscarPorId2(idMiembro);
        Map<Comunidad, RolComunidad> comunidadesDelMiembro = miembro.getComunidadesPertenecientes();
        RolUsuario userRole = context.sessionAttribute("tipo_rol");
        if (userRole == RolUsuario.ADMINISTRADOR_PLATAFORMA) {
            model.put("es_admin", context.sessionAttribute("es_admin"));
        }
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO



        List<Comunidad> comunidadesNoPertenece = comunidades.stream()
                .filter(comunidad -> !comunidad.getMiembrosNuestro().containsKey(miembro))
                .collect(Collectors.toList());

        logger.log(Level.INFO, "ESTAS SON LAS COMUNIDADES A LAS QUE NO PERTENECE:");
        comunidadesNoPertenece.forEach(unaComunidad -> logger.log(Level.INFO, unaComunidad.getNombre()));

        model.put("comunidades", comunidadesNoPertenece);
        context.render("unirseAComunidad.hbs", model);
    }

    @Override
    public void show(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioComunidad.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        context.render("comunidad/comunidad.hbs", model);
    }

    @Override
    public void create(Context context) {
        Comunidad comunidad = null;
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        context.render("comunidad/comunidad.hbs", model);
    }

    @Override
    public void save(Context context) {
        Comunidad comunidad = new Comunidad();
        this.asignarParametros(comunidad, context);
        this.repositorioComunidad.guardar(comunidad);
        context.status(HttpStatus.CREATED);
        context.redirect("/crear-comunidad");
    }

    @Override
    public void edit(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioComunidad.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        context.render("comunidad/comunidad.hbs", model);
    }

    @Override
    public void update(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioComunidad.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(comunidad, context);
        this.repositorioComunidad.actualizar(comunidad);
        context.redirect("/comunidad");
    }

    @Override
    public void delete(Context context) {
        Comunidad comunidad = (Comunidad) this.repositorioComunidad.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.repositorioComunidad.eliminar(Long.parseLong(context.pathParam("id")));
        context.redirect("/comunidad");
    }

    private void asignarParametros(Comunidad comunidad, Context context) {
        if (!Objects.equals(context.formParam("nombre-comunidad"), "")) {
            comunidad.setNombre(context.formParam("nombre-comunidad"));
        }

        if (!Objects.equals(context.formParam("descripcion-comunidad"), "")) {
            comunidad.setDescripcion(context.formParam("descripcion-comunidad"));
        }
    }

    public void vista(Context context) {
        RolUsuario userRole = context.sessionAttribute("tipo_rol");

        if (userRole == RolUsuario.ADMINISTRADOR_PLATAFORMA) {
            Map<String, Object> model = new HashMap<>();
            model.put("es_admin", context.sessionAttribute("es_admin"));
            context.render("crearComunidad.hbs", model);
        } else {
            context.render("401.hbs");
            context.status(403); // Código 403 para acceso no autorizado

        }
    }
    public void unirseAComunidad(Context context) throws JsonProcessingException {
        String json = context.body();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> comunidadesIds = objectMapper.readValue(json, new TypeReference<List<Integer>>() {});

        for (Integer comunidadId : comunidadesIds) {
            Comunidad comunidad = repositorioComunidad.buscarPorId2(comunidadId);
            if (comunidad != null) {
                context.sessionAttribute("id");
                Miembro miembro = repositorioMiembro.buscarPorId2(context.sessionAttribute("id"));;
                comunidad.agregarMiembro(miembro, RolComunidad.AFECTADO);
                repositorioComunidad.actualizar(comunidad);
            }
        }

    }

    public List<Miembro> buscarMiembros(Comunidad comunidad) {
        return repositorioComunidad.getMiembrosDeComunidad(comunidad);
    }


}

