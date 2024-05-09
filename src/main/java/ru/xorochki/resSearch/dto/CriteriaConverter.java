package ru.xorochki.resSearch.dto;

import org.mapstruct.Mapper;
import ru.xorochki.resSearch.model.Criteria;

@Mapper(componentModel = "spring")
public interface CriteriaConverter {
    CriteriaResponse criteriaConvertToCriteriaResponse(Criteria criteria);
}
