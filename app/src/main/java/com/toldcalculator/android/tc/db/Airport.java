package com.toldcalculator.android.tc.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Airport {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private String name;

  @NonNull
  private String ICAO;

  @NonNull
  private int elevation;

  @NonNull
  private String runways;

  public long getId() {
    return id;
  }

  public void setId(long id) {
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
  public String getICAO() {
    return ICAO;
  }

  public void setICAO(@NonNull String ICAO) {
    this.ICAO = ICAO;
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
    return ICAO + " " + name;
  }
}
