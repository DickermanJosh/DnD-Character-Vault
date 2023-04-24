package com.example.dnd_character_vault.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dnd_character_vault.User;

@Database(entities = {User.class},version  = 1)
public abstract class UserLoginDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "LoginUser.db";
    public static final String USER_TABLE = "user_table";

    private static volatile UserLoginDataBase instance;
    // LOCK is used as a tool it "lock" the database so that no other processes can access it while we are updating it
    private static final Object LOCK = new Object();

    public abstract UserLoginDAO mUserLoginDAO();


    public static UserLoginDataBase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), UserLoginDataBase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

}
