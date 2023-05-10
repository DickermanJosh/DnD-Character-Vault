package com.example.dnd_character_vault;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dnd_character_vault.DB.DnDAppDataBase;

import java.util.List;
import java.util.Objects;

@Entity(tableName = DnDAppDataBase.CHARACTER_TABLE)
public class Character {

    @PrimaryKey(autoGenerate = true)
    private int mCharacterID;

    private int mUserID;
    private String name;
    private String race;
    private String characterClass;
    private String subClass;
    private int level;
    private int hitPoints;
    private int maxHitPoints;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    private int armorClass;

    private int initiative;

    private int inspiration;

    private int proficiencyBonus;

    private String backStory;
    private String traits;
    private String ideals;
    private String bonds;
    private String flaws;
    private String features;
    private String spells;




    // Constructor
    public Character(int userID, String name, String race, String characterClass, String subClass, int level, int maxHitPoints, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int armorClass, int initiative, int inspiration, int proficiencyBonus, String backStory, String traits, String ideals, String bonds, String flaws, String features, String spells) {
        this.mUserID = userID;
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
        this.subClass = subClass;
        this.level = level;
        this.maxHitPoints = maxHitPoints;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.armorClass = armorClass;
        this.initiative = initiative;
        this.inspiration = inspiration;
        this.proficiencyBonus = proficiencyBonus;
        this.backStory = backStory;
        this.traits = traits;
        this.ideals = ideals;
        this.bonds = bonds;
        this.flaws = flaws;
        this.features = features;
        this.spells = spells;
        //this.equipment = equipment;
        hitPoints = maxHitPoints;
    }

    @Override
    public String toString() {
        return name + " is a "
                + race + " " +
                characterClass +
                ". ID: " + mCharacterID;
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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getInspiration() {
        return inspiration;
    }

    public void setInspiration(int inspiration) {
        this.inspiration = inspiration;
    }

    public int getProficiencyBonus() {
        return proficiencyBonus;
    }

    public void setProficiencyBonus(int proficiencyBonus) {
        this.proficiencyBonus = proficiencyBonus;
    }

    public String getBackStory() {
        return backStory;
    }

    public void setBackStory(String backStory) {
        this.backStory = backStory;
    }

    public String getTraits() {
        return traits;
    }

    public void setTraits(String traits) {
        this.traits = traits;
    }

    public String getIdeals() {
        return ideals;
    }

    public void setIdeals(String ideals) {
        this.ideals = ideals;
    }

    public String getBonds() {
        return bonds;
    }

    public void setBonds(String bonds) {
        this.bonds = bonds;
    }

    public String getFlaws() {
        return flaws;
    }

    public void setFlaws(String flaws) {
        this.flaws = flaws;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getSpells() {
        return spells;
    }

    public void setSpells(String spells) {
        this.spells = spells;
    }
}

