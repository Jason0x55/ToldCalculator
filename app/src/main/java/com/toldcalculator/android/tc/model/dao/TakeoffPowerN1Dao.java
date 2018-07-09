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

  @Query("SELECT * FROM takeOffData WHERE altitude = :altitude AND temperature = :temperature")
  TakeoffPowerN1 select(int altitude, int temperature);
}
