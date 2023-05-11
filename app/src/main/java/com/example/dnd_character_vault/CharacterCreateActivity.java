package com.example.dnd_character_vault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityCharacterCreateBinding;

import java.util.ArrayList;
import java.util.List;

public class CharacterCreateActivity extends AppCompatActivity {

    private static final String CHARACTER_CREATE_ID = "com.example.dnd_character_vault.CharacterCreateID";

    ActivityCharacterCreateBinding binding;

    private EditText mName;
    private EditText mRace;
    private EditText mClass;
    private EditText mSubClass;
    private EditText mLevel;
    private EditText mMaxHP;
    private EditText mStrength;
    private EditText mDexterity;
    private EditText mConstitution;
    private EditText mIntelligence;
    private EditText mWisdom;
    private EditText mCharisma;
    private EditText mArmorClass;
    private EditText mInitiative;
    private EditText mInspiration;
    private EditText mProficiencyBonus;
    private EditText mBackstory;
    private EditText mTraits;
    private EditText mIdeals;
    private EditText mBonds;
    private EditText mFlaws;
    private EditText mFeatures;

    private Button mCreate;
    DnDVaultDAO mDnDVaultDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_create);
        binding = ActivityCharacterCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDisplay();
        getDataBase();

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!verifyFields()){
                    return;
                }

                Character newCharacter = new Character(
                        MainActivity.currentUser.getLogId(),
                        mName.getText().toString(),
                        mRace.getText().toString(),
                        mClass.getText().toString(),
                        mSubClass.getText().toString(),
                        Integer.parseInt(mLevel.getText().toString()),
                        Integer.parseInt(mMaxHP.getText().toString()),
                        Integer.parseInt(mStrength.getText().toString()),
                        Integer.parseInt(mDexterity.getText().toString()),
                        Integer.parseInt(mConstitution.getText().toString()),
                        Integer.parseInt(mIntelligence.getText().toString()),
                        Integer.parseInt(mWisdom.getText().toString()),
                        Integer.parseInt(mCharisma.getText().toString()),
                        Integer.parseInt(mArmorClass.getText().toString()),
                        Integer.parseInt(mInitiative.getText().toString()),
                        Integer.parseInt(mInspiration.getText().toString()),
                        Integer.parseInt(mProficiencyBonus.getText().toString()),
                        mBackstory.getText().toString(),
                        mTraits.getText().toString(),
                        mIdeals.getText().toString(),
                        mBonds.getText().toString(),
                        mFlaws.getText().toString(),
                        mFeatures.getText().toString()
                );

                addCharacterToDB(newCharacter);

                Intent intent = CharacterSelectActivity.IntentFactory(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });
    }

    private void setupDisplay() {
        // Wire-up for all the traits that will be needed to be filled out to instantiate a new character
        mName = binding.CreateCharacterName;
        mRace = binding.CreateCharacterRace;
        mClass = binding.CreateCharacterClass;
        mSubClass = binding.CreateCharacterSubclass;
        mLevel = binding.CreateCharacterLevel;
        mMaxHP = binding.CreateCharacterMaxHP;
        mStrength = binding.CreateCharacterStrength;
        mDexterity = binding.CreateCharacterDexterity;
        mConstitution = binding.CreateCharacterConstitution;
        mIntelligence = binding.CreateCharacterIntelligence;
        mWisdom = binding.CreateCharacterWisdom;
        mCharisma = binding.CreateCharacterCharisma;
        mArmorClass = binding.CreateCharacterArmorClass;
        mInitiative = binding.CreateCharacterInitiative;
        mInspiration = binding.CreateCharacterInspiration;
        mProficiencyBonus = binding.CreateCharacterProficiencyBonus;
        mBackstory = binding.CreateCharacterBackstory;
        mTraits = binding.CreateCharacterTraits;
        mIdeals = binding.CreateCharacterIdeals;
        mBonds = binding.CreateCharacterBonds;
        mFlaws = binding.CreateCharacterFlaws;
        mFeatures = binding.CreateCharacterFeatures;
        mCreate = binding.createCharacterButton;
    }

    private void getDataBase() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    private void addCharacterToDB(Character characterToAdd){
        mDnDVaultDAO.insert(characterToAdd);
    }

    public static Intent IntentFactory(Context context, int userID){
        Intent intent = new Intent(context, CharacterCreateActivity.class);
        intent.putExtra(CHARACTER_CREATE_ID,userID);
        return intent;
    }

    private boolean verifyFields(){

        // List of all the EditText fields to verify none of them are null, except for subclass which will just be set to "none"
        List<EditText> characterAttributes = new ArrayList<>();

        characterAttributes.add(mName);
        characterAttributes.add(mRace);
        characterAttributes.add(mClass);
        characterAttributes.add(mBackstory);
        characterAttributes.add(mTraits);
        characterAttributes.add(mIdeals);
        characterAttributes.add(mBonds);
        characterAttributes.add(mFlaws);
        characterAttributes.add(mFeatures);

        // List of just the EditText fields that take Integers, to verify the are not null and are ints
        List<EditText> integerCharacterAttributes = new ArrayList<>();

        integerCharacterAttributes.add(mLevel);
        integerCharacterAttributes.add(mMaxHP);
        integerCharacterAttributes.add(mStrength);
        integerCharacterAttributes.add(mDexterity);
        integerCharacterAttributes.add(mConstitution);
        integerCharacterAttributes.add(mIntelligence);
        integerCharacterAttributes.add(mWisdom);
        integerCharacterAttributes.add(mCharisma);
        integerCharacterAttributes.add(mArmorClass);
        integerCharacterAttributes.add(mInitiative);
        integerCharacterAttributes.add(mInspiration);
        integerCharacterAttributes.add(mProficiencyBonus);

        // Making sure none of the String EditText's are null
        for(int i = 0; i < characterAttributes.size(); i++){
            if(characterAttributes.get(i).getText().toString().isEmpty()){
                showToast("Please fill in " +
                        characterAttributes.get(i).getHint().toString() +
                        " before creating your character");
                return false;
            }
        }

        // Making sure none of the Int EditText's are null & that they are Ints
        for (int i = 0; i < integerCharacterAttributes.size(); i++) {

            if (integerCharacterAttributes.get(i).getText().toString().isEmpty()) {
                showToast("Please fill in "
                        + integerCharacterAttributes.get(i).getHint().toString()
                        + " before creating your character");
                return false;
            }

            try {
                Integer.parseInt(integerCharacterAttributes.get(i).getText().toString());
            } catch (NumberFormatException e) {
                showToast(integerCharacterAttributes.get(i).getText().toString()
                        + " is not a valid number for "
                        + integerCharacterAttributes.get(i).getHint().toString());
                return false;
            }
        }

        if(mSubClass.getText().toString().isEmpty()){
            mSubClass.setText("None");
        }

        showToast("Creating your character now");
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(CharacterCreateActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
