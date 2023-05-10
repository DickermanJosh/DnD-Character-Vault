package com.example.dnd_character_vault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityCharacterCreateBinding;
import com.example.dnd_character_vault.databinding.ActivityCharacterEditBinding;

public class CharacterEditActivity extends AppCompatActivity {

    private static final String CHARACTER_EDIT_ID = "com.example.dnd_character_vault.CharacterEditID";

    ActivityCharacterEditBinding binding;

    private DnDVaultDAO mDnDVaultDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_edit);
        binding = ActivityCharacterEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    private void getDataBase() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    public static Intent IntentFactory(Context context, int userID, int characterID) {
        Intent intent = new Intent(context, CharacterEditActivity.class);
        intent.putExtra(CHARACTER_EDIT_ID, userID);
        intent.putExtra(CHARACTER_EDIT_ID,characterID);
        return intent;
    }
}