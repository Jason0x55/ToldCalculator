package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

/**
 * This entity class holds takeoff data, aircraft information, and weather information.
 */
@Entity(foreignKeys = @ForeignKey(entity = User.class,
    parentColumns = "id",
    childColumns = "userId"),
    indices = {
        @Index(value = {"userId"})
    })
public class SavedTakeoffData {

  @PrimaryKey(autoGenerate = true)
  private long id;

  private long userId;
  @NonNull
  private Date timestamp = new Date();
  @NonNull
  private String aircraftName;
  @NonNull
  private String airportId;
  private int runwayRequired;
  private float takeoffN1;
  private int takeoffV1;
  private int takeoffVR;
  private int takeoffV2;
  private int takeoffWeight;
  private String weatherRawText;

  /**
   * Returns the id for the savedTakeoffData table entry.
   * @return the id for the savedTakeoffData table entry.
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id for the savedTakeoffData table entry.
   * @param id the id for the savedTakeoffData table entry.
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the userId associated with the saved data.
   * @return the userId associated with the saved data.
   */
  public long getUserId() {
    return userId;
  }
  /**
   * Sets the userId associated with the saved data.
   * @param userId the userId associated with the saved data.
   */
  public void setUserId(long userId) {
    this.userId = userId;
  }
  /**
   * Returns the date and time that the data was saved.
   * @return the date and time that the data was saved.
   */
  @NonNull
  public Date getTimestamp() {
    return timestamp;
  }
  /**
   * Sets the date and time that the data was saved.
   * @param timestamp the date and time that the data was saved.
   */
  public void setTimestamp(@NonNull Date timestamp) {
    this.timestamp = timestamp;
  }
  /**
   * Returns the aircraft profile name.
   * @return the aircraft profile name.
   */
  @NonNull
  public String getAircraftName() {
    return aircraftName;
  }
  /**
   * Sets the aircraft profile name.
   * @param aircraftName the aircraft profile name.
   */
  public void setAircraftName(@NonNull String aircraftName) {
    this.aircraftName = aircraftName;
  }
  /**
   * Returns the airport id associated with the saved data.
   * @return the airport id associated with the saved data.
   */
  @NonNull
  public String getAirportId() {
    return airportId;
  }
  /**
   * Sets the airport id associated with the saved data.
   * @param airportId the airport id associated with the saved data.
   */
  public void setAirportId(@NonNull String airportId) {
    this.airportId = airportId;
  }
  /**
   * Returns the required runway in feet.
   * @return the required runway in feet.
   */
  public int getRunwayRequired() {
    return runwayRequired;
  }
  /**
   * Sets the required runway in feet.
   * @param runwayRequired the required runway in feet.
   */
  public void setRunwayRequired(int runwayRequired) {
    this.runwayRequired = runwayRequired;
  }
  /**
   * Returns the takeoff power N1 setting.
   * @return the takeoff power N1 setting.
   */
  public float getTakeoffN1() {
    return takeoffN1;
  }
  /**
   * Sets the takeoff power N1 setting.
   * @param takeoffN1 the takeoff power N1 setting.
   */
  public void setTakeoffN1(float takeoffN1) {
    this.takeoffN1 = takeoffN1;
  }
  /**
   * Returns the takeoff speed V1 in knots.
   * @return the takeoff speed V1 in knots.
   */
  public int getTakeoffV1() {
    return takeoffV1;
  }
  /**
   * Sets the takeoff speed V1 in knots.
   * @param takeoffV1 the takeoff speed V1 in knots.
   */
  public void setTakeoffV1(int takeoffV1) {
    this.takeoffV1 = takeoffV1;
  }
  /**
   * Returns the takeoff speed VR in knots.
   * @return the takeoff speed VR in knots.
   */
  public int getTakeoffVR() {
    return takeoffVR;
  }
  /**
   * Sets the takeoff speed VR in knots.
   * @param takeoffVR the takeoff speed VR in knots.
   */
  public void setTakeoffVR(int takeoffVR) {
    this.takeoffVR = takeoffVR;
  }
  /**
   * Returns the takeoff speed V2 in knots.
   * @return the takeoff speed V2 in knots.
   */
  public int getTakeoffV2() {
    return takeoffV2;
  }
  /**
   * Sets the takeoff speed V2 in knots.
   * @param takeoffV2 the takeoff speed V2 in knots.
   */
  public void setTakeoffV2(int takeoffV2) {
    this.takeoffV2 = takeoffV2;
  }
  /**
   * Returns the aircraft takeoff weight in pounds.
   * @return the aircraft takeoff weight in pounds.
   */
  public int getTakeoffWeight() {
    return takeoffWeight;
  }
  /**
   * Sets the aircraft takeoff weight in pounds.
   * @param takeoffWeight the aircraft takeoff weight in pounds.
   */
  public void setTakeoffWeight(int takeoffWeight) {
    this.takeoffWeight = takeoffWeight;
  }
  /**
   * Returns a string of the raw METAR text.
   * @return a string of the raw METAR text.
   */
  public String getWeatherRawText() {
    return weatherRawText;
  }
  /**
   * Sets a string of the raw METAR text.
   * @param weatherRawText a string of the raw METAR text.
   */
  public void setWeatherRawText(String weatherRawText) {
    this.weatherRawText = weatherRawText;
  }

  @Override
  public String toString() {
    String string = airportId + " " + aircraftName + " " + takeoffWeight;
    return string;
  }
}
