package com.example.codecheckagiletest.application;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.codecheckagiletest.dmain.entity.RecipesEntity;
import com.example.codecheckagiletest.dmain.model.RecipeDto;
import com.example.codecheckagiletest.dmain.service.RecipesService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RecipesControllerTest {

  private MockMvc mockMvc;

  @Mock
  private RecipesService recipesService;

  @InjectMocks
  private RecipesController recipesController;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(recipesController).build();
  }

  @After
  public void tearDown() throws Exception {}

  private void setRecipesEntity1(RecipesEntity recipesEntity) {
    recipesEntity.setId(1);
    recipesEntity.setTitle("title1");
    recipesEntity.setMakingTime("10分");
    recipesEntity.setServes("1人");
    recipesEntity.setIngredients("材料1");
    recipesEntity.setCost(100);
  }

  private void setRecipesEntity2(RecipesEntity recipesEntity) {
    recipesEntity.setId(2);
    recipesEntity.setTitle("title2");
    recipesEntity.setMakingTime("20分");
    recipesEntity.setServes("2人");
    recipesEntity.setIngredients("材料2");
    recipesEntity.setCost(200);
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test_POSTでreceipeにアクセスするとcreateRecipesが実行される() throws Exception {
    // テストデータ準備
    RecipeDto recipe = new RecipeDto();
    recipe.setTitle("title1");
    recipe.setMakingTime("10分");
    recipe.setServes("1人");
    recipe.setIngredients("材料1");
    recipe.setCost("100");

    doNothing().when(recipesService).createRecipe(Mockito.any(RecipesEntity.class));
    // 実行
    mockMvc
        .perform(
            post("/recipes").contentType(MediaType.APPLICATION_JSON).content(asJsonString(recipe)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.message", is("Recipe successfully created!")))
        .andExpect(jsonPath("$.recipe[0].title", is("title1")));

    // 確認
    verify(recipesService, times(1)).createRecipe(Mockito.any(RecipesEntity.class));
    verifyNoMoreInteractions(recipesService);

  }


  @Test
  public void test_必要な項目がnullでPOSTでreceipeにアクセスするとcreateErrorが実行される() throws Exception {
    // テストデータ準備
    RecipeDto recipe = new RecipeDto();
    recipe.setTitle(null);
    recipe.setMakingTime("10分");
    recipe.setServes("1人");
    recipe.setIngredients("材料1");
    recipe.setCost("200");

    doNothing().when(recipesService).createRecipe(Mockito.any(RecipesEntity.class));
    // 実行
    mockMvc
        .perform(
            post("/recipes").contentType(MediaType.APPLICATION_JSON).content(asJsonString(recipe)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.message", is("Recipe creation failed!")))
        .andExpect(jsonPath("$.required", is("title, making_time, serves, ingredients, cost")));

    verify(recipesService, times(0)).createRecipe(Mockito.any(RecipesEntity.class));
    verifyNoMoreInteractions(recipesService);
  }

  @Test
  public void test_GETでrecipeにアクセスするとgetAllRecipesが実行される() throws Exception {
    // テストデータ準備
    RecipesEntity recipesEntity = new RecipesEntity();
    setRecipesEntity1(recipesEntity);

    List<RecipesEntity> recipesEntityList = new ArrayList<>();
    recipesEntityList.add(recipesEntity);

    recipesEntity = new RecipesEntity();
    setRecipesEntity2(recipesEntity);

    recipesEntityList.add(recipesEntity);

    // 振る舞い設定
    when(recipesService.findAllRecipes()).thenReturn(recipesEntityList);

    // 実行とレスポンスのjson確認
    mockMvc.perform(get("/recipes")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.recipes", hasSize(2))).andExpect(jsonPath("$.recipes[0].id", is(1)))
        .andExpect(jsonPath("$.recipes[0].title", is("title1")))
        .andExpect(jsonPath("$.recipes[1].id", is(2)))
        .andExpect(jsonPath("$.recipes[1].title", is("title2")));

    // 確認
    verify(recipesService, times(1)).findAllRecipes();
    verifyNoMoreInteractions(recipesService);
  }

  @Test
  public void test_GETでパラメータにidがしていた場合にgetRecipeFindByIdが実行される() throws Exception {

    // テストデータ設定
    RecipesEntity recipesEntity = new RecipesEntity();
    setRecipesEntity1(recipesEntity);

    // 振る舞い設定
    when(recipesService.findRecipeById(1)).thenReturn(recipesEntity);

    // 実行
    mockMvc.perform(get("/recipes/{id}", 1)).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.message", is("Recipe details by id")))
        .andExpect(jsonPath("$.recipe[0].title", is("title1")));

    // 確認
    verify(recipesService, times(1)).findRecipeById(1);
    verifyNoMoreInteractions(recipesService);
  }

  @Test
  public void test_PATCHでパラメータにidが指定されていた場合にupdateRecipeが実行される() throws Exception {
    // テストデータ準備
    RecipeDto recipe = new RecipeDto();
    recipe.setTitle("title1");
    recipe.setMakingTime("10分");
    recipe.setServes("1人");
    recipe.setIngredients("材料1");
    recipe.setCost("100");

    // 振る舞い設定
    doNothing().when(recipesService).updateRecipeById(Mockito.any(RecipesEntity.class));

    // 実行と確認
    mockMvc
        .perform(patch("/recipes/{id}", 1).contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(recipe)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.message", is("Recipe successfully updated!")))
        .andExpect(jsonPath("$.recipe[0].title", is("title1")));

    // 確認
    verify(recipesService, times(1)).updateRecipeById(Mockito.any(RecipesEntity.class));
    verifyNoMoreInteractions(recipesService);
  }

  @Test
  public void test_DELETEでパラメータにidが指定されてレシピが存在した場合にdeleteRecipeByIdが実行されて指定のメッセージが返却される()
      throws Exception {
    // 振る舞い設定
    when(recipesService.existsById(1)).thenReturn(true);
    doNothing().when(recipesService).deleteRecipeById(1);

    // 実行と確認
    mockMvc.perform(delete("/recipes/{id}", 1)).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.message", is("Recipe successfully removed!")));

    // 確認
    verify(recipesService, times(1)).existsById(1);
    verify(recipesService, times(1)).deleteRecipeById(1);
    verifyNoMoreInteractions(recipesService);
  }

  @Test
  public void test_DELETEでパラメータにidが指定されてレシピが存在しなかった場合にdeleteRecipeByIdが実行されて指定のメッセージが返却される()
      throws Exception {
    // 振る舞い設定
    when(recipesService.existsById(1)).thenReturn(false);
    doNothing().when(recipesService).deleteRecipeById(1);

    // 実行と確認
    mockMvc.perform(delete("/recipes/{id}", 1)).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.message", is("No Recipe found")));

    // 確認
    verify(recipesService, times(1)).existsById(1);
    verifyNoMoreInteractions(recipesService);
  }

}
