package domain.entidades.generadorRankings.estrategiasExportacion.pdf;

import domain.entidades.generadorRankings.estrategiasExportacion.EstrategiaDeExportacion;
import domain.entidades.generadorRankings.exportables.Exportable;

public class ExportarAPDF implements EstrategiaDeExportacion {
    private AdapterExportadorPDF adapter;

    public ExportarAPDF(AdapterExportadorPDF adapter){
        this.adapter = adapter;
    }

    public String exportar(Exportable exportable) {
        return this.adapter.exportar(exportable);
    }
}
