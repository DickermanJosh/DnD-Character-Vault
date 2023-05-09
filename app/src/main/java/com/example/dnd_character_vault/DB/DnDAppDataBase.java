package com.example.dnd_character_vault.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dnd_character_vault.Character;
import com.example.dnd_character_vault.Item;
import com.example.dnd_character_vault.User;

@Database(entities = {User.class, Character.class, Item.class},version  = 3)
public abstract class DnDAppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "DnDCharacterVault.db";
    public static final String USER_TABLE = "user_table";
    public static final String CHARACTER_TABLE = "character_table";
    public static final String ITEM_TABLE = "item_table";
    private static volatile DnDAppDataBase instance;
    // LOCK is used as a tool it "lock" the database so that no other processes can access it while we are updating it
    private static final Object LOCK = new Object();

    public abstract DnDVaultDAO mUserLoginDAO();

    public static DnDAppDataBase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), DnDAppDataBase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

}
