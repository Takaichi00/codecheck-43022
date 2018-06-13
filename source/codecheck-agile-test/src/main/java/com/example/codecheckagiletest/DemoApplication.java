package com.example.codecheckagiletest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.codecheckagiletest.dmain.model.RecipesEntity;
import com.example.codecheckagiletest.dmain.service.RecipesService;

@RestController
@SpringBootApplication
public class DemoApplication {

  @Autowired
  RecipesService recipesService;

  @RequestMapping("/")
  public String home() {

    List<RecipesEntity> hoge = recipesService.findAll();

    return hoge.get(0).getTitle();
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
