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
import java.util.List;

public class CharacterEditActivity extends AppCompatActivity {

    private static final String CHARACTER_EDIT_ID = "com.example.dnd_character_vault.CharacterEditID";

    ActivityCharacterEditBinding binding;

    private ListView mCharacterAttributes;
    private Button mBackButton;
    private Button mSaveButton;
    private EditText mEditCharacterStat;

    private DnDVaultDAO mDnDVaultDAO;

    private Character currentCharacter;
    private List<String> characterAttributesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_edit);
        binding = ActivityCharacterEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mCharacterAttributes = binding.characterListview;
        mBackButton = binding.backButton;
        mSaveButton = binding.saveButton;
        mEditCharacterStat = binding.editCharacterStat;

        setupDatabase();

        getCharacter();

        setupList();

        mCharacterAttributes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Changing the state of the activity to allow the user to edit their information
                mCharacterAttributes.setVisibility(View.INVISIBLE);
                mEditCharacterStat.setVisibility(View.VISIBLE);
                mSaveButton.setVisibility(View.VISIBLE);
                mEditCharacterStat.setHint(characterAttributesList.get(i));
                mBackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: Add save confirmation
                        mCharacterAttributes.setVisibility(View.VISIBLE);
                        mEditCharacterStat.setVisibility(View.INVISIBLE);
                        mSaveButton.setVisibility(View.INVISIBLE);
                        setupList();
                    }
                });

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

                } else if(i == 2){

                } else if(i == 3){

                } else if(i == 4){

                } else if(i == 5){

                } else if(i == 6){

                }

            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add save confirmation
                Intent intent = CharacterSelectActivity.IntentFactory(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });
    }

    private void getCharacter() {
        List<Character> characterList = mDnDVaultDAO.getCharacterListByUserID(MainActivity.currentUser.getLogId());

        for (int i = 0; i < characterList.size(); i++){
            if (characterList.get(i).getCharacterID() == CharacterSelectActivity.characterID){
                currentCharacter = characterList.get(i);
                break;
            }
        }
    }

    private void setupList(){
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
        characterAttributesList.add("Spells: " + currentCharacter.getSpells());

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