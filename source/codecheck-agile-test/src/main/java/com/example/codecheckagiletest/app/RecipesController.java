package com.example.codecheckagiletest.app;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.codecheckagiletest.dao.RecipesEntity;
import com.example.codecheckagiletest.dmain.model.Recipe;
import com.example.codecheckagiletest.dmain.model.Recipes;
import com.example.codecheckagiletest.dmain.model.ResponseCreateRecipesErrorDto;
import com.example.codecheckagiletest.dmain.model.ResponseCreateRecipesSuccessDto;
import com.example.codecheckagiletest.dmain.model.ResponseDeleteRecipeByIdDto;
import com.example.codecheckagiletest.dmain.model.ResponseFindRecipeById;
import com.example.codecheckagiletest.dmain.model.ResponseGetAllRecipesDto;
import com.example.codecheckagiletest.dmain.model.ResponseUpdateRecipe;
import com.example.codecheckagiletest.dmain.service.RecipesService;

@RestController
public class RecipesController {

  @Autowired
  RecipesService recipesService;

  @RequestMapping(value = "/recipes", method = RequestMethod.POST)
  public ResponseCreateRecipesSuccessDto createRecipes(@RequestBody Recipe recipe) throws IllegalAccessException {

    if(recipe.getTitle() == null) throw new IllegalArgumentException();
    if(recipe.getMaking_time() == null) throw new IllegalArgumentException();
    if(recipe.getServes() == null) throw new IllegalArgumentException();
    if(recipe.getIngredients() == null) throw new IllegalArgumentException();
    if(recipe.getCost() == null) throw new IllegalArgumentException();

    RecipesEntity recipesEntity = new RecipesEntity();
    recipesEntity.setTitle(recipe.getTitle());
    recipesEntity.setMakingTime(recipe.getMaking_time());
    recipesEntity.setServes(recipe.getServes());
    recipesEntity.setIngredients(recipe.getIngredients());
    recipesEntity.setCost(Integer.parseInt(recipe.getCost()));
    recipesService.createRecipe(recipesEntity);

    ResponseCreateRecipesSuccessDto responseCreateRecipesSuccessDto = new ResponseCreateRecipesSuccessDto();
    responseCreateRecipesSuccessDto.setMessage("Recipe successfully created!");
    responseCreateRecipesSuccessDto.setRecipe(new ArrayList<Recipe>());
    responseCreateRecipesSuccessDto.getRecipe().add(recipe);
    return responseCreateRecipesSuccessDto;
  }

  @ExceptionHandler({ IllegalArgumentException.class })
  @ResponseBody
  public ResponseCreateRecipesErrorDto createError() {
    ResponseCreateRecipesErrorDto responseCreateRecipesErrorDto = new ResponseCreateRecipesErrorDto();
    responseCreateRecipesErrorDto.setMessage("Recipe creation failed!");
    responseCreateRecipesErrorDto.setRequired("title, making_time, serves, ingredients, cost");
    return responseCreateRecipesErrorDto;

  }

  @RequestMapping(value = "/recipes", method = RequestMethod.GET)
  public ResponseGetAllRecipesDto getAllRecipes() {
    ResponseGetAllRecipesDto responseGetAllRecipesDto = new ResponseGetAllRecipesDto();

    List<Recipes>recipesList = new ArrayList<>();

    List<RecipesEntity> recipesEntities = recipesService.findAllRecipes();

    for(RecipesEntity recipesEntity : recipesEntities) {
      Recipes recipes = new Recipes();
      recipes.setId(recipesEntity.getId());
      recipes.setTitle(recipesEntity.getTitle());
      recipes.setMaking_time(recipesEntity.getMakingTime());
      recipes.setServes(recipesEntity.getServes());
      recipes.setIngredients(recipesEntity.getIngredients());
      recipes.setCost(Integer.toString(recipesEntity.getCost()));

      recipesList.add(recipes);
    }

    responseGetAllRecipesDto.setRecipes(recipesList);

    return responseGetAllRecipesDto;
  }

  @RequestMapping(value = "/recipes/{id}", method = RequestMethod.GET)
  public ResponseFindRecipeById getRecipeFindById(@PathVariable int id) {
    ResponseFindRecipeById responseFindRecipeById = new ResponseFindRecipeById();
    RecipesEntity recipeEntity = recipesService.findRecipeById(id);

    Recipe recipe = new Recipe();
    recipe.setTitle(recipeEntity.getTitle());
    recipe.setMaking_time(recipeEntity.getMakingTime());
    recipe.setServes(recipeEntity.getServes());
    recipe.setIngredients(recipeEntity.getIngredients());
    recipe.setCost(Integer.toString(recipeEntity.getCost()));

    responseFindRecipeById.setMessage("Recipe details by id");

    responseFindRecipeById.setRecipe(new ArrayList<Recipe>());
    responseFindRecipeById.getRecipe().add(recipe);

    return responseFindRecipeById;
  }

  @RequestMapping(value = "/recipes/{id}", method = RequestMethod.PATCH)
  public ResponseUpdateRecipe updateRecipe(@RequestBody Recipe recipe, @PathVariable int id){
    ResponseUpdateRecipe responseUpdateRecipe = new ResponseUpdateRecipe();

    RecipesEntity recipesEntity = new RecipesEntity();
    recipesEntity.setId(id);
    recipesEntity.setTitle(recipe.getTitle());
    recipesEntity.setMakingTime(recipe.getMaking_time());
    recipesEntity.setServes(recipe.getServes());
    recipesEntity.setIngredients(recipe.getIngredients());
    recipesEntity.setCost(Integer.parseInt(recipe.getCost()));

    recipesService.updateRecipeById(recipesEntity);

    responseUpdateRecipe.setRecipe(new ArrayList<Recipe>());
    responseUpdateRecipe.getRecipe().add(recipe);
    responseUpdateRecipe.setMessage("Recipe successfully updated!");

    return responseUpdateRecipe;
  }

  @RequestMapping(value = "/recipes/{id}", method = RequestMethod.DELETE)
  public ResponseDeleteRecipeByIdDto deleteRecipeById(@PathVariable int id) {

    ResponseDeleteRecipeByIdDto responseDeleteRecipeByIdDto = new ResponseDeleteRecipeByIdDto();

    if(!recipesService.existsById(id)) {
      responseDeleteRecipeByIdDto.setMessage("No Recipe found");
      return responseDeleteRecipeByIdDto;
    }

    recipesService.deleteRecipeById(id);
    responseDeleteRecipeByIdDto.setMessage("Recipe successfully removed!");

    return responseDeleteRecipeByIdDto;

  }
}
