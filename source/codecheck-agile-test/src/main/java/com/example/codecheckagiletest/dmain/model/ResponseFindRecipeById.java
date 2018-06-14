package com.example.codecheckagiletest.dmain.model;

import java.util.List;
import lombok.Data;

@Data
public class ResponseFindRecipeById {
  String message;
  List<Recipe> recipe;
}
