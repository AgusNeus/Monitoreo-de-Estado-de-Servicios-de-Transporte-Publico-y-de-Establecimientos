package domain.entidades.generadorRankings.estrategiasExportacion.pdf;

import domain.entidades.generadorRankings.exportables.Exportable;

public interface AdapterExportadorPDF {
    public String exportar(Exportable exportable);
}