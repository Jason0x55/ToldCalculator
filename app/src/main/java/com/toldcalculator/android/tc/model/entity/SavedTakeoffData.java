package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
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
  private Date timestamp = new Date();
  private String aircraftName;
  private String airportId;
  private int runwayRequired;
  private float takeoffN1;
  private int takeoffV1;
  private int takeoffVR;
  private int takeoffV2;
  private int takeoffWeight;
  private String weatherRawText;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getAircraftName() {
    return aircraftName;
  }

  public void setAircraftName(String aircraftName) {
    this.aircraftName = aircraftName;
  }

  public String getAirportId() {
    return airportId;
  }

  public void setAirportId(String airportId) {
    this.airportId = airportId;
  }

  public int getRunwayRequired() {
    return runwayRequired;
  }

  public void setRunwayRequired(int runwayRequired) {
    this.runwayRequired = runwayRequired;
  }

  public float getTakeoffN1() {
    return takeoffN1;
  }

  public void setTakeoffN1(float takeoffN1) {
    this.takeoffN1 = takeoffN1;
  }

  public int getTakeoffV1() {
    return takeoffV1;
  }

  public void setTakeoffV1(int takeoffV1) {
    this.takeoffV1 = takeoffV1;
  }

  public int getTakeoffVR() {
    return takeoffVR;
  }

  public void setTakeoffVR(int takeoffVR) {
    this.takeoffVR = takeoffVR;
  }

  public int getTakeoffV2() {
    return takeoffV2;
  }

  public void setTakeoffV2(int takeoffV2) {
    this.takeoffV2 = takeoffV2;
  }

  public int getTakeoffWeight() {
    return takeoffWeight;
  }

  public void setTakeoffWeight(int takeoffWeight) {
    this.takeoffWeight = takeoffWeight;
  }

  public String getWeatherRawText() {
    return weatherRawText;
  }

  public void setWeatherRawText(String weatherRawText) {
    this.weatherRawText = weatherRawText;
  }

  @Override
  public String toString() {
    String string = airportId + " " + aircraftName + " " + takeoffWeight;
    return string;
  }
}
