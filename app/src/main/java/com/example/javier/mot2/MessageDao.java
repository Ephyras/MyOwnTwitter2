package com.example.javier.mot2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by Javier on 2/5/2018.
 */

@Dao
public interface MessageDao {


    @Query("SELECT * FROM message where owner=:username")
    Message[] getMessagesByUsername(String username);

    @Insert
    void insertAll(Message... messages);
}
