package com.example.dnd_character_vault;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dnd_character_vault.DB.DnDAppDataBase;

@Entity(tableName = DnDAppDataBase.WEAPON_TABLE)
public class Weapon {
    @PrimaryKey(autoGenerate = true)
    private int mWeaponId;

    private int mCharacterID;
    private int mUserID;
    private String name;
    private int damage;
    private String description;
    private String abilities;
    private int amountHeld;

    public Weapon(int characterID, int userID, String name, int damage, String description, String abilities, int amountHeld) {
        mCharacterID = characterID;
        mUserID = userID;
        this.name = name;
        this.damage = damage;
        this.description = description;
        this.abilities = abilities;
        this.amountHeld = amountHeld;
    }

    @Override
    public String toString() {
        return name + ", ID: " + getWeaponId();
    }
    public int getWeaponId() {
        return mWeaponId;
    }

    public void setWeaponId(int weaponId) {
        mWeaponId = weaponId;
    }

    public int getCharacterID() {
        return mCharacterID;
    }

    public void setCharacterID(int characterID) {
        mCharacterID = characterID;
    }

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public int getAmountHeld() {
        return amountHeld;
    }

    public void setAmountHeld(int amountHeld) {
        this.amountHeld = amountHeld;
    }
}
