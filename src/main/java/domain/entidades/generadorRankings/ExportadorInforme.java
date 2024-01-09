package domain.entidades.generadorRankings;

import domain.entidades.servicios.Entidad;

import java.util.List;

public interface ExportadorInforme {

   public void exportarInforme(List<Entidad> listaRanking);
}
