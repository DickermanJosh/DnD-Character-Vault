package com.example.dnd_character_vault.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dnd_character_vault.User;

import java.util.List;
@Dao
public interface UserLoginDAO {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User users);

    @Query("SELECT * FROM " + UserLoginDataBase.USER_TABLE + " WHERE mUserName = :username")
    User getUserByUserName(String username);

    @Query("SELECT * FROM " + UserLoginDataBase.USER_TABLE + " WHERE mLogId = :logId")
    User getUserById(int logId);
}
