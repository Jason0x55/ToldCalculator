package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"temperature", "altitude"}, unique = true)})
public class TakeoffPowerN1 {

  @PrimaryKey(autoGenerate = true)
  private int id;

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
