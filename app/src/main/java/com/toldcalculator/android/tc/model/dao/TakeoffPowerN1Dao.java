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

  @Query(Queries.HIGHALT_HIGHTEMP)
  TakeoffPowerN1 selectHighAltHighTemp(int altitude, int temperature);

  @Query(Queries.HIGHALT_LOWTEMP)
  TakeoffPowerN1 selectHighAltLowTemp(int altitude, int temperature);

  @Query(Queries.LOWALT_HIGHTEMP)
  TakeoffPowerN1 selectLowAltHighTemp(int altitude, int temperature);

  @Query(Queries.LOWALT_LOWTEMP)
  TakeoffPowerN1 selectLowAltLowTemp(int altitude, int temperature);
}