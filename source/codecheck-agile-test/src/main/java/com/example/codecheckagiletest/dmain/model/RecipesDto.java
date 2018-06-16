package com.example.codecheckagiletest.dmain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * idを含んだレシピ内容のDTO.
 */
@Data
public class RecipesDto {

  private int id;

  private String title;

  @JsonProperty("making_time")
  private String makingTime;

  private String serves;

  private String ingredients;

  private String cost;
}
