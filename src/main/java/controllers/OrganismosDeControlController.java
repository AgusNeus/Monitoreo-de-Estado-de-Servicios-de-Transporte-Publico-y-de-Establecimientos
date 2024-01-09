package controllers;

import domain.Repositorios.OrganismoDeControl.RepositorioOrganismoDeControl;
import domain.entidades.servicios.OrganismoDeControl;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrganismosDeControlController implements ICrudViewsHandler {
    private RepositorioOrganismoDeControl repositorioOrganismoDeControl;

    public OrganismosDeControlController(RepositorioOrganismoDeControl repositorioOrganismoDeControl) {
        this.repositorioOrganismoDeControl = repositorioOrganismoDeControl;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<OrganismoDeControl> organismoDeControl = this.repositorioOrganismoDeControl.buscarTodos();
        model.put("organismoDeControl", organismoDeControl);
        context.render("organismoDeControl.hbs", model);
    }

    @Override
    public void show(Context context) {
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repositorioOrganismoDeControl.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("organismoDeControl", organismoDeControl);
        context.render("organismoDeControl/organismoDeControl.hbs", model);
    }

    @Override
    public void create(Context context) {
        OrganismoDeControl organismoDeControl = null;
        Map<String, Object> model = new HashMap<>();
        model.put("organismoDeControl", organismoDeControl);
        context.render("organismoDeControl/organismoDeControl.hbs", model);
    }

    @Override
    public void save(Context context) {
        OrganismoDeControl organismoDeControl = new OrganismoDeControl();
        this.asignarParametros(organismoDeControl, context);
        this.repositorioOrganismoDeControl.guardar(organismoDeControl);
        context.status(HttpStatus.CREATED);
        context.redirect("/organismoDeControl");
    }

    @Override
    public void edit(Context context) {
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repositorioOrganismoDeControl.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("organismoDeControl", organismoDeControl);
        context.render("organismoDeControl/organismoDeControl.hbs", model);
    }

    @Override
    public void update(Context context) {
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repositorioOrganismoDeControl.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(organismoDeControl, context);
        this.repositorioOrganismoDeControl.actualizar(organismoDeControl);
        context.redirect("/organismoDeControl");
    }

    @Override
    public void delete(Context context) {
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repositorioOrganismoDeControl.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.repositorioOrganismoDeControl.eliminar(Long.parseLong(context.pathParam("id")));
        context.redirect("/organismoDeControl");
    }

    private void asignarParametros(OrganismoDeControl organismoDeControl, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            organismoDeControl.setNombre(context.formParam("nombre"));
        }
    }
}

