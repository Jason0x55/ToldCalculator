package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Airport {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private String name;

  @ColumnInfo(name = "ICAO_ID", collate = ColumnInfo.NOCASE)
  private String icaoId;

  private String ident;

  @NonNull
  private int elevation;

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
  public String getIcaoId() {
    return icaoId;
  }

  public void setIcaoId(@NonNull String icaoId) {
    this.icaoId = icaoId;
  }

  public String getIdent() {
    return ident;
  }

  public void setIdent(String ident) {
    this.ident = ident;
  }

  @NonNull
  public int getElevation() {
    return elevation;
  }

  public void setElevation(@NonNull int elevation) {
    this.elevation = elevation;
  }

  @Override
  public String toString() {
    return icaoId + " " + name;
  }
}
