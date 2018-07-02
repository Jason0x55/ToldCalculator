package com.toldcalculator.android.tc.model.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import com.toldcalculator.android.tc.model.entity.Aircraft;
import com.toldcalculator.android.tc.model.entity.TakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;
import java.util.List;

public class AircraftAndData {

  @Embedded
  private Aircraft aircraft;

  @Relation(parentColumn = "id", entityColumn = "aircraftId")
  List<TakeoffData> takeoffData;

  @Relation(parentColumn = "id", entityColumn = "aircraftId")
  List<TakeoffPowerN1> takeoffPowerN1;

  public Aircraft getAircraft() {
    return aircraft;
  }

  public void setAircraft(Aircraft aircraft) {
    this.aircraft = aircraft;
  }

  public List<TakeoffData> getTakeoffData() {
    return takeoffData;
  }

  public void setTakeoffData(
      List<TakeoffData> takeoffData) {
    this.takeoffData = takeoffData;
  }

  public List<TakeoffPowerN1> getTakeoffPowerN1() {
    return takeoffPowerN1;
  }

  public void setTakeoffPowerN1(
      List<TakeoffPowerN1> takeoffPowerN1) {
    this.takeoffPowerN1 = takeoffPowerN1;
  }
}
