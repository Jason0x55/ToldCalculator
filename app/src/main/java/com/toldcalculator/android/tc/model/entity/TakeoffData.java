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
  private long aircraftId;

  @NonNull
  private int altitude;

  @NonNull
  private int weight;

  @NonNull
  private int temperature;

  private Integer takeoffSpeedV1;

  private Integer takeoffDistance;

  private Integer takeoffSpeedVR;

  private Integer takeoofSpeedV2;

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

  public Integer getTakeoffSpeedV1() {
    return takeoffSpeedV1;
  }

  public void setTakeoffSpeedV1(Integer takeoffSpeedV1) {
    this.takeoffSpeedV1 = takeoffSpeedV1;
  }

  public Integer getTakeoffDistance() {
    return takeoffDistance;
  }

  public void setTakeoffDistance(Integer takeoffDistance) {
    this.takeoffDistance = takeoffDistance;
  }

  public Integer getTakeoffSpeedVR() {
    return takeoffSpeedVR;
  }

  public void setTakeoffSpeedVR(Integer takeoffSpeedVR) {
    this.takeoffSpeedVR = takeoffSpeedVR;
  }

  public Integer getTakeoofSpeedV2() {
    return takeoofSpeedV2;
  }

  public void setTakeoofSpeedV2(Integer takeoofSpeedV2) {
    this.takeoofSpeedV2 = takeoofSpeedV2;
  }
}
