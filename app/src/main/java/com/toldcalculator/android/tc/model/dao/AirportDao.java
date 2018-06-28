package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import com.toldcalculator.android.tc.model.entity.Airport;

@Dao
public interface AirportDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Airport airport);
}