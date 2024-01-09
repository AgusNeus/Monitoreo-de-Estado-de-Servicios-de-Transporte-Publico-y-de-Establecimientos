package componentesExternos.microservicioG16.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.entidades.comunidad.Comunidad;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MicroservicioG16 implements MicroservicioAdapter {

    public MicroservicioG16() {}

    @Override
    public Comunidad getComunidad(String comunidadId) {
        return null;
    }

    @Override
    public List<Comunidad> sendCommunitiesToApi(Comunidad comunidad, List<Comunidad> comunidades) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payload = new HashMap<>();
        try {
            payload.put("comunidad", comunidad);
            payload.put("lista_comunidades", comunidades);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al agregar comunidad y lista de comunidades al payload");
            return null;
        }

        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Error al convertir el payload a JSON");
            return null;
        }

        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8000/propose_fusion/", request, String.class);

            if (response.getStatusCodeValue() == 200) {
                System.out.println("Solicitud POST exitosa: " + response.getBody());
                List<Comunidad> comunidadesList = objectMapper.readValue(response.getBody(), new TypeReference<List<Comunidad>>(){});
                return comunidadesList;

            } else {
                System.out.println("Error en la solicitud POST: " + response.getStatusCodeValue());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excepcion al realizar la solicitud POST");
            return null;
        }
    }
}


