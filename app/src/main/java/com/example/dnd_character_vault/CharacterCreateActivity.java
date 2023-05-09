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
                Toast.makeText(CharacterCreateActivity.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDisplay() {
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
        mBonds = binding.CreateCharacterBonds;
        mFlaws = binding.CreateCharacterFlaws;
        mFeatures = binding.CreateCharacterFeatures;
        mCreate = binding.createCharacterButton;
    }

    private void getDataBase() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    private void addCharacterToDB(){
        //TODO: Write method
    }

    public static Intent IntentFactory(Context context, int userID){
        Intent intent = new Intent(context, CharacterCreateActivity.class);
        intent.putExtra(CHARACTER_CREATE_ID,userID);
        return intent;
    }

}
