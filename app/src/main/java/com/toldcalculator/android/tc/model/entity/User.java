package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = {@ForeignKey(entity = Airport.class,
    parentColumns = "id",
    childColumns = "airportId"),
    @ForeignKey(entity = Aircraft.class,
        parentColumns = "id",
        childColumns = "aircraftId")})
public class User {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  private Long aircraftId;

  private Long airportId;

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

  public Long getAircraftId() {
    return aircraftId;
  }

  public void setAircraftId(Long aircraftId) {
    this.aircraftId = aircraftId;
  }

  public Long getAirportId() {
    return airportId;
  }

  public void setAirportId(Long airportId) {
    this.airportId = airportId;
  }

}
