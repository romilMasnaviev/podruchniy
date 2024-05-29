package ru.xorochki.resSearch.dto;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.xorochki.resSearch.model.Criteria;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-29T23:04:31+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CriteriaConverterImpl implements CriteriaConverter {

    @Override
    public CriteriaResponse criteriaConvertToCriteriaResponse(Criteria criteria) {
        if ( criteria == null ) {
            return null;
        }

        CriteriaResponse criteriaResponse = new CriteriaResponse();

        criteriaResponse.setId( criteria.getId() );
        criteriaResponse.setName( criteria.getName() );

        return criteriaResponse;
    }
}
