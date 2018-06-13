package com.example.codecheckagiletest.dmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.codecheckagiletest.dmain.model.RecipesEntity;

@Repository
public interface RecipesRepository extends JpaRepository<RecipesEntity,Long>{

}
