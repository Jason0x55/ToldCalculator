package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.toldcalculator.android.tc.model.entity.Airport;
import com.toldcalculator.android.tc.model.pojo.AirportAndRunways;

@Dao
public interface AirportDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Airport airport);

  @Query("SELECT * FROM airport WHERE id = :id")
  Airport select(long id);

  @Query("SELECT * FROM airport WHERE ICAO_ID = :icaoId")
  AirportAndRunways selectWithRunways(String icaoId);
}