package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;

@Dao
public interface TakeoffPowerN1Dao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(TakeoffPowerN1 takeoffPowerN1);

  @Query("SELECT * FROM takeoffPowerN1 WHERE altitude >= :altitude AND temperature >= :temperature LIMIT 1")
  TakeoffPowerN1 selectHighAltHighTemp(int altitude, int temperature);

  @Query("SELECT * FROM takeoffPowerN1 WHERE altitude >= :altitude AND temperature <= :temperature"
      + " ORDER BY temperature DESC, altitude LIMIT 1")
  TakeoffPowerN1 selectHighAltLowTemp(int altitude, int temperature);

  @Query("SELECT * FROM takeoffPowerN1 WHERE altitude <= :altitude AND temperature >= :temperature"
      + " ORDER BY temperature, altitude DESC LIMIT 1")
  TakeoffPowerN1 selectLowAltHighTemp(int altitude, int temperature);

  @Query("SELECT * FROM takeoffPowerN1 WHERE altitude <= :altitude AND temperature <= :temperature"
      + " ORDER BY temperature DESC, altitude DESC LIMIT 1")
  TakeoffPowerN1 selectLowAltLowTemp(int altitude, int temperature);
}