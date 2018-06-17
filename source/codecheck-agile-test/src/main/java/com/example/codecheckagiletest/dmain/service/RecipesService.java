package com.example.codecheckagiletest.dmain.service;

import com.example.codecheckagiletest.dmain.entity.RecipesEntity;
import com.example.codecheckagiletest.dmain.repository.RecipesRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Recipeテーブルの操作を行うサービスクラス.
 */
@Service
public class RecipesService {

  @Autowired
  private RecipesRepository recipesRepository;

  /**
   * レシピの一覧を返却する.
   *
   * @return レシピエンティティのリスト
   */
  public List<RecipesEntity> findAllRecipes() {
    return recipesRepository.findAll();
  }

  /**
   * レシピを新規作成する.
   *
   * @param recipesEntity 作成したいレシピエンティティ
   */
  public void createRecipe(RecipesEntity recipesEntity) {
    recipesRepository.save(recipesEntity);
  }

  /**
   * 指定したidに対応するレシピを返却する.
   *
   * @param id 取得したいレシピid
   * @return 取得できたレシピエンティティ
   */
  public RecipesEntity findRecipeById(int id) {
    Optional<RecipesEntity> result = recipesRepository.findById(id);
    if (result.isPresent()) {
      return result.get();
    }
    return null;
  }

  /**
   * レシピを更新する.
   *
   * @param recipesEntity 更新したいレシピエンティティ
   */
  public void updateRecipeById(RecipesEntity recipesEntity) {
    recipesRepository.save(recipesEntity);
  }

  /**
   * 指定したidに対応するレシピを削除する.
   *
   * @param id 削除したいレシピのid
   */
  public void deleteRecipeById(int id) {
    recipesRepository.deleteById(id);
  }

  /**
   * 指定したidのレシピが存在するかどうかを判定する.
   *
   * @param id 存在するか判定するid
   * @return 判定するならtrue, しないならfalse
   */
  public boolean existsById(int id) {
    return recipesRepository.existsById(id);
  }

}
