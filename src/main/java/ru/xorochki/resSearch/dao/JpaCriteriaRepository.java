package ru.xorochki.resSearch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xorochki.resSearch.model.Criteria;

public interface JpaCriteriaRepository extends JpaRepository<Criteria,Long> {

}
