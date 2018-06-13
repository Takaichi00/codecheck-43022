package com.example.codecheckagiletest.dmain.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "recipes")
public class RecipesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private int id;

  @Column
  private String title;

  @Column(name = "making_time")
  private String makingTime;

  @Column
  private String serves;

  @Column
  private String ingredients;

  @Column
  private int cost;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Date updatedAt;

}
