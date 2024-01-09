package controllers;


import domain.Repositorios.Comunidad.RepositorioComunidad;
import domain.Repositorios.Entidad.RepositorioEntidad;
import domain.Repositorios.EntidadPrestadora.RepositorioEntidadPrestadora;
import domain.Repositorios.Establecimiento.RepositorioEstablecimiento;
import domain.Repositorios.Incidente.RepositorioIncidente;
import domain.Repositorios.Miembro.RepositorioMiembro;
import domain.Repositorios.OrganismoDeControl.RepositorioOrganismoDeControl;
import domain.Repositorios.Servicio.RepositorioServicio;
import domain.Repositorios.Usuario.RepositorioDeUsuarios;
import domain.entidades.generadorRankings.GeneradorRanking;

public class FactoryController {
    public static Object controller(String nombre) {
        Object controller = null;
        switch (nombre) {

            case "Servicios": controller = new ServiciosController(new RepositorioServicio()); break;
            case "Incidentes": controller = new IncidentesController(new RepositorioIncidente(), new RepositorioComunidad(), new RepositorioServicio(), new RepositorioEstablecimiento(), new RepositorioMiembro(), new RepositorioDeUsuarios()); break;
            case "MiembrosYUsuarios": controller = new MiembrosController(new RepositorioMiembro(), new RepositorioDeUsuarios(), new RepositorioComunidad()); break;
            case "Comunidades": controller = new ComunidadesController(new RepositorioComunidad(), new RepositorioMiembro()); break;
            case "Entidades": controller = new EntidadesController(new RepositorioEntidad()); break;
            case "Establecimientos": controller = new EstablecimientosController(new RepositorioEstablecimiento()); break;
            case "OrganismosDeControl": controller = new OrganismosDeControlController(new RepositorioOrganismoDeControl()); break;
            case "EntidadesPrestadoras": controller = new EntidadesPrestadorasController(new RepositorioEntidadPrestadora()); break;
            case "InicioSesion": controller =  new InicioDeSesionController(new RepositorioDeUsuarios(), new RepositorioMiembro());break;
            case "Rankings": controller = new RankingsController(new RepositorioIncidente());break;
            case "EntidadesYOrganismos": controller = new EntidadesYOrganismosController(new RepositorioOrganismoDeControl(),new RepositorioEntidadPrestadora());break;
        }

        return controller;
    }
}
