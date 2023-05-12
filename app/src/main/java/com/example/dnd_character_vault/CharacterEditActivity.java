package com.example.dnd_character_vault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityCharacterCreateBinding;
import com.example.dnd_character_vault.databinding.ActivityCharacterEditBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterEditActivity extends AppCompatActivity {

    private static final String CHARACTER_EDIT_ID = "com.example.dnd_character_vault.CharacterEditID";

    ActivityCharacterEditBinding binding;

    private ListView mCharacterAttributes;
    private ListView mItems;
    private ListView mSpells;
    private ListView mWeapons;
    private Button mBackButton;
    private Button mSaveButton;
    private Button mAddToListButton;
    private EditText mEditCharacterStat;

    private DnDVaultDAO mDnDVaultDAO;

    private Character currentCharacter;
    public static int characterID;
    public static int selectedItemID;
    public static int selectedSpellID;
    public static int selectedWeaponID;
    private List<String> characterAttributesList = new ArrayList<>();
    private List<String> characterItemsList = new ArrayList<>();
    private List<String> characterWeaponsList = new ArrayList<>();
    private List<String> characterSpellList = new ArrayList<>();
    Map<Integer,Integer> itemIDtoPositionMap = new HashMap<>();
    Map<Integer,Integer> spellIDtoPositionMap = new HashMap<>();
    Map<Integer,Integer> weaponIDtoPositionMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_edit);
        binding = ActivityCharacterEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mCharacterAttributes = binding.characterListview;
        mItems = binding.itemListview;
        mSpells = binding.spellListview;
        mWeapons = binding.weaponListview;
        mBackButton = binding.backButton;
        mSaveButton = binding.saveButton;
        mAddToListButton = binding.addToListButton;
        mEditCharacterStat = binding.editCharacterStat;

        mItems.setVisibility(View.INVISIBLE);
        mSpells.setVisibility(View.INVISIBLE);
        mWeapons.setVisibility(View.INVISIBLE);

        setupDatabase();

        getCharacter();

        setupList();

        setupItemsList();

        setupSpellsList();

        setupWeaponsList();

        mCharacterAttributes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Changing the state of the activity to allow the user to edit their information
                mCharacterAttributes.setVisibility(View.INVISIBLE);
                mEditCharacterStat.setVisibility(View.VISIBLE);
                mSaveButton.setVisibility(View.VISIBLE);
                mEditCharacterStat.setHint(characterAttributesList.get(i));
                mEditCharacterStat.getText().clear();

                // I'm aware that all these if statements are pretty gross
                // but I couldn't think of another way to access all the setters
                // that wasn't needlessly complicated
                if(i == 0){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setName(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Name change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 1){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setRace(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Race change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 2){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setCharacterClass(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Class change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 3){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setSubClass(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("SubClass change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 4){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setBackStory(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Backstory change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 5){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setTraits(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Traits change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 6){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setIdeals(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Ideals change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 7){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setBonds(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Bonds change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 8){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setFlaws(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Flaws change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 9){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {
                                currentCharacter.setFeatures(mEditCharacterStat.getText().toString());
                                mDnDVaultDAO.update(currentCharacter);
                                showToast("Features change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 10){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setLevel(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Level change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 11){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setHitPoints(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("HP change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                }  else if(i == 12){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setMaxHitPoints(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("MaxHP change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                }  else if(i == 13){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setStrength(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Strength change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 14){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setDexterity(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Dexterity change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                }  else if(i == 15){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setConstitution(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Constitution change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 16){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setIntelligence(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Intelligence change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                }  else if(i == 17){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setWisdom(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Wisdom change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 18){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setCharisma(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Charisma change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 19){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setArmorClass(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Armor Class change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 20){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setInitiative(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Initiative change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                }  else if(i == 21){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setInspiration(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Inspiration change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 22){
                    mSaveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(!mEditCharacterStat.getText().toString().isEmpty()) {

                                try{
                                    int number = Integer.parseInt(mEditCharacterStat.getText().toString());
                                    currentCharacter.setProficiencyBonus(number);
                                    mDnDVaultDAO.update(currentCharacter);
                                } catch(NumberFormatException e){
                                    showToast("Please enter a valid number");
                                }

                                showToast("Proficiency Bonus change saved successfully");
                            } else{
                                showToast("You haven't made any changes!");
                            }
                        }
                    });

                } else if(i == 23){ // Items
                    //mCharacterAttributes.setVisibility(View.INVISIBLE);
                    mEditCharacterStat.setVisibility(View.INVISIBLE);
                    mSaveButton.setVisibility(View.INVISIBLE);
                    mEditCharacterStat.getText().clear();
                    mSpells.setVisibility(View.INVISIBLE);
                    mWeapons.setVisibility(View.INVISIBLE);
                    mItems.setVisibility(View.VISIBLE);
                    mAddToListButton.setVisibility(View.VISIBLE);
                    mAddToListButton.setText("Add a New Item");
                    setupItemsList();
                    if(characterItemsList.isEmpty()){
                        showToast("You don't currently have any saved items");
                    }
                    mItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            selectedItemID = itemIDtoPositionMap.get(i);

                            Intent intent = EditItemActivity.IntentFactory(getApplicationContext(),
                                    MainActivity.currentUser.getLogId(),characterID,selectedItemID);
                            startActivity(intent);
                        }
                    });

                    mAddToListButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = ItemCreateActivity.IntentFactory(getApplicationContext(),
                                    MainActivity.currentUser.getLogId(),characterID);
                            startActivity(intent);
                        }
                    });

                } else if(i == 24){ // Spells

                    //mCharacterAttributes.setVisibility(View.INVISIBLE);
                    mEditCharacterStat.setVisibility(View.INVISIBLE);
                    mSaveButton.setVisibility(View.INVISIBLE);
                    mEditCharacterStat.getText().clear();
                    mItems.setVisibility(View.INVISIBLE);
                    mWeapons.setVisibility(View.INVISIBLE);
                    mSpells.setVisibility(View.VISIBLE);
                    mAddToListButton.setVisibility(View.VISIBLE);
                    mAddToListButton.setText("Add a New Spell");
                    setupSpellsList();
                    if(characterSpellList.isEmpty()){
                        showToast("You don't currently have any saved spells");
                    }
                    mSpells.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            selectedSpellID = spellIDtoPositionMap.get(i);

                            Intent intent = EditSpellActivity.IntentFactory(getApplicationContext(),
                                    MainActivity.currentUser.getLogId(),characterID,selectedSpellID);
                            startActivity(intent);
                        }
                    });

                    mAddToListButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = SpellCreateActivity.IntentFactory(getApplicationContext(),
                                    MainActivity.currentUser.getLogId(),characterID);
                            startActivity(intent);
                        }
                    });

                } else if(i == 25){ // Weapons
                    mCharacterAttributes.setVisibility(View.INVISIBLE);
                    mEditCharacterStat.setVisibility(View.INVISIBLE);
                    mSaveButton.setVisibility(View.INVISIBLE);
                    mEditCharacterStat.getText().clear();
                    mSpells.setVisibility(View.INVISIBLE);
                    mItems.setVisibility(View.INVISIBLE);
                    mWeapons.setVisibility(View.VISIBLE);
                    mAddToListButton.setVisibility(View.VISIBLE);
                    mAddToListButton.setText("Add a New Weapon");
                    setupWeaponsList();
                    if(characterWeaponsList.isEmpty()){
                        showToast("You don't currently have any saved weapons");
                    }
                    mWeapons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            selectedWeaponID = weaponIDtoPositionMap.get(i);

                            Intent intent = EditWeaponActivity.IntentFactory(getApplicationContext(),
                                    MainActivity.currentUser.getLogId(),characterID,selectedWeaponID);
                            startActivity(intent);
                        }
                    });

                    mAddToListButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = WeaponCreateActivity.IntentFactory(getApplicationContext(),
                                    MainActivity.currentUser.getLogId(),characterID);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mCharacterAttributes.getVisibility() == View.INVISIBLE){

                    Intent intent = CharacterEditActivity.IntentFactory(getApplicationContext(),
                            MainActivity.currentUser.getLogId(),
                            CharacterEditActivity.characterID);

                    startActivity(intent);

                } else{
                    Intent intent = CharacterSelectActivity.IntentFactory(getApplicationContext(),MainActivity.currentUser.getLogId());
                    startActivity(intent);
                    setupList();
                }
            }
        });

    }

    private void setupItemsList() {

        characterItemsList.clear();
        itemIDtoPositionMap.clear();

        List<Item> userItems = mDnDVaultDAO.getItemListByUserID(MainActivity.currentUser.getLogId());
        List<Item> potentialCharacterItems = mDnDVaultDAO.getItemByCharacterID(characterID);

        for(int i = 0; i < userItems.size(); i++){

            for(int k = 0; k < potentialCharacterItems.size(); k++){
                if(potentialCharacterItems.get(k).equals(userItems.get(i))){
                    characterItemsList.add(potentialCharacterItems.get(k).toString());
                    itemIDtoPositionMap.put(characterItemsList.size()-1,potentialCharacterItems.get(k).getItemId());
                    break;
                }
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,characterItemsList);
        mItems.setAdapter(arrayAdapter);
    }

    private void setupSpellsList(){
        characterSpellList.clear();
        spellIDtoPositionMap.clear();

        List<Spell> userSpells = mDnDVaultDAO.getSpellListByUserID(MainActivity.currentUser.getLogId());
        List<Spell> potentialCharacterSpells = mDnDVaultDAO.getSpellsByCharacterID(characterID);

        for(int i = 0; i < userSpells.size(); i++){

            for(int k = 0; k < potentialCharacterSpells.size(); k++){
                if(potentialCharacterSpells.get(k).equals(userSpells.get(i))){
                    characterSpellList.add(potentialCharacterSpells.get(k).toString());
                    spellIDtoPositionMap.put(characterSpellList.size()-1,potentialCharacterSpells.get(k).getSpellId());
                    break;
                }
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,characterSpellList);
        mSpells.setAdapter(arrayAdapter);
    }

    private void setupWeaponsList(){
        characterWeaponsList.clear();
        weaponIDtoPositionMap.clear();

        List<Weapon> userWeapons = mDnDVaultDAO.getWeaponListByUserID(MainActivity.currentUser.getLogId());
        List<Weapon> potentialCharacterWeapons = mDnDVaultDAO.getWeaponsByCharacterID(characterID);

        for(int i = 0; i < userWeapons.size(); i++){

            for(int k = 0; k < potentialCharacterWeapons.size(); k++){
                if(potentialCharacterWeapons.get(k).equals(userWeapons.get(i))){
                    characterWeaponsList.add(potentialCharacterWeapons.get(k).toString());
                    weaponIDtoPositionMap.put(characterWeaponsList.size()-1,potentialCharacterWeapons.get(k).getWeaponId());
                    break;
                }
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,characterWeaponsList);
        mWeapons.setAdapter(arrayAdapter);
    }

    private void getCharacter() {
        List<Character> characterList = mDnDVaultDAO.getCharacterListByUserID(MainActivity.currentUser.getLogId());

        for (int i = 0; i < characterList.size(); i++){
            if (characterList.get(i).getCharacterID() == CharacterSelectActivity.characterID){
                currentCharacter = characterList.get(i);
                characterID = currentCharacter.getCharacterID();
                break;
            }
        }
    }

    private void setupList(){
        characterAttributesList.clear();
        characterAttributesList.add("Name: " + currentCharacter.getName());
        characterAttributesList.add("Race: " + currentCharacter.getRace());
        characterAttributesList.add("Class: " + currentCharacter.getCharacterClass());
        characterAttributesList.add("SubClass: " + currentCharacter.getSubClass());
        characterAttributesList.add("Backstory: " + currentCharacter.getBackStory());
        characterAttributesList.add("Traits: " + currentCharacter.getTraits());
        characterAttributesList.add("Ideals: " + currentCharacter.getIdeals());
        characterAttributesList.add("Bonds: " + currentCharacter.getBonds());
        characterAttributesList.add("Flaws: " + currentCharacter.getFlaws());
        characterAttributesList.add("Features: " + currentCharacter.getFeatures());
        characterAttributesList.add("Level: " + currentCharacter.getLevel());
        characterAttributesList.add("HP: " + currentCharacter.getHitPoints());
        characterAttributesList.add("MaxHP: " + currentCharacter.getMaxHitPoints());
        characterAttributesList.add("Strength: " + currentCharacter.getStrength());
        characterAttributesList.add("Dexterity: " + currentCharacter.getDexterity());
        characterAttributesList.add("Constitution: " + currentCharacter.getConstitution());
        characterAttributesList.add("Intelligence: " + currentCharacter.getIntelligence());
        characterAttributesList.add("Wisdom: " + currentCharacter.getWisdom());
        characterAttributesList.add("Charisma: " + currentCharacter.getCharisma());
        characterAttributesList.add("ArmorClass: " + currentCharacter.getArmorClass());
        characterAttributesList.add("Initiative: " + currentCharacter.getInitiative());
        characterAttributesList.add("Inspiration: " + currentCharacter.getInspiration());
        characterAttributesList.add("ProficiencyBonus: " + currentCharacter.getProficiencyBonus());
        characterAttributesList.add("Items: ");
        characterAttributesList.add("Spells: ");
        characterAttributesList.add("Weapons: ");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,characterAttributesList);
        mCharacterAttributes.setAdapter(arrayAdapter);

    }
    private void setupDatabase() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    public static Intent IntentFactory(Context context, int userID, int characterID) {
        Intent intent = new Intent(context, CharacterEditActivity.class);
        intent.putExtra(CHARACTER_EDIT_ID, userID);
        intent.putExtra(CHARACTER_EDIT_ID,characterID);
        return intent;
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}