package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//Add foreign key to aircraft
@Entity(indices = {@Index(value = {"temperature", "altitude"}, unique = true)},
    foreignKeys = @ForeignKey(entity = Aircraft.class,
        parentColumns = "id",
        childColumns = "aircraftId"))
public class TakeoffPowerN1 {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  private long aircraftId;

  @NonNull
  private int temperature;

  @NonNull
  private int altitude;

  @NonNull
  private float takeoffPowerN1;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @NonNull
  public long getAircraftId() {
    return aircraftId;
  }

  public void setAircraftId(@NonNull long aircraftId) {
    this.aircraftId = aircraftId;
  }

  @NonNull
  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(@NonNull int temperature) {
    this.temperature = temperature;
  }

  @NonNull
  public int getAltitude() {
    return altitude;
  }

  public void setAltitude(@NonNull int altitude) {
    this.altitude = altitude;
  }

  @NonNull
  public float getTakeoffPowerN1() {
    return takeoffPowerN1;
  }

  public void setTakeoffPowerN1(@NonNull float takeoffPowerN1) {
    this.takeoffPowerN1 = takeoffPowerN1;
  }
}
