package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
    indices = {
        @Index(value = {"name"}, unique = true),
        @Index(value = {"ICAO_ID"}, unique = true)
    }
)public class Airport {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  private String name;

  @NonNull
  @ColumnInfo(name = "ICAO_ID", collate = ColumnInfo.NOCASE)
  private String icaoId;

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
  public String getIcaoId() {
    return icaoId;
  }

  public void setIcaoId(@NonNull String icaoId) {
    this.icaoId = icaoId;
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
    return icaoId + " " + name;
  }
}
