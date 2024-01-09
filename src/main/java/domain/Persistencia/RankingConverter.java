package domain.Persistencia;

import domain.entidades.Rankings.MayorCantidadReportes;
import domain.entidades.Rankings.MayorGradoImpacto;
import domain.entidades.Rankings.MayorPromedioCierre;
import domain.entidades.Rankings.Ranking;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RankingConverter implements AttributeConverter<Ranking, String> {
    @Override
    public String convertToDatabaseColumn(Ranking ranking) {
        return ranking.getClass().getName();
    }
    
    @Override
    public Ranking convertToEntityAttribute(String ranking) {
        Ranking ranking1 = null;

        if ("MayorCantidadReportes".equals(ranking)) {
            ranking1 = new MayorCantidadReportes();
        } else if ("MayorGradoImpacto".equals(ranking)) {
            ranking1 = new MayorGradoImpacto();
        } else if ("MayorPromedioCierre".equals(ranking)) {
            ranking1 = new MayorPromedioCierre();
        }

        return ranking1;
    }

}