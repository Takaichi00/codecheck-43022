package com.example.codecheckagiletest.dmain.model;

import java.util.List;
import lombok.Data;

/**
 * レシピを更新するときのレスポンスDTO.
 */
@Data
public class ResponseUpdateRecipe {

  private String message;

  private List<RecipeDto> recipe;
}
