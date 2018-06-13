package com.example.codecheckagiletest.dmain.model;

import javax.persistence.Column;
import lombok.Data;

@Data
public class CreateRecepesRequestDto {

  private String title;

  @Column(name = "making_time")
  private String makingTime;

  private String serves;

  private String ingredients;

  private int cost;
}
