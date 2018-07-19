package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

@Entity
public class SavedTakeoffData {

  @PrimaryKey
  private Long id;

  private Date timestamp;

  private String aircraftName;

  private String airportId;
  private int RunwayRequired;
  private float TakeoffN1;
  private int TakeoffV1;
  private int TakeoffVR;
  private int TakeoffV2;
  private int TakeoffWeight;
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
    return RunwayRequired;
  }

  public void setRunwayRequired(int runwayRequired) {
    RunwayRequired = runwayRequired;
  }

  public float getTakeoffN1() {
    return TakeoffN1;
  }

  public void setTakeoffN1(float takeoffN1) {
    TakeoffN1 = takeoffN1;
  }

  public int getTakeoffV1() {
    return TakeoffV1;
  }

  public void setTakeoffV1(int takeoffV1) {
    TakeoffV1 = takeoffV1;
  }

  public int getTakeoffVR() {
    return TakeoffVR;
  }

  public void setTakeoffVR(int takeoffVR) {
    TakeoffVR = takeoffVR;
  }

  public int getTakeoffV2() {
    return TakeoffV2;
  }

  public void setTakeoffV2(int takeoffV2) {
    TakeoffV2 = takeoffV2;
  }

  public int getTakeoffWeight() {
    return TakeoffWeight;
  }

  public void setTakeoffWeight(int takeoffWeight) {
    TakeoffWeight = takeoffWeight;
  }

  public String getWeatherRawText() {
    return weatherRawText;
  }

  public void setWeatherRawText(String weatherRawText) {
    this.weatherRawText = weatherRawText;
  }
}
