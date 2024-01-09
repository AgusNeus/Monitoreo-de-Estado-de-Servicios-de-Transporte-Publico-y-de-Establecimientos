package componentesExternos.microservicioG16.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.entidades.comunidad.Comunidad;
import org.apache.http.HttpEntity;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MicroservicioAdapter {


    Comunidad getComunidad(String comunidadId);

    List<Comunidad> sendCommunitiesToApi(Comunidad comunidad, List<Comunidad> comunidades);



}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
