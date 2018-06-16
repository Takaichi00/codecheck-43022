package com.example.codecheckagiletest.dmain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * recipeテーブルのエンティティ.
 */
@Getter
@Setter
@Entity
@Table(name = "recipes")
public class RecipesEntity extends TimestampEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  @NotNull
  private int id;

  @Column
  @NotNull
  private String title;

  @Column(name = "making_time")
  @NotNull
  private String makingTime;

  @Column
  @NotNull
  private String serves;

  @Column
  @NotNull
  private String ingredients;

  @Column
  @NotNull
  private int cost;

}
