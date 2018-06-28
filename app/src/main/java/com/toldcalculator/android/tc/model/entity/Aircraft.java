package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Aircraft {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  @NonNull
  private String aircraftType;

  @NonNull
  private int basicEmptyWeight;

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

  @NonNull
  public String getAircraftType() {
    return aircraftType;
  }

  public void setAircraftType(@NonNull String aircraftType) {
    this.aircraftType = aircraftType;
  }

  @NonNull
  public int getBasicEmptyWeight() {
    return basicEmptyWeight;
  }

  public void setBasicEmptyWeight(@NonNull int basicEmptyWeight) {
    this.basicEmptyWeight = basicEmptyWeight;
  }

  @Override
  public String toString() {
    return name + " : " + aircraftType;
  }
}
