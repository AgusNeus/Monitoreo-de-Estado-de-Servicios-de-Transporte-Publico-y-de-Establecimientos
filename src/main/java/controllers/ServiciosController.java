package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import domain.entidades.servicios.Servicio;
import domain.Repositorios.Servicio.RepositorioServicio;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServiciosController implements ICrudViewsHandler {
    private RepositorioServicio repositorioServicio;

    public ServiciosController(RepositorioServicio repositorioDeServicios) {
        this.repositorioServicio = repositorioDeServicios;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Servicio> servicios = this.repositorioServicio.buscarTodos();
        model.put("servicios", servicios);
        context.render("servicios.hbs", model);
    }

    @Override
    public void show(Context context) {
        Servicio servicio = (Servicio) this.repositorioServicio.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("servicio", servicio);
        context.render("servicios/servicio.hbs", model);
    }

    @Override
    public void create(Context context) {
        Servicio servicio = null;
        Map<String, Object> model = new HashMap<>();
        model.put("servicio", servicio);
        context.render("servicios/servicio.hbs", model);
    }

    @Override
    public void save(Context context) {
        Servicio servicio = new Servicio();
        this.asignarParametros(servicio, context);
        this.repositorioServicio.guardar(servicio);
        context.status(HttpStatus.CREATED);
        context.redirect("/servicios");
    }

    @Override
    public void edit(Context context) {
        Servicio servicio = (Servicio) this.repositorioServicio.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("servicio", servicio);
        context.render("servicios/servicio.hbs", model);
    }

    @Override
    public void update(Context context) {
        Servicio servicio = (Servicio) this.repositorioServicio.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(servicio, context);
        this.repositorioServicio.actualizar(servicio);
        context.redirect("/servicios");
    }

    @Override
    public void delete(Context context) {
        Servicio servicio = (Servicio) this.repositorioServicio.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.repositorioServicio.eliminar(Long.parseLong(context.pathParam("id")));
        context.redirect("/servicios");
    }

    private void asignarParametros(Servicio servicio, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            //servicio.setNombre(context.formParam("nombre")); //TODO
        }
    }
}
