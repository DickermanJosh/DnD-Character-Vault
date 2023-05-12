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

public class EditSpellActivity extends AppCompatActivity {

    private static final String EDIT_SPELL_ID = "com.example.dnd_character_vault.SpellEditID";

    ActivityEditSpellBinding binding;

    private EditText mName;
    private EditText mDesc;
    private EditText mDamage;
    private EditText mCharges;

    private Button mSaveButton;
    private Button mBackButton;
    private Button mDelete;

    private Spell originalSpell;

    private DnDVaultDAO mDnDVaultDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_spell);

        binding = ActivityEditSpellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDB();

        originalSpell = mDnDVaultDAO.getSpellBySpellID(CharacterEditActivity.selectedSpellID);

        wireupDisplay();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyFields();

                mDnDVaultDAO.update(originalSpell);

                showToast("Spell successfully updated");
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

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDnDVaultDAO.delete(originalSpell);
                showToast(originalSpell.getName() + " successfully deleted");

                Intent intent = CharacterEditActivity.IntentFactory(getApplicationContext(),
                        MainActivity.currentUser.getLogId(),
                        CharacterEditActivity.characterID);
                startActivity(intent);
            }
        });
    }

    private void verifyFields() {

        if(!mName.getText().toString().isEmpty()){
            originalSpell.setName(mName.getText().toString());
        }
        if(!mDesc.getText().toString().isEmpty()){
            originalSpell.setDescription(mDesc.getText().toString());
        }
        if(!mDamage.getText().toString().isEmpty()){
            originalSpell.setDamage(Integer.parseInt(mDamage.getText().toString()));
        }
        if(!mCharges.getText().toString().isEmpty()){
            originalSpell.setCharges(Integer.parseInt(mCharges.getText().toString()));
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
        mName = binding.spellNameEditText;
        mName.setHint("Name: " + originalSpell.getName());
        mDesc = binding.spellDescEditText;
        mDesc.setHint("Description: " + originalSpell.getDescription());
        mDamage = binding.spellDamageEditText;
        mDamage.setHint("Damage: " + originalSpell.getDamage());
        mCharges = binding.spellChargesEditText;
        mCharges.setHint("Charges: " + originalSpell.getCharges());
        mSaveButton = binding.saveButton;
        mBackButton = binding.backButton;
        mDelete = binding.deleteSpellButton;
    }


    public static Intent IntentFactory(Context context, int userID, int characterID, int spellID) {
        Intent intent = new Intent(context, EditSpellActivity.class);
        intent.putExtra(EDIT_SPELL_ID, userID);
        intent.putExtra(EDIT_SPELL_ID,characterID);
        intent.putExtra(EDIT_SPELL_ID,spellID);
        return intent;
    }
}