package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TakeoffDistance {

  @PrimaryKey(autoGenerate = true)
  private int id;

  private int altitude;

  private int weight;

  private int temperature;

  private int takeoffSpeedV1;
}
