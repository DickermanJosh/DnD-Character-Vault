package com.example.dnd_character_vault;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dnd_character_vault.DB.DnDAppDataBase;

@Entity(tableName = DnDAppDataBase.SPELL_TABLE)
public class Spell {

    @PrimaryKey(autoGenerate = true)
    private int mSpellId;

    private int mCharacterID;
    private int mUserID;
    private String name;
    private int damage;
    private String description;
    private int charges;

    public Spell(int characterID, int userID, String name, int damage, String description, int charges) {
        mCharacterID = characterID;
        mUserID = userID;
        this.name = name;
        this.damage = damage;
        this.description = description;
        this.charges = charges;
    }

    @Override
    public String toString() {
        return name + ", ID: " + getSpellId();
    }

    public int getSpellId() {
        return mSpellId;
    }

    public void setSpellId(int spellId) {
        mSpellId = spellId;
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

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }
}
