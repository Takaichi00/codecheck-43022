package com.example.codecheckagiletest.dmain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * レシピ内容のDTO.
 */
@Data
public class RecipeDto {

  private String title;

  @JsonProperty("making_time")
  private String makingTime;

  private String serves;

  private String ingredients;

  private String cost;
}
