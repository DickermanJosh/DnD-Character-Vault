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
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        "Empty",
                        "Empty",
                        "Empty",
                        "Empty",
                        "Empty",
                        "Empty"
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

        // List of all the EditText fields to verify none of them are null
        List<EditText> characterAttributes = new ArrayList<>();

        characterAttributes.add(mName);
        characterAttributes.add(mRace);
        characterAttributes.add(mClass);

        // Making sure none of the String EditText's are null
        for(int i = 0; i < characterAttributes.size(); i++){
            if(characterAttributes.get(i).getText().toString().isEmpty()){
                showToast("Please fill in " +
                        characterAttributes.get(i).getHint().toString() +
                        " before creating your character");
                return false;
            }
        }

        // A subclass isn't always required
        if(mSubClass.getText().toString().isEmpty()){
            mSubClass.setText("Empty");
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(CharacterCreateActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
