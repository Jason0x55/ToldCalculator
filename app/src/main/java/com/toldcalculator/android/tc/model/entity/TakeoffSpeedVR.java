package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class TakeoffSpeedVR {

  @PrimaryKey(autoGenerate = true)
  private int id;

  @NonNull
  private int weight;

  @NonNull
  private int speed;

}
