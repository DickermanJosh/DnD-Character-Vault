package com.example.dnd_character_vault.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dnd_character_vault.Character;
import com.example.dnd_character_vault.User;

import java.util.List;
@Dao
public interface DnDVaultDAO {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User users);

    @Query("SELECT * FROM " + DnDAppDataBase.USER_TABLE + " WHERE mUserName = :username")
    User getUserByUserName(String username);

    @Query("SELECT * FROM " + DnDAppDataBase.USER_TABLE + " WHERE mLogId = :logId")
    User getUserById(int logId);

/*    @Query("SELECT * FROM " + DnDAppDataBase.USER_TABLE + " ORDER BY mCharacters desc")
    List<Character> getCharacters();*/

    @Insert
    void insert(Character... characters);

    @Update
    void update(Character... characters);

    @Delete
    void delete(Character characters);

  /*  @Query("SELECT * FROM " + DnDAppDataBase.CHARACTER_TABLE + " WHERE char = :username")
    User getCharacterByID(int ID);

    @Query("SELECT * FROM " + DnDAppDataBase.USER_TABLE + " WHERE mLogId = :logId")
    User getUserById(int logId);

    @Query("SELECT * FROM " + DnDAppDataBase.USER_TABLE + " ORDER BY mCharacters desc")
    List<Character> getCharacters();*/
}
