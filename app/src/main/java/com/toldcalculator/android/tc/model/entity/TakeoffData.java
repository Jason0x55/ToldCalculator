package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"altitude", "weight", "temperature",}, unique = true)},
    foreignKeys = @ForeignKey(entity = Aircraft.class,
        parentColumns = "id",
        childColumns = "aircraftId"))
public class TakeoffData {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  private int aircraftId;

  @NonNull
  private int altitude;

  @NonNull
  private int weight;

  @NonNull
  private int temperature;

  @NonNull
  private int takeoffSpeedV1;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @NonNull
  public int getAircraftId() {
    return aircraftId;
  }

  public void setAircraftId(@NonNull int aircraftId) {
    this.aircraftId = aircraftId;
  }

  @NonNull
  public int getAltitude() {
    return altitude;
  }

  public void setAltitude(@NonNull int altitude) {
    this.altitude = altitude;
  }

  @NonNull
  public int getWeight() {
    return weight;
  }

  public void setWeight(@NonNull int weight) {
    this.weight = weight;
  }

  @NonNull
  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(@NonNull int temperature) {
    this.temperature = temperature;
  }

  @NonNull
  public int getTakeoffSpeedV1() {
    return takeoffSpeedV1;
  }

  public void setTakeoffSpeedV1(@NonNull int takeoffSpeedV1) {
    this.takeoffSpeedV1 = takeoffSpeedV1;
  }
}
