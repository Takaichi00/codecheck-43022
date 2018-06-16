package com.example.codecheckagiletest.dmain.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

/**
 * 作成日時と更新日時のエンティティ.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class TimestampEntity {

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  @PrePersist
  public void onPrePersist() {
    setCreatedAt(new Date());
    setUpdatedAt(new Date());
  }

  @PreUpdate
  public void onPreUpdate() {
    setUpdatedAt(new Date());
  }
}
