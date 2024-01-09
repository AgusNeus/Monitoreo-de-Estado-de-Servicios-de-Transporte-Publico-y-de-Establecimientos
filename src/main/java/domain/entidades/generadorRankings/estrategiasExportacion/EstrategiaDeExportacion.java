package domain.entidades.generadorRankings.estrategiasExportacion;

import domain.entidades.generadorRankings.exportables.Exportable;

public interface EstrategiaDeExportacion {
    public String exportar(Exportable exportable);
}
