package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  private Long aircraft;

  private Long airport;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public Long getAircraft() {
    return aircraft;
  }

  public void setAircraft(Long aircraft) {
    this.aircraft = aircraft;
  }

  public Long getAirport() {
    return airport;
  }

  public void setAirport(Long airport) {
    this.airport = airport;
  }

}
