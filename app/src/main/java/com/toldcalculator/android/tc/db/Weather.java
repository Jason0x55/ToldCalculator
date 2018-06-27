package com.toldcalculator.android.tc.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Weather {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private String name;

  private int temperature;

  private int dewpoint;

  private String winds;

  private int pressure;

  private String visibility;

  private String clouds;

  private String weatherType;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(int temperature) {
    this.temperature = temperature;
  }

  public int getDewpoint() {
    return dewpoint;
  }

  public void setDewpoint(int dewpoint) {
    this.dewpoint = dewpoint;
  }

  public String getWinds() {
    return winds;
  }

  public void setWinds(String winds) {
    this.winds = winds;
  }

  public int getPressure() {
    return pressure;
  }

  public void setPressure(int pressure) {
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
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    // TODO return METAR string.
    return super.toString();
  }
}
