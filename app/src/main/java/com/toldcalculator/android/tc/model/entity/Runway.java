package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = Airport.class,
    parentColumns = "id",
    childColumns = "airportId"))
public class Runway {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  private long airportId;

  @NonNull
  private String runwayId;

  @NonNull
  private int length;

  @NonNull
  private int width;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @NonNull
  public long getAirportId() {
    return airportId;
  }

  public void setAirportId(@NonNull long airportId) {
    this.airportId = airportId;
  }

  @NonNull
  public String getRunwayId() {
    return runwayId;
  }

  public void setRunwayId(@NonNull String runwayId) {
    this.runwayId = runwayId;
  }

  @NonNull
  public int getLength() {
    return length;
  }

  public void setLength(@NonNull int length) {
    this.length = length;
  }

  @NonNull
  public int getWidth() {
    return width;
  }

  public void setWidth(@NonNull int width) {
    this.width = width;
  }
}
