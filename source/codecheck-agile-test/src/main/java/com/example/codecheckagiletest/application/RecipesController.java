package com.example.codecheckagiletest.application;

import com.example.codecheckagiletest.dmain.entity.RecipesEntity;
import com.example.codecheckagiletest.dmain.model.RecipeDto;
import com.example.codecheckagiletest.dmain.model.RecipesDto;
import com.example.codecheckagiletest.dmain.model.ResponseCreateRecipesErrorDto;
import com.example.codecheckagiletest.dmain.model.ResponseCreateRecipesSuccessDto;
import com.example.codecheckagiletest.dmain.model.ResponseDeleteRecipeByIdDto;
import com.example.codecheckagiletest.dmain.model.ResponseFindRecipeById;
import com.example.codecheckagiletest.dmain.model.ResponseGetAllRecipesDto;
import com.example.codecheckagiletest.dmain.model.ResponseUpdateRecipe;
import com.example.codecheckagiletest.dmain.service.RecipesService;
import com.example.codecheckagiletest.exception.RequestBadRecipeException;
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

@RestController
@RequestMapping("/recipes")
public class RecipesController {

  @Autowired
  private RecipesService recipesService;

  /**
   * レシピを新規作成する.
   *
   * @param recipe 作成したいレシピのタイトル、調理時間、人数、材料、費用
   * @return 作成に成功したメッセージと作成したレシピ
   * @throws RequestBadRecipeException リクエストにnull値が入っていた場合にスローされる
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseCreateRecipesSuccessDto createRecipes(@RequestBody RecipeDto recipe)
      throws RequestBadRecipeException {

    if (recipe.getTitle() == null) {
      throw new RequestBadRecipeException();
    }
    if (recipe.getMakingTime() == null) {
      throw new RequestBadRecipeException();
    }
    if (recipe.getServes() == null) {
      throw new RequestBadRecipeException();
    }
    if (recipe.getIngredients() == null) {
      throw new RequestBadRecipeException();
    }
    if (recipe.getCost() == null) {
      throw new RequestBadRecipeException();
    }

    RecipesEntity recipesEntity = new RecipesEntity();
    recipesEntity.setTitle(recipe.getTitle());
    recipesEntity.setMakingTime(recipe.getMakingTime());
    recipesEntity.setServes(recipe.getServes());
    recipesEntity.setIngredients(recipe.getIngredients());
    recipesEntity.setCost(Integer.parseInt(recipe.getCost()));
    recipesService.createRecipe(recipesEntity);

    ResponseCreateRecipesSuccessDto responseCreateRecipesSuccessDto =
        new ResponseCreateRecipesSuccessDto();
    responseCreateRecipesSuccessDto.setMessage("Recipe successfully created!");
    responseCreateRecipesSuccessDto.setRecipe(new ArrayList<RecipeDto>());
    responseCreateRecipesSuccessDto.getRecipe().add(recipe);
    return responseCreateRecipesSuccessDto;
  }

  /**
   * レシピ作成に失敗したときにエラーメッセージを返却する.
   * @return 失敗したメッセージと失敗内容
   */
  @ExceptionHandler({RequestBadRecipeException.class})
  @ResponseBody
  public ResponseCreateRecipesErrorDto createError() {
    ResponseCreateRecipesErrorDto responseCreateRecipesErrorDto =
        new ResponseCreateRecipesErrorDto();
    responseCreateRecipesErrorDto.setMessage("Recipe creation failed!");
    responseCreateRecipesErrorDto.setRequired("title, making_time, serves, ingredients, cost");
    return responseCreateRecipesErrorDto;

  }

  /**
   * データベースの全てのレシピをを返却する.
   * @return 取得できたレシピ一覧
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResponseGetAllRecipesDto getAllRecipes() {
    ResponseGetAllRecipesDto responseGetAllRecipesDto = new ResponseGetAllRecipesDto();

    List<RecipesDto> recipesList = new ArrayList<>();

    List<RecipesEntity> recipesEntities = recipesService.findAllRecipes();

    for (RecipesEntity recipesEntity : recipesEntities) {
      RecipesDto recipes = new RecipesDto();
      recipes.setId(recipesEntity.getId());
      recipes.setTitle(recipesEntity.getTitle());
      recipes.setMakingTime(recipesEntity.getMakingTime());
      recipes.setServes(recipesEntity.getServes());
      recipes.setIngredients(recipesEntity.getIngredients());
      recipes.setCost(Integer.toString(recipesEntity.getCost()));

      recipesList.add(recipes);
    }

    responseGetAllRecipesDto.setRecipes(recipesList);

    return responseGetAllRecipesDto;
  }

  /**
   * 指定idのレシピのみを返却する.
   * @param id 取得したいレシピのid
   * @return 取得できたidのレシピ
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseFindRecipeById getRecipeFindById(@PathVariable int id) {
    RecipesEntity recipeEntity = recipesService.findRecipeById(id);

    RecipeDto recipe = new RecipeDto();
    recipe.setTitle(recipeEntity.getTitle());
    recipe.setMakingTime(recipeEntity.getMakingTime());
    recipe.setServes(recipeEntity.getServes());
    recipe.setIngredients(recipeEntity.getIngredients());
    recipe.setCost(Integer.toString(recipeEntity.getCost()));

    ResponseFindRecipeById responseFindRecipeById = new ResponseFindRecipeById();
    responseFindRecipeById.setMessage("Recipe details by id");

    responseFindRecipeById.setRecipe(new ArrayList<RecipeDto>());
    responseFindRecipeById.getRecipe().add(recipe);

    return responseFindRecipeById;
  }

  /**
   * 指定idのレシピを更新し、更新したレシピを返却する.
   * @param recipe 更新したいレシピ
   * @param id 更新したいレシピのid
   * @return 更新に成功したメッセージと更新したレシピ内容
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseUpdateRecipe updateRecipe(@RequestBody RecipeDto recipe, @PathVariable int id) {
    RecipesEntity recipesEntity = new RecipesEntity();
    recipesEntity.setId(id);
    recipesEntity.setTitle(recipe.getTitle());
    recipesEntity.setMakingTime(recipe.getMakingTime());
    recipesEntity.setServes(recipe.getServes());
    recipesEntity.setIngredients(recipe.getIngredients());
    recipesEntity.setCost(Integer.parseInt(recipe.getCost()));

    recipesService.updateRecipeById(recipesEntity);

    ResponseUpdateRecipe responseUpdateRecipe = new ResponseUpdateRecipe();
    responseUpdateRecipe.setRecipe(new ArrayList<RecipeDto>());
    responseUpdateRecipe.getRecipe().add(recipe);
    responseUpdateRecipe.setMessage("Recipe successfully updated!");

    return responseUpdateRecipe;
  }

  /**
   * 指定idのレシピを削除する.
   * @param id 削除したいレシピid
   * @return 削除に成功した場合は成功したメッセージ、指定idのレシピが存在せずに失敗した場合は失敗メッセージ
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseDeleteRecipeByIdDto deleteRecipeById(@PathVariable int id) {

    ResponseDeleteRecipeByIdDto responseDeleteRecipeByIdDto = new ResponseDeleteRecipeByIdDto();

    if (!recipesService.existsById(id)) {
      responseDeleteRecipeByIdDto.setMessage("No Recipe found");
      return responseDeleteRecipeByIdDto;
    }

    recipesService.deleteRecipeById(id);
    responseDeleteRecipeByIdDto.setMessage("Recipe successfully removed!");

    return responseDeleteRecipeByIdDto;

  }
}
