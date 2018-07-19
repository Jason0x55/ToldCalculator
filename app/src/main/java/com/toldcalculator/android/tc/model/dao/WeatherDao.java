package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.toldcalculator.android.tc.model.entity.Weather;
import java.util.List;

@Dao
public interface WeatherDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insert(Weather weather);

  @Query("SELECT * FROM weather")
  List<Weather> selectAll();
}
