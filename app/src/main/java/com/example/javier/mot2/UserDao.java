package com.example.javier.mot2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by Javier on 2/5/2018.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user where username=:username")
    User getUserByUsername(String username);

    @Insert
    void insertAll(User... users);
}
