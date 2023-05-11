package com.example.dnd_character_vault.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dnd_character_vault.Character;
import com.example.dnd_character_vault.Item;
import com.example.dnd_character_vault.Spell;
import com.example.dnd_character_vault.User;
import com.example.dnd_character_vault.Weapon;

@Database(entities = {User.class, Character.class,
        Item.class, Weapon.class, Spell.class},version  = 4)
public abstract class DnDAppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "DnDCharacterVault.db";
    public static final String USER_TABLE = "user_table";
    public static final String CHARACTER_TABLE = "character_table";
    public static final String ITEM_TABLE = "item_table";
    public static final String SPELL_TABLE = "spell_table";
    public static final String WEAPON_TABLE = "weapon_table";
    private static volatile DnDAppDataBase instance;
    // LOCK is used as a tool it "lock" the database so that no other processes can access it while we are updating it
    private static final Object LOCK = new Object();

    public abstract DnDVaultDAO mDnDVaultDAO();

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
