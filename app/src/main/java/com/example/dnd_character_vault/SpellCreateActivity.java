package com.example.dnd_character_vault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityItemCreateBinding;
import com.example.dnd_character_vault.databinding.ActivitySpellCreateBinding;

import java.util.ArrayList;
import java.util.List;

public class SpellCreateActivity extends AppCompatActivity {
    private static final String SPELL_CREATE_USER = "com.example.dnd_character_vault.SpellCreateUser";



    ActivitySpellCreateBinding binding;

    private EditText mName;
    private EditText mDesc;
    private EditText mDamage;
    private EditText mCharges;

    private Button mSaveButton;
    private Button mBackButton;

    private Spell createdSpell;
    private DnDVaultDAO mDnDVaultDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_create);

        binding = ActivitySpellCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDB();
        wireupDisplay();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verifyFields()){
                    return;
                }
                createdSpell = new Spell(MainActivity.currentUser.getLogId(),
                        CharacterEditActivity.characterID,mName.getText().toString(),
                        Integer.parseInt(mDamage.getText().toString()),mDesc.getText().toString(),
                        Integer.parseInt(mCharges.getText().toString()));

                addSpellToDB(createdSpell);

            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add confirmation if fields haven't been saved
                Intent intent = CharacterEditActivity.IntentFactory(getApplicationContext(),
                        MainActivity.currentUser.getLogId(),
                        CharacterEditActivity.characterID);

                startActivity(intent);
            }
        });

    }

    private void addSpellToDB(Spell spellToAdd) {
        mDnDVaultDAO.insert(spellToAdd);
    }

    private void setupDB() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    private boolean verifyFields() {
        List<EditText> spellFields = new ArrayList<>();
        spellFields.add(mName);
        spellFields.add(mDesc);
        spellFields.add(mDamage);
        spellFields.add(mCharges);

        for(int i = 0; i < spellFields.size(); i++){
            if(spellFields.get(i).getText().toString().isEmpty()){
                showToast("Please fill out all fields before creating your spell");
                return false;
            }
        }
        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void wireupDisplay(){
        mName = binding.spellNameEditText;
        mDesc = binding.spellDescEditText;
        mDamage = binding.spellDamageEditText;
        mCharges = binding.spellChargesEditText;
        mSaveButton = binding.saveButton;
        mBackButton = binding.backButton;
    }


    public static Intent IntentFactory(Context context, int userID, int characterID){
        Intent intent = new Intent(context,SpellCreateActivity.class);
        intent.putExtra(SPELL_CREATE_USER,userID);
        intent.putExtra(SPELL_CREATE_USER,characterID);
        return intent;
    }
}