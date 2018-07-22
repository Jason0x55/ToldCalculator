package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * This entity class is used to hold takeoff data based on altitude, weight, and temperature.
 */
@Entity(indices = {@Index(value = {"altitude", "weight", "temperature",}, unique = true),
    @Index(value = {"aircraftId"})},
    foreignKeys = @ForeignKey(entity = Aircraft.class,
        parentColumns = "id",
        childColumns = "aircraftId"))
public class TakeoffData {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private Long aircraftId;

  @NonNull
  private Integer altitude;

  @NonNull
  private Integer weight;

  @NonNull
  private Integer temperature;

  private Integer takeoffDistance;

  private Integer takeoffSpeedV1;

  private Integer takeoffSpeedVR;

  private Integer takeoffSpeedV2;

  /**
   * Returns the id of the TakeoffData table entry.
   * @return the id of the TakeoffData table entry.
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id of the TakeoffData table entry.
   * @param id the id of the TakeoffData table entry.
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
   * Returns the weight in pounds.
   * @return the weight in pounds.
   */
  @NonNull
  public Integer getWeight() {
    return weight;
  }
  /**
   * Sets the weight in pounds.
   * @param weight the weight in pounds.
   */
  public void setWeight(@NonNull Integer weight) {
    this.weight = weight;
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
   * Returns the takeoff speed V1 in knots.
   * @return the takeoff speed V1 in knots.
   */
  public Integer getTakeoffSpeedV1() {
    return takeoffSpeedV1;
  }
  /**
   * Sets the takeoff speed V1 in knots.
   * @param takeoffSpeedV1 the takeoff speed V1 in knots.
   */
  public void setTakeoffSpeedV1(Integer takeoffSpeedV1) {
    this.takeoffSpeedV1 = takeoffSpeedV1;
  }
  /**
   * Returns the required takeoff distance in feet.
   * @return the required takeoff distance in feet.
   */
  public Integer getTakeoffDistance() {
    return takeoffDistance;
  }
  /**
   * Sets the required takeoff distance. Should be in feet.
   * @param takeoffDistance the required takeoff distance in feet.
   */
  public void setTakeoffDistance(Integer takeoffDistance) {
    this.takeoffDistance = takeoffDistance;
  }
  /**
   * Returns the takeoff speed VR in knots.
   * @return the takeoff speed VR in knots.
   */
  public Integer getTakeoffSpeedVR() {
    return takeoffSpeedVR;
  }
  /**
   * Sets the takeoff speed VR in knots.
   * @param takeoffSpeedVR the takeoff speed VR in knots.
   */
  public void setTakeoffSpeedVR(Integer takeoffSpeedVR) {
    this.takeoffSpeedVR = takeoffSpeedVR;
  }
  /**
   * Returns the takeoff speed V2 in knots.
   * @return the takeoff speed V2 in knots.
   */
  public Integer getTakeoffSpeedV2() {
    return takeoffSpeedV2;
  }
  /**
   * Sets the takeoff speed V2 in knots.
   * @param takeoffSpeedV2 the takeoff speed V2 in knots.
   */
  public void setTakeoffSpeedV2(Integer takeoffSpeedV2) {
    this.takeoffSpeedV2 = takeoffSpeedV2;
  }
}
