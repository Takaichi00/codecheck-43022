package com.example.codecheckagiletest.dmain.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.codecheckagiletest.dao.RecipesEntity;
import com.example.codecheckagiletest.dao.RecipesRepository;

@Service
public class RecipesService {

  @Autowired
  RecipesRepository recipesRepository;

  public List<RecipesEntity> findAllRecipes() {
    return recipesRepository.findAll();
  }

  public void createRecipe(RecipesEntity recipesEntity) {
    recipesRepository.save(recipesEntity);
  }

  public RecipesEntity findRecipeById(int id) {
    Optional<RecipesEntity> result = recipesRepository.findById(id);
    return result.get();
  }

  public void updateRecipeById(RecipesEntity recipesEntity) {
    recipesRepository.save(recipesEntity);
  }

  public void deleteRecipeById(int id) {
    recipesRepository.deleteById(id);
  }

  public boolean existsById(int id) {
    return recipesRepository.existsById(id);
  }

}
