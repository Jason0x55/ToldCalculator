package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import com.toldcalculator.android.tc.model.entity.TakeoffSpeedV1;

@Dao
public interface TakeoffSpeedV1Dao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(TakeoffSpeedV1 takeoffSpeedV1);
}

