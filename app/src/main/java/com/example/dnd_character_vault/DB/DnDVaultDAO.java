package com.example.dnd_character_vault.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dnd_character_vault.Character;
import com.example.dnd_character_vault.Item;
import com.example.dnd_character_vault.User;

import java.util.List;
@Dao
public interface DnDVaultDAO {

    // USER QUERIES
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
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

    // CHARACTER QUERIES
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    @Insert
    void insert(Character... characters);

    @Update
    void update(Character... characters);

    @Delete
    void delete(Character characters);

    // Get a list of characters based on the user
    @Query("SELECT * FROM " + DnDAppDataBase.CHARACTER_TABLE + " WHERE mUserID = :userID ORDER BY mCharacterID DESC")
    List<Character> getCharacterListByUserID(int userID);

    // Get a character based on their ID
    @Query("SELECT * FROM " + DnDAppDataBase.CHARACTER_TABLE + " WHERE mCharacterID = :characterID")
    Character getCharacterByCharacterID(int characterID);


    // ITEM QUERIES
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    @Insert
    void insert(Item... items);

    @Update
    void update(Item... items);

    @Delete
    void delete(Item items);

    // Get a list of all the items associated with a user, listed in order of characterID
    @Query("SELECT * FROM " + DnDAppDataBase.ITEM_TABLE + " WHERE mUserID = :userID ORDER BY mCharacterID DESC")
    List<Item> getItemListByUserID(int userID);

    // Get a list of all the items associated with a specific character (should only be user after getting the user)
    @Query("SELECT * FROM " + DnDAppDataBase.ITEM_TABLE + " WHERE mCharacterID = :characterID ORDER BY mItemId DESC")
    List<Item> getItemByCharacterID(int characterID);

    // Get a specific item based on its ID
    @Query("SELECT * FROM " + DnDAppDataBase.ITEM_TABLE + " WHERE mItemId = :itemID")
    Item getItemByItemID(int itemID);
}
