package domain.entidades.generadorRankings.exportables;

import java.util.*;

public class Documento implements Exportable{
    private Map<String, List<String>> datos;

    public Documento(){
        this.datos = new HashMap<String, List<String>>();
    }

    public void agregarDato(String clave, String ... valor){
        this.agregarClaveSiNoExiste(clave);
        Collections.addAll(this.datos.get(clave), valor);
    }

    private void agregarClaveSiNoExiste(String clave){
        if(!this.datos.containsKey(clave)){
            this.datos.put(clave, new ArrayList<String>());
        }
    }

    public Map<String, List<String>> datos() {
        return this.datos;
    }
}
