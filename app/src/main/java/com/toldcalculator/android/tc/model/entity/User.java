package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * This entity class is used to store user information such as the user name,
 * default aircraft and airport.
 */
@Entity(foreignKeys = {@ForeignKey(entity = Airport.class,
    parentColumns = "id",
    childColumns = "airportId"),
    @ForeignKey(entity = Aircraft.class,
        parentColumns = "id",
        childColumns = "aircraftId")},
    indices = {@Index(value = {"airportId"}), @Index({"aircraftId"})})
public class User {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  private Long aircraftId;

  private Long airportId;

  /**
   * Returns the id of the user table entry.
   * @return the id of the user table entry.
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id of the user table entry.
   * @param id the id of the user table entry.
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the profile user name.
   * @return the profile user name.
   */
  @NonNull
  public String getName() {
    return name;
  }
  /**
   * Sets the profile user name.
   * @param name the profile user name.
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }
  /**
   * Returns the aircraftId associated with the user profile.
   * @return the aircraftId associated with the user profile.
   */
  public Long getAircraftId() {
    return aircraftId;
  }
  /**
   * Sets the aircraftId associated with the user profile.
   * @param aircraftId the aircraftId associated with the user profile.
   */
  public void setAircraftId(Long aircraftId) {
    this.aircraftId = aircraftId;
  }
  /**
   * Returns the airportId associated with the user profile.
   * @return the airportId associated with the user profile.
   */
  public Long getAirportId() {
    return airportId;
  }
  /**
   * Sets the airportId associated with the user profile.
   * @param airportId the airportId associated with the user profile.
   */
  public void setAirportId(Long airportId) {
    this.airportId = airportId;
  }
}