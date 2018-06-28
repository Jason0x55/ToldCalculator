package com.toldcalculator.android.tc.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = Airport.class,
    parentColumns = "id",
    childColumns = "airportId"))
public class Runway {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private long airportId;

    @NonNull
    private String runwayId;

    @NonNull
    private int length;

    @NonNull
    private int width;

  }
