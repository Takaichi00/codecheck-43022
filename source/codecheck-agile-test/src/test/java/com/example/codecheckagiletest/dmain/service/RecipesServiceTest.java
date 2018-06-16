package com.example.codecheckagiletest.dmain.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.codecheckagiletest.dmain.entity.RecipesEntity;
import com.example.codecheckagiletest.dmain.repository.RecipesRepository;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipesServiceTest {

  @Autowired
  private RecipesService testTarget;

  @Autowired
  private RecipesRepository receipesRepository;


  public static final Operation DELETE_ALL = Operations.deleteAllFrom("recipes");

  public static final Operation INSERT =
      Operations.insertInto("recipes")
          .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1).incrementingBy(1))
          .columns("title", "making_time", "serves", "ingredients", "cost", "created_at",
              "updated_at")
          .values("チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", 1000, "2016-01-10 12:10:12",
              "2016-01-10 12:10:12")
          .values("オムライス", "30分", "2人", "玉ねぎ,卵,スパイス,醤油", 700, "2016-01-11 13:10:12",
              "2016-01-11 13:10:12")
          .build();

  @Autowired
  private DataSource dataSource;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {
    Destination dest = new DataSourceDestination(dataSource);
    Operation ops = Operations.sequenceOf(DELETE_ALL, INSERT);
    DbSetup dbSetup = new DbSetup(dest, ops);
    dbSetup.launch();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test_テーブルのレコードを全件取得できる() {
    List<RecipesEntity> actual = testTarget.findAllRecipes();
    assertThat(actual.get(0).getId(), is(1));
  }

  @Test
  public void test_テーブルに新規レコードを作成できる() {
    // テストデータ用意
    RecipesEntity recipesEntity = new RecipesEntity();
    recipesEntity.setTitle("testTitle1");
    recipesEntity.setMakingTime("10分");
    recipesEntity.setServes("5人");
    recipesEntity.setIngredients("TestIngredients");
    recipesEntity.setCost(500);

    // テスト実行
    testTarget.createRecipe(recipesEntity);

    //確認
    List<RecipesEntity> actual = receipesRepository.findAll();
    assertThat(actual.size(), is(3));
    assertThat(actual.get(2).getId(), is(3));
    assertThat(actual.get(2).getTitle(), is("testTitle1"));
    assertThat(actual.get(2).getMakingTime(), is("10分"));
    assertThat(actual.get(2).getServes(), is("5人"));
    assertThat(actual.get(2).getIngredients(), is("TestIngredients"));
    assertThat(actual.get(2).getCost(), is(500));
  }

  @Test
  public void test_idから指定のレシピを返却できる() {
    // テスト実行
    RecipesEntity actual = testTarget.findRecipeById(1);

    // 確認
    assertThat(actual.getId(), is(1));
    assertThat(actual.getTitle(), is("チキンカレー"));
    assertThat(actual.getMakingTime(), is("45分"));
    assertThat(actual.getServes(), is("4人"));
    assertThat(actual.getIngredients(), is("玉ねぎ,肉,スパイス"));
    assertThat(actual.getCost(), is(1000));
  }

  @Test
  public void test_指定したidに対応するレコードを更新する() {
    // テストデータ準備
    RecipesEntity recipesEntity = new RecipesEntity();
    recipesEntity.setId(1);
    recipesEntity.setTitle("トマトスープレシピ");
    recipesEntity.setMakingTime("15分");
    recipesEntity.setServes("5人");
    recipesEntity.setIngredients("玉ねぎ, トマト, スパイス, 水");
    recipesEntity.setCost(450);

    // テスト実行
    testTarget.updateRecipeById(recipesEntity);

    // 確認
    Optional<RecipesEntity> actualOptionalEntity = receipesRepository.findById(1);
    RecipesEntity actual = actualOptionalEntity.get();
    assertThat(actual.getId(), is(1));
    assertThat(actual.getTitle(), is("トマトスープレシピ"));
    assertThat(actual.getMakingTime(), is("15分"));
    assertThat(actual.getServes(), is("5人"));
    assertThat(actual.getIngredients(), is("玉ねぎ, トマト, スパイス, 水"));
    assertThat(actual.getCost(), is(450));
  }

  @Test
  public void test_指定したidに対応するレコードを削除する() {
    // 実行
    testTarget.deleteRecipeById(1);

    // 確認
    boolean actual = receipesRepository.existsById(1);
    assertThat(actual, is(false));
  }

  @Test
  public void test_指定したidに対応するレコードが存在するか判定できる() {
    // 実行
    boolean actual1 = testTarget.existsById(1);
    boolean actual2 = testTarget.existsById(2);
    boolean actual3 = testTarget.existsById(3);

    // 確認
    assertThat(actual1, is(true));
    assertThat(actual2, is(true));
    assertThat(actual3, is(false));
  }

}
