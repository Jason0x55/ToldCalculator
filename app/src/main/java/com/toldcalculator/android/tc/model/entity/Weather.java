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
  private Long airportId;

  private Integer temperature;

  private Integer dewpoint;

  private String winds;

  private Integer pressure;

  private String visibility;

  private String clouds;

  private String weatherType;

  private String rawText;

  private Date timestamp = new Date();

  /**
   * Returns the id of the weather table entry.
   * @return the id of the weather table entry.
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id of the weather table entry.
   * @param id the id of the weather table entry.
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the airportId associated with the weather.
   * @return the airportId associated with the weather.
   */
  @NonNull
  public Long getAirportId() {
    return airportId;
  }
  /**
   * Sets the airportId associated with the weather.
   * @param airportId the airportId associated with the weather.
   */
  public void setAirportId(@NonNull Long airportId) {
    this.airportId = airportId;
  }
  /**
   * Returns the temperature in celsius.
   * @return the temperature in celsius.
   */
  public Integer getTemperature() {
    return temperature;
  }
  /**
   * Sets the temperature. Should be in celsius.
   * @param temperature the temperature in celsius.
   */
  public void setTemperature(Integer temperature) {
    this.temperature = temperature;
  }
  /**
   * Returns the dewpoint in celsius.
   * @return the dewpoint in celsius.
   */
  public Integer getDewpoint() {
    return dewpoint;
  }
  /**
   * Sets the dewpoint. Should be in celsius.
   * @param dewpoint the dewpoint in celsius.
   */
  public void setDewpoint(Integer dewpoint) {
    this.dewpoint = dewpoint;
  }
  /**
   * Returns the observed wind direction in degrees and speed in knots.
   * @return the observed wind direction in degrees  and speed in knots..
   */
  public String getWinds() {
    return winds;
  }
  /**
   * Set the observed wind direction in degrees and speed in knots..
   * @param winds the observed wind direction in degrees and speed in knots.
   */
  public void setWinds(String winds) {
    this.winds = winds;
  }
  /**
   * Returns the observed altimeter setting in inches of mercury.
   * @return the observed altimeter setting in inches of mercury.
   */
  public Integer getPressure() {
    return pressure;
  }
  /**
   * Sets the observed altimeter setting. Should be in inches of mercury.
   * @param pressure the observed altimeter setting in inches of mercury.
   */
  public void setPressure(Integer pressure) {
    this.pressure = pressure;
  }
  /**
   * Returns the observed visibility in statute miles.
   * @return the observed visibility in statute miles.
   */
  public String getVisibility() {
    return visibility;
  }
  /**
   * Set the observed visibility in statute miles.
   * @param visibility the observed visibility in statute miles.
   */
  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }
  /**
   * Returns sky coverage information.
   * @return sky coverage information.
   */
  public String getClouds() {
    return clouds;
  }
  /**
   * Sets sky coverage information.
   * @param clouds sky coverage information.
   */
  public void setClouds(String clouds) {
    this.clouds = clouds;
  }
  /**
   * Returns the current type of weather observed.
   * @return the current type of weather observed.
   */
  public String getWeatherType() {
    return weatherType;
  }
  /**
   * Sets the current type of weather observed.
   * @param weatherType the current type of weather observed.
   */
  public void setWeatherType(String weatherType) {
    this.weatherType = weatherType;
  }
  /**
   * Returns a string containing all the METAR information.
   * @return a string containing all the METAR information.
   */
  public String getRawText() {
    return rawText;
  }
  /**
   * Sets METAR raw text as a string containing all METAR information.
   * @param rawText a string containing all the METAR information.
   */
  public void setRawText(String rawText) {
    this.rawText = rawText;
  }
  /**
   * Returns the date and time that the data was saved.
   * @return the date and time that the data was saved.
   */
  public Date getTimestamp() {
    return timestamp;
  }
  /**
   * Sets the date and time that the data was saved.
   * @param timestamp the date and time that the data was saved.
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return getRawText();
  }
}
