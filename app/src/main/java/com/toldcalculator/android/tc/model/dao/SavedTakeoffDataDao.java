package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.toldcalculator.android.tc.model.entity.SavedTakeoffData;
import java.util.List;

@Dao
public interface SavedTakeoffDataDao {

  @Insert
  long insert(SavedTakeoffData savedTakeoffData);

  @Query ("SELECT * FROM savedtakeoffdata where id = :id")
  SavedTakeoffData select(long id);

  @Query("SELECT * FROM savedtakeoffdata")
  List<SavedTakeoffData> selectAll();
}
