package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

/**
 * This entity class is used to hold basic metar weather information.
 */
@Entity(foreignKeys = @ForeignKey(entity = Airport.class,
    parentColumns = "id",
    childColumns = "airportId"),
    indices = {@Index(value = {"airportId"})})
public class Weather {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private long airportId;

  private Integer temperature;

  private Integer dewpoint;

  private String winds;

  private Integer pressure;

  private String visibility;

  private String clouds;

  private String weatherType;

  private String rawText;

  private Date timestamp = new Date();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public long getAirportId() {
    return airportId;
  }

  public void setAirportId(@NonNull long airportId) {
    this.airportId = airportId;
  }

  public Integer getTemperature() {
    return temperature;
  }

  public void setTemperature(Integer temperature) {
    this.temperature = temperature;
  }

  public Integer getDewpoint() {
    return dewpoint;
  }

  public void setDewpoint(Integer dewpoint) {
    this.dewpoint = dewpoint;
  }

  public String getWinds() {
    return winds;
  }

  public void setWinds(String winds) {
    this.winds = winds;
  }

  public Integer getPressure() {
    return pressure;
  }

  public void setPressure(Integer pressure) {
    this.pressure = pressure;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public String getClouds() {
    return clouds;
  }

  public void setClouds(String clouds) {
    this.clouds = clouds;
  }

  public String getWeatherType() {
    return weatherType;
  }

  public void setWeatherType(String weatherType) {
    this.weatherType = weatherType;
  }

  @NonNull
  public long getName() {
    return airportId;
  }

  public void setName(@NonNull long airportKey) {
    this.airportId = airportKey;
  }

  public String getRawText() {
    return rawText;
  }

  public void setRawText(String rawText) {
    this.rawText = rawText;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return getRawText();
  }
}
