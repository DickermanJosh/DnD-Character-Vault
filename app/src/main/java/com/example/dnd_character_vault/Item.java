package com.example.dnd_character_vault;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dnd_character_vault.DB.DnDAppDataBase;

@Entity(tableName = DnDAppDataBase.ITEM_TABLE)
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    private String name;
    private String description;
    private String abilities;
    private int amountHeld;

    public Item(String name, String description, String abilities, int amountHeld) {
        this.name = name;
        this.description = description;
        this.abilities = abilities;
        this.amountHeld = amountHeld;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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