package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.toldcalculator.android.tc.model.entity.TakeoffData;

@Dao
public interface TakeoffDataDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(TakeoffData takeoffData);

  @Query(Queries.HIGHALT_HIGHWEIGHT_HIGHTEMP)
  TakeoffData selectHighAll(int altitude, int weight, int temperature);

  @Query(Queries.HIGHALT_HIGHWEIGHT_LOWTEMP)
  TakeoffData selectHighAltHighWTLowTemp(int altitude, int weight, int temperature);

  @Query(Queries.HIGHALT_LOWWEIGHT_HIGHTEMP)
  TakeoffData selectHighAltLowWTHighTemp(int altitude, int weight, int temperature);

  @Query(Queries.HIGHALT_LOWWEIGHT_LOWTEMP)
  TakeoffData selectHighAltLowWtLowTemp(int altitude, int weight, int temperature);

  @Query(Queries.LOWALT_HIGHWEIGHT_HIGHTEMP)
  TakeoffData selectLowAltHighWTHighTemp(int altitude, int weight, int temperature);

  @Query(Queries.LOWALT_HIGHWEIGHT_LOWTEMP)
  TakeoffData selectLowAltHighWTLowTemp(int altitude, int weight, int temperature);

  @Query(Queries.LOWALT_LOWWEIGHT_HIGHTEMP)
  TakeoffData selectLowAltLowWTHighTemp(int altitude, int weight, int temperature);

  @Query(Queries.LOWALT_LOWWEIGHT_LOWTEMP)
  TakeoffData selectLowAll(int altitude, int weight, int temperature);
}
