package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * This entity class hold a single N1 power setting based off temperature and altitude.
 */
@Entity(indices = {@Index(value = {"temperature", "altitude"}, unique = true),
    @Index(value = {"aircraftId"})},
    foreignKeys = @ForeignKey(entity = Aircraft.class,
        parentColumns = "id",
        childColumns = "aircraftId"))
public class TakeoffPowerN1 {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private long aircraftId;

  @NonNull
  private int temperature;

  @NonNull
  private int altitude;

  private float takeoffPowerN1;

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public float getTakeoffPowerN1() {
    return takeoffPowerN1;
  }

  public void setTakeoffPowerN1( float takeoffPowerN1) {
    this.takeoffPowerN1 = takeoffPowerN1;
  }
}
