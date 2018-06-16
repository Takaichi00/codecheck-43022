package com.example.codecheckagiletest.dmain.model;

import java.util.List;
import lombok.Data;

/**
 * idからレシピを検索したときのレスポンスDTO.
 */
@Data
public class ResponseFindRecipeById {

  private String message;

  private List<RecipeDto> recipe;

}
