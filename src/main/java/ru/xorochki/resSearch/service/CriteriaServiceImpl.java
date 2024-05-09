package ru.xorochki.resSearch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaCriteriaRepository;
import ru.xorochki.resSearch.dto.CriteriaConverter;
import ru.xorochki.resSearch.dto.CriteriaResponse;
import ru.xorochki.resSearch.dto.RestaurantConverter;
import ru.xorochki.resSearch.dto.RestaurantResponse;
import ru.xorochki.resSearch.model.Criteria;
import ru.xorochki.resSearch.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CriteriaServiceImpl implements CriteriaService {

    private final JpaCriteriaRepository criteriaRepository;
    private final CriteriaConverter criteriaConverter;
    private final RestaurantService restaurantService;
    private final RestaurantConverter restaurantConverter;

    @Override
    public List<CriteriaResponse> getAllCriteria() {
        log.info("Retrieving all criteria");
        List<Criteria> criteriaList = criteriaRepository.findAll();
        List<CriteriaResponse> criteriaResponses = criteriaList.stream()
                .map(criteriaConverter::criteriaConvertToCriteriaResponse)
                .collect(Collectors.toList());
        log.info("Retrieved {} criteria", criteriaResponses.size());
        return criteriaResponses;
    }

    @Override
    public List<RestaurantResponse> findAllByIds(List<Long> criteriaIds) {
        log.info("Finding restaurants by criteria IDs: {}", criteriaIds);
        List<Criteria> criteria = criteriaRepository.findAllById(criteriaIds);
        List<Restaurant> restaurants = restaurantService.findByAllCriteria(criteria);
        List<RestaurantResponse> restaurantResponses = restaurantConverter.restaurantConvertToRestaurantResponses(restaurants);
        log.info("Found {} restaurants by criteria IDs: {}", restaurantResponses.size(), criteriaIds);
        return restaurantResponses;
    }

}
