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

  @Query("SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight >= :weight "
      + "AND temperature >= :temperature LIMIT 1")
  TakeoffData selectHighAll(int altitude, int weight, int temperature);

  @Query("SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight >= :weight "
      + "AND temperature <= :temperature ORDER BY temperature DESC LIMIT 1")
  TakeoffData selectHighAltHighWTLowTemp(int altitude, int weight, int temperature);

  @Query("SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight <= :weight "
      + "AND temperature >= :temperature ORDER BY weight DESC LIMIT 1")
  TakeoffData selectHighAltLowWTHighTemp(int altitude, int weight, int temperature);

  @Query("SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight <= :weight "
      + "AND temperature <= :temperature ORDER BY weight DESC, temperature DESC LIMIT 1")
  TakeoffData selectHighAltLowWtLowTemp(int altitude, int weight, int temperature);

  @Query("SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight >= :weight "
      + "AND temperature >= :temperature ORDER BY altitude DESC, weight, temperature LIMIT 1")
  TakeoffData selectLowAltHighWTHighTemp(int altitude, int weight, int temperature);

  @Query("SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight >= :weight "
      + "AND temperature <= :temperature ORDER BY temperature DESC, altitude DESC LIMIT 1")
  TakeoffData selectLowAltHighWTLowTemp(int altitude, int weight, int temperature);

  @Query("SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight <= :weight "
      + "AND temperature >= :temperature ORDER BY altitude DESC, temperature LIMIT 1")
  TakeoffData selectLowAltLowWTHighTemp(int altitude, int weight, int temperature);

  @Query("SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight <= :weight "
      + "AND temperature <= :temperature ORDER BY altitude DESC, temperature DESC LIMIT 1")
  TakeoffData selectLowAll(int altitude, int weight, int temperature);
}
