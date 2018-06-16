package com.example.codecheckagiletest.dmain.model;

import java.util.List;
import lombok.Data;

/**
 * レシピ新規作成に成功したときのレスポンスDTO.
 */
@Data
public class ResponseCreateRecipesSuccessDto {

  private String message;

  private List<RecipeDto> recipe;

}
