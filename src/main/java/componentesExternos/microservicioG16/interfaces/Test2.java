package componentesExternos.microservicioG16.interfaces;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.entidades.comunidad.Miembro;
import domain.entidades.comunidad.Comunidad;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.*;

public class Test2 {
    List<Comunidad> comunidades;
    Comunidad comunidad;
    public static void main(String[] args) {

        Comunidad comunidad = new Comunidad();
        comunidad.setNombre("COMUNIDAD1");

        Comunidad comunidad2 = new Comunidad();
        comunidad2.setNombre("COMUNIDAD2");

        Comunidad comunidad3 = new Comunidad();
        comunidad3.setNombre("COMUNIDAD3");

        Miembro miembro = new Miembro();
        miembro.setNombre("Carlos");

        Miembro miembro2 = new Miembro();
        miembro2.setNombre("Jose");

        Miembro miembro3 = new Miembro();
        miembro3.setNombre("Luis");

        List<Miembro> miembros = new ArrayList<>();
        miembros.add(miembro);
        miembros.add(miembro2);
        miembros.add(miembro3);

        List<Miembro> miembros2 = new ArrayList<>();
        miembros2.add(miembro);
        miembros2.add(miembro2);

        comunidad.setMiembros(miembros);
        comunidad2.setMiembros(miembros);
        comunidad3.setMiembros(miembros2);

        Float gradoConfianza = 0.5f ;
        Float gradoConfianza2 = 0.2f ;

        comunidad.setGradoDeConfianza(gradoConfianza);
        comunidad2.setGradoDeConfianza(gradoConfianza);
        comunidad3.setGradoDeConfianza(gradoConfianza);

        List<Comunidad> comunidades = new ArrayList<>();
        comunidades.add(comunidad);
        comunidades.add(comunidad2);
        comunidades.add(comunidad3);

        comunidad.agregarServiciosParaAPI();
        comunidad.agregarEstablecimientosParaAPI();

        comunidad2.agregarServiciosParaAPI();
        comunidad2.agregarEstablecimientosParaAPI();

        comunidad3.agregarServiciosParaAPI();
        comunidad3.agregarEstablecimientosParaAPI();

        MicroservicioG16 fusionComunidades = new MicroservicioG16();


        List<Comunidad> comunidadesRetornadas = fusionComunidades.sendCommunitiesToApi(comunidad, comunidades);

        if (comunidadesRetornadas != null) {
            System.out.println("Comunidades retornadas por el servidor:");
            for (Comunidad c : comunidadesRetornadas) {
                System.out.println(c.getNombre());
            }
        }
    }
    public List<Comunidad> sendCommunitiesToApi(Comunidad comunidad, List<Comunidad> comunidades) throws JsonProcessingException {
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

        String jsonPayload = objectMapper.writeValueAsString(payload);
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
                Comunidad[] comunidadesArray = objectMapper.readValue(response.getBody(), Comunidad[].class);
                return Arrays.asList(comunidadesArray);

            } else {
                System.out.println("Error en la solicitud POST: " + response.getStatusCodeValue());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ExcepciÃ³n al realizar la solicitud POST");
            return null;
        }
    }
}