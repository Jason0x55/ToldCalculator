package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.toldcalculator.android.tc.model.entity.User;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(User user);

  @Query("SELECT * FROM user ")
  User select();
}
