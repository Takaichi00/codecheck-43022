package com.example.codecheckagiletest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends JpaRepository<RecipesEntity,Integer>{

}
