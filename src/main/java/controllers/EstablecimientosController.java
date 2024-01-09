package controllers;

import domain.Repositorios.Establecimiento.RepositorioEstablecimiento;
import domain.Repositorios.OrganismoDeControl.RepositorioOrganismoDeControl;
import domain.entidades.servicios.Establecimiento;
import domain.entidades.servicios.Servicio;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EstablecimientosController implements ICrudViewsHandler {
    private RepositorioEstablecimiento repositorioEstablecimiento;

    public EstablecimientosController(RepositorioEstablecimiento repositorio) {
        this.repositorioEstablecimiento = repositorio;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Establecimiento> establecimientos = this.repositorioEstablecimiento.buscarTodos();
        model.put("establecimientos", establecimientos);
        context.render("establecimientos.hbs", model);
    }

    @Override
    public void show(Context context) {
        Establecimiento establecimiento = (Establecimiento) this.repositorioEstablecimiento.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", establecimiento);
        context.render("establecimientos.hbs", model);
    }

    @Override
    public void create(Context context) {
        Establecimiento establecimiento = null;
        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", establecimiento);
        context.render("establecimientos.hbs", model);
    }

    @Override
    public void save(Context context) {
        Establecimiento establecimiento = new Establecimiento();
        this.asignarParametros(establecimiento, context);
        this.repositorioEstablecimiento.guardar(establecimiento);
        context.status(HttpStatus.CREATED);
        context.redirect("/establecimientos");
    }

    @Override
    public void edit(Context context) {
        Establecimiento establecimiento = (Establecimiento) this.repositorioEstablecimiento.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", establecimiento);
        context.render("establecimiento.hbs", model);
    }

    @Override
    public void update(Context context) {
        Establecimiento establecimiento = (Establecimiento) this.repositorioEstablecimiento.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(establecimiento, context);
        this.repositorioEstablecimiento.actualizar(establecimiento);
        context.redirect("/establecimientos");
    }

    @Override
    public void delete(Context context) {
        Establecimiento establecimiento = (Establecimiento) this.repositorioEstablecimiento.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.repositorioEstablecimiento.eliminar(Long.parseLong(context.pathParam("id")));
        context.redirect("/establecimientos");
    }

    private void asignarParametros(Establecimiento establecimiento, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            establecimiento.setNombre(context.formParam("nombre"));
        }
    }
}

