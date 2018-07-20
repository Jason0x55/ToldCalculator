package com.toldcalculator.android.tc.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import com.toldcalculator.android.tc.model.entity.User;
import com.toldcalculator.android.tc.model.pojo.UserInfo;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(User user);

  @Query("SELECT * FROM user LIMIT 1")
  User select();

  @Transaction
  @Query("SELECT * FROM user LIMIT 1")
  UserInfo userInfo();
}
