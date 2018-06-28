package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;


@Dao
public interface TakeoffPowerN1Dao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(TakeoffPowerN1 takeoffPowerN1);
}
