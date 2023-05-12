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
import com.example.dnd_character_vault.databinding.ActivitySpellCreateBinding;
import com.example.dnd_character_vault.databinding.ActivityWeaponCreateBinding;

import java.util.ArrayList;
import java.util.List;

public class WeaponCreateActivity extends AppCompatActivity {
    private static final String WEAPON_CREATE_USER = "com.example.dnd_character_vault.WeaponCreateUser";



    ActivityWeaponCreateBinding binding;

    private EditText mName;
    private EditText mDamage;
    private EditText mDesc;
    private EditText mAbilities;
    private EditText mAmountHeld;

    private Button mSaveButton;
    private Button mBackButton;

    private Weapon createdWeapon;
    private DnDVaultDAO mDnDVaultDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_create);

        binding = ActivityWeaponCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDB();
        wireupDisplay();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verifyFields()){
                    return;
                }
                createdWeapon = new Weapon(MainActivity.currentUser.getLogId(),
                        CharacterEditActivity.characterID,mName.getText().toString(),
                        Integer.parseInt(mDamage.getText().toString()),mDesc.getText().toString(),mAbilities.getText().toString(),
                        Integer.parseInt(mAmountHeld.getText().toString()));

                addWeaponToDB(createdWeapon);

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

    private void addWeaponToDB(Weapon weaponToAdd) {
        mDnDVaultDAO.insert(weaponToAdd);
    }

    private void setupDB() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    private boolean verifyFields() {
        List<EditText> weaponFields = new ArrayList<>();
        weaponFields.add(mName);
        weaponFields.add(mDamage);
        weaponFields.add(mDesc);
        weaponFields.add(mAbilities);
        weaponFields.add(mAmountHeld);

        for(int i = 0; i < weaponFields.size(); i++){
            if(weaponFields.get(i).getText().toString().isEmpty()){
                showToast("Please fill out all fields before creating your weapon");
                return false;
            }
        }
        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void wireupDisplay(){
        mName = binding.weaponNameEditText;
        mDamage = binding.weaponDamageEditText;
        mDesc = binding.weaponDescEditText;
        mAbilities = binding.weaponAbilitiesEditText;
        mAmountHeld = binding.weaponAmountHeldEditText;
        mSaveButton = binding.saveButton;
        mBackButton = binding.backButton;
    }


    public static Intent IntentFactory(Context context, int userID, int characterID){
        Intent intent = new Intent(context,WeaponCreateActivity.class);
        intent.putExtra(WEAPON_CREATE_USER,userID);
        intent.putExtra(WEAPON_CREATE_USER,characterID);
        return intent;
    }
}