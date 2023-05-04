package com.example.dnd_character_vault;

import androidx.room.PrimaryKey;

import java.util.Objects;

public class Character {

    @PrimaryKey(autoGenerate = true)
    private int characterID;
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

    // Constructor
    public Character(String name, String race, String characterClass, String subClass, int level, int maxHitPoints, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
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

        hitPoints = maxHitPoints;
    }

    @Override
    public String toString() {
        return name + " is a "
                + race + " " +
                characterClass +
                ". ID: " + characterID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return characterID == character.characterID && Objects.equals(name, character.name) && Objects.equals(race, character.race) && Objects.equals(characterClass, character.characterClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterID, name, race, characterClass);
    }

    // Getters and setters
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
}

