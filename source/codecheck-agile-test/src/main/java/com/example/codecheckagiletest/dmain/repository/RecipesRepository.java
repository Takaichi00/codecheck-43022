package com.example.codecheckagiletest.dmain.repository;

import com.example.codecheckagiletest.dmain.entity.RecipesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}.
 */
@Repository
public interface RecipesRepository extends JpaRepository<RecipesEntity, Integer> {

}
