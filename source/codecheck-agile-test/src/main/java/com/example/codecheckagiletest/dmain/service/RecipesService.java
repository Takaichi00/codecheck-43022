package com.example.codecheckagiletest.dmain.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.codecheckagiletest.dmain.model.RecipesEntity;
import com.example.codecheckagiletest.dmain.repository.RecipesRepository;

@Service
public class RecipesService {

  @Autowired
  RecipesRepository receipesRepository;

  public List<RecipesEntity> findAll() {
    return receipesRepository.findAll();
  }

}
