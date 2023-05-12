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
import com.example.dnd_character_vault.databinding.ActivityEditSpellBinding;
import com.example.dnd_character_vault.databinding.ActivityEditWeaponBinding;

public class EditWeaponActivity extends AppCompatActivity {


    private static final String EDIT_WEAPON_ID = "com.example.dnd_character_vault.WeaponEditID";

    ActivityEditWeaponBinding binding;

    private EditText mName;
    private EditText mDesc;
    private EditText mDamage;
    private EditText mAbilities;
    private EditText mAmountHeld;

    private Button mSaveButton;
    private Button mBackButton;
    private Button mDeleteButton;

    private Weapon originalWeapon;

    private DnDVaultDAO mDnDVaultDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_weapon);

        binding = ActivityEditWeaponBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDB();

        originalWeapon = mDnDVaultDAO.getWeaponByWeaponID(CharacterEditActivity.selectedWeaponID);

        wireupDisplay();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyFields();

                mDnDVaultDAO.update(originalWeapon);

                showToast("Weapon successfully updated");
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CharacterEditActivity.IntentFactory(getApplicationContext(),
                        MainActivity.currentUser.getLogId(),
                        CharacterEditActivity.characterID);
                startActivity(intent);
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDnDVaultDAO.delete(originalWeapon);
                showToast(originalWeapon.getName() + " successfully deleted");

                Intent intent = CharacterEditActivity.IntentFactory(getApplicationContext(),
                        MainActivity.currentUser.getLogId(),
                        CharacterEditActivity.characterID);
                startActivity(intent);
            }
        });
    }

    private void verifyFields() {

        if(!mName.getText().toString().isEmpty()){
            originalWeapon.setName(mName.getText().toString());
        }
        if(!mDesc.getText().toString().isEmpty()){
            originalWeapon.setDescription(mDesc.getText().toString());
        }
        if(!mDamage.getText().toString().isEmpty()){
            originalWeapon.setDamage(Integer.parseInt(mDamage.getText().toString()));
        }
        if(!mAbilities.getText().toString().isEmpty()){
            originalWeapon.setAbilities(mAbilities.getText().toString());
        }
        if(!mAmountHeld.getText().toString().isEmpty()){
            originalWeapon.setAmountHeld(Integer.parseInt(mAmountHeld.getText().toString()));
        }
    }

    private void setupDB() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void wireupDisplay(){
        mName = binding.weaponNameEditText;
        mName.setHint("Name: " + originalWeapon.getName());
        mDesc = binding.weaponDescEditText;
        mDesc.setHint("Description: " + originalWeapon.getDescription());
        mAbilities = binding.weaponAbilitiesEditText;
        mAbilities.setHint("Abilities: " + originalWeapon.getAbilities());
        mDamage = binding.weaponDamageEditText;
        mDamage.setHint("Damage: " + originalWeapon.getDamage());
        mAmountHeld = binding.weaponAmountHeldEditText;
        mAmountHeld.setHint("Amount Held: " + originalWeapon.getAmountHeld());
        mSaveButton = binding.saveButton;
        mBackButton = binding.backButton;
        mDeleteButton = binding.deleteWeaponButton;
    }


    public static Intent IntentFactory(Context context, int userID, int characterID, int weaponID) {
        Intent intent = new Intent(context, EditWeaponActivity.class);
        intent.putExtra(EDIT_WEAPON_ID, userID);
        intent.putExtra(EDIT_WEAPON_ID,characterID);
        intent.putExtra(EDIT_WEAPON_ID,weaponID);
        return intent;
    }
}