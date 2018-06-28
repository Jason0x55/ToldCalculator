package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Airport {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  private String name;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String ICAO_ID;

  @NonNull
  private int elevation;

  @NonNull
  private String runways;

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
  public String getICAO_ID() {
    return ICAO_ID;
  }

  public void setICAO_ID(@NonNull String ICAO_ID) {
    this.ICAO_ID = ICAO_ID;
  }

  @NonNull
  public int getElevation() {
    return elevation;
  }

  public void setElevation(@NonNull int elevation) {
    this.elevation = elevation;
  }

  @NonNull
  public String getRunways() {
    return runways;
  }

  public void setRunways(@NonNull String runways) {
    this.runways = runways;
  }

  @Override
  public String toString() {
    return ICAO_ID + " " + name;
  }
}
