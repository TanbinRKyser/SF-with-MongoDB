package com.tuskar.sfgrecipe2.repositories;

import com.tuskar.sfgrecipe2.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure,String> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
