package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * This entity class is used to hold basic airport information such as the airport name,
 * ICAO identifier, and elevation.
 */
@Entity
public class Airport {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  private String name;

  @ColumnInfo(name = "ICAO_ID", collate = ColumnInfo.NOCASE)
  private String icaoId;

  private String ident;

  @NonNull
  private Integer elevation;

  /**
   * Returns the id to the airport table entry.
   * @return the id to the airport table entry.
   */
  public long getId() {
    return id;
  }
  /**
   *  Sets id of the airport table entry.
   * @param id id of the airport table entry.
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the name of the airport.
   * @return the name of the airport.
   */
  @NonNull
  public String getName() {
    return name;
  }
  /**
   * Sets the name of the airport.
   * @param name name of the airport.
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }
  /**
   * Returns the ICAO identifier of the airport.
   * @return the ICAO identifier of the airport.
   */
  @NonNull
  public String getIcaoId() {
    return icaoId;
  }
  /**
   * Sets the ICAO identifier of the airport.
   * @param icaoId the ICAO identifier of the airport.
   */
  public void setIcaoId(@NonNull String icaoId) {
    this.icaoId = icaoId;
  }
  /**
   * Returns the identifier of the airport.
   * @return the identifier of the airport.
   */
  public String getIdent() {
    return ident;
  }
  /**
   * Set the identifier of the airport.
   * @param ident the identifier of the airport.
   */
  public void setIdent(String ident) {
    this.ident = ident;
  }
  /**
   * Returns the elevation of the airport in feet.
   * @return the elevation of the airport in feet.
   */
  @NonNull
  public Integer getElevation() {
    return elevation;
  }
  /**
   * Sets the elevation of the airport. Should be in feet.
   * @param elevation the elevation of the airport in feet.
   */
  public void setElevation(@NonNull Integer elevation) {
    this.elevation = elevation;
  }

  @Override
  public String toString() {
    return icaoId + " " + name;
  }
}