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

  @Query("SELECT * FROM takeOffData WHERE altitude = :altitude AND weight = :weight AND temperature = :temperature")
  TakeoffData select(int altitude, int weight, int temperature);
}
