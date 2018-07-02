package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.toldcalculator.android.tc.model.entity.Runway;
import java.util.List;

@Dao
public interface RunwayDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Runway runway);

  @Query("SELECT * FROM runway")
  List<Runway> select();
}
