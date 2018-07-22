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
  private Long aircraftId;

  @NonNull
  private Integer temperature;

  @NonNull
  private Integer altitude;

  private float takeoffPowerN1;

  /**
   * Returns the id of the TakeoffPowerN1 table entry.
   * @return the id of the TakeoffPowerN1 table entry.
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id of the TakeoffPowerN1 table entry.
   * @param id the id of the TakeoffPowerN1 table entry.
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the aircraftId associated with the takeoff data.
   * @return the aircraftId associated with the takeoff data.
   */
  @NonNull
  public Long getAircraftId() {
    return aircraftId;
  }
  /**
   * Sets the aircraftId associated with the takeoff data.
   * @param aircraftId the aircraftId associated with the takeoff data.
   */
  public void setAircraftId(@NonNull Long aircraftId) {
    this.aircraftId = aircraftId;
  }
  /**
   * Returns the temperature in celsius.
   * @return the temperature in celsius.
   */
  @NonNull
  public Integer getTemperature() {
    return temperature;
  }
  /**
   * Sets the temperature in celsius.
   * @param temperature the temperature in celsius.
   */
  public void setTemperature(@NonNull Integer temperature) {
    this.temperature = temperature;
  }
  /**
   * Returns the altitude in feet.
   * @return the altitude in feet.
   */
  @NonNull
  public Integer getAltitude() {
    return altitude;
  }
  /**
   * Sets the altitude in feet.
   * @param altitude the altitude in feet.
   */
  public void setAltitude(@NonNull Integer altitude) {
    this.altitude = altitude;
  }
  /**
   * Returns the takeoff power N1 setting.
   * @return the takeoff power N1 setting.
   */
  public float getTakeoffPowerN1() {
    return takeoffPowerN1;
  }
  /**
   * Sets the takeoff power N1 setting.
   * @param takeoffPowerN1 the takeoff power N1 setting.
   */
  public void setTakeoffPowerN1( float takeoffPowerN1) {
    this.takeoffPowerN1 = takeoffPowerN1;
  }
}
