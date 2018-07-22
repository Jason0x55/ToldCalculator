package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * This entity class holds information about a runway for a specific airport such as the airport id,
 * runway identifier, length, and width.
 */
@Entity(foreignKeys = {@ForeignKey(entity = Airport.class,
    parentColumns = "id",
    childColumns = "airportId")},
    indices = {@Index(value = {"airportId"})})
public class Runway {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private Long airportId;

  @NonNull
  private String runwayId;

  @NonNull
  private Integer length;

  @NonNull
  private Integer width;

  /**
   * Returns the id of Runway table entry.
   * @return the id of Runway table entry.
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id of Runway table entry.
   * @param id the id of Runway table entry.
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the airport id associated with the runway.
   * @return the airport id associated with the runway.
   */
  @NonNull
  public Long getAirportId() {
    return airportId;
  }
  /**
   * Sets the airport id associated with the runway.
   * @param airportId the airport id associated with the runway.
   */
  public void setAirportId(@NonNull Long airportId) {
    this.airportId = airportId;
  }
  /**
   * Returns the runway identifier.
   * @return the runway identifier.
   */
  @NonNull
  public String getRunwayId() {
    return runwayId;
  }
  /**
   * Sets the runway identifier.
   * @param runwayId the runway identifier.
   */
  public void setRunwayId(@NonNull String runwayId) {
    this.runwayId = runwayId;
  }
  /**
   * Returns the length of the runway in feet.
   * @return the length of the runway in feet.
   */
  @NonNull
  public Integer getLength() {
    return length;
  }
  /**
   * Sets the length of the runway. Should be in feet.
   * @param length the length of the runway in feet.
   */
  public void setLength(@NonNull Integer length) {
    this.length = length;
  }
  /**
   * Returns the width of the runway in feet.
   * @return the width of the runway in feet.
   */
  @NonNull
  public Integer getWidth() {
    return width;
  }
  /**
   * Sets the width of the runway. Should be in feet.
   * @param width the width of the runway in feet.
   */
  public void setWidth(@NonNull Integer width) {
    this.width = width;
  }
}
