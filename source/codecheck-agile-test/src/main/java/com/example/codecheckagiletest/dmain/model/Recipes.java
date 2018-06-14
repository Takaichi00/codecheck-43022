package com.example.codecheckagiletest.dmain.model;

import lombok.Data;

@Data
public class Recipes {
  private int id;

  private String title;

  private String making_time;

  private String serves;

  private String ingredients;

  private String cost;
}
