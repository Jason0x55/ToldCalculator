package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

@Entity
public class SavedTakeoffData {

  @PrimaryKey
  private Long id;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
