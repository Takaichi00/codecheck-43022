package com.example.codecheckagiletest.dmain.model;

import java.util.List;
import lombok.Data;

/**
 * レシピをすべて取得するときのレスポンスDTO.
 */
@Data
public class ResponseGetAllRecipesDto {

  private List<RecipesDto> recipes;

}
