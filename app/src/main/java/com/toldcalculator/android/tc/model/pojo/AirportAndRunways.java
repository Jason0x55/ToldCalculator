package com.toldcalculator.android.tc.model.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import com.toldcalculator.android.tc.model.entity.Airport;
import com.toldcalculator.android.tc.model.entity.Runway;
import java.util.List;

public class AirportAndRunways {

  @Embedded
  private Airport airport;

  @Relation(parentColumn = "id", entityColumn = "airportId")
  private List<Runway> runway;

  public Airport getAirport() {
    return airport;
  }

  public void setAirport(Airport airport) {
    this.airport = airport;
  }

  public List<Runway> getRunway() {
    return runway;
  }

  public void setRunway(List<Runway> runway) {
    this.runway = runway;
  }
}