package com.toldcalculator.android.tc.model.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import com.toldcalculator.android.tc.model.entity.Aircraft;
import com.toldcalculator.android.tc.model.entity.Airport;
import com.toldcalculator.android.tc.model.entity.User;
import java.util.List;

public class UserInfo {

  @Embedded
  private User user;

  @Relation(parentColumn = "aircraftId", entityColumn = "id")
  private List<Aircraft> aircraft;

  @Relation(parentColumn = "airportId", entityColumn = "id")
  private List<Airport> airport;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Aircraft> getAircraft() {
    return aircraft;
  }

  public void setAircraft(List<Aircraft> aircraft) {
    this.aircraft = aircraft;
  }

  public List<Airport> getAirport() {
    return airport;
  }

  public void setAirport(List<Airport> airport) {
    this.airport = airport;
  }
}