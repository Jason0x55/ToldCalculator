package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * This entity class holds the basic information about the aircraft such as the name, the
 * aircraft type, and the aircraft's basic empty weight.
 */
@Entity
public class Aircraft {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  @NonNull
  private String aircraftType;

  private int basicEmptyWeight;

  /**
   * Return the id of an aircraft entry.
   * @return the id of an aircraft entry.
   */
  public long getId() {
    return id;
  }
  /**
   * Set the id of an aircraft entry.
   * @param id the id of an aircraft entry.
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the aircraft profile name.
   * @return the aircraft profile name.
   */
  @NonNull
  public String getName() {
    return name;
  }
  /**
   * Set the aircraft profile name.
   * @param name the aircraft profile name.
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }
  /**
   * Returns the aircraft type set by the user.
   * @return the type of aircraft.
   */
  @NonNull
  public String getAircraftType() {
    return aircraftType;
  }
  /**
   * Set the type of aircraft.
   * @param aircraftType the type of aircraft.
   */
  public void setAircraftType(@NonNull String aircraftType) {
    this.aircraftType = aircraftType;
  }
  /**
   * Return the aircraft basic empty weight set by the user.
   * @return aircraft basic empty weight.
   */
  public int getBasicEmptyWeight() {
    return basicEmptyWeight;
  }
  /**
   * Sets the basic empty weight of the aircraft.
   * @param basicEmptyWeight basic empty weight of the aircraft.
   */
  public void setBasicEmptyWeight(int basicEmptyWeight) {
    this.basicEmptyWeight = basicEmptyWeight;
  }

  @Override
  public String toString() {
    return name + " : " + aircraftType;
  }
}