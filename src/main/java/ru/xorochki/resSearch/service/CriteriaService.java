package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.dto.CriteriaResponse;
import ru.xorochki.resSearch.dto.RestaurantResponse;
import ru.xorochki.resSearch.model.Criteria;
import ru.xorochki.resSearch.model.Restaurant;

import java.util.List;

public interface CriteriaService {

    List<CriteriaResponse> getAllCriteria();

    List<RestaurantResponse> findAllByIds(List<Long> ids);
}
