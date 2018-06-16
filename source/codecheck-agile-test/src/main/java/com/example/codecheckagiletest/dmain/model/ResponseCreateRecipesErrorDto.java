package com.example.codecheckagiletest.dmain.model;

import lombok.Data;

/**
 * レシピ新規作成に失敗したときのレスポンスDTO.
 */
@Data
public class ResponseCreateRecipesErrorDto {

  private String message;

  private String required;
}
