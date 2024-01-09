package controllers;

import domain.Repositorios.Entidad.RepositorioEntidad;
import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.Servicio;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EntidadesController implements ICrudViewsHandler {
    private RepositorioEntidad repositorioEntidades;

    public EntidadesController(RepositorioEntidad repositorio) {
        this.repositorioEntidades = repositorio;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Entidad> entidades = this.repositorioEntidades.buscarTodos();
        model.put("entidad", entidades);
        context.render("entidades.hbs", model);
    }

    @Override
    public void show(Context context) {
        Entidad entidad = (Entidad) this.repositorioEntidades.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        context.render("entidades.hbs", model);
    }

    @Override
    public void create(Context context) {
        Entidad entidad = null;
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        context.render("entidades.hbs", model);
    }

    @Override
    public void save(Context context) {
        Entidad entidad = new Entidad();
        this.asignarParametros(entidad, context);
        this.repositorioEntidades.guardar(entidad);
        context.status(HttpStatus.CREATED);
        context.redirect("/entidades");
    }

    @Override
    public void edit(Context context) {
        Entidad entidad = (Entidad) this.repositorioEntidades.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        context.render("entidades.hbs", model);
    }

    @Override
    public void update(Context context) {
        Entidad entidad = (Entidad) this.repositorioEntidades.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(entidad, context);
        this.repositorioEntidades.actualizar(entidad);
        context.redirect("/entidades");
    }

    @Override
    public void delete(Context context) {
        Entidad entidad = (Entidad) this.repositorioEntidades.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.repositorioEntidades.eliminar(Long.parseLong(context.pathParam("id")));
        context.redirect("/entidades");
    }

    private void asignarParametros(Entidad entidad, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            entidad.setNombre(context.formParam("nombre"));
        }
    }
}

