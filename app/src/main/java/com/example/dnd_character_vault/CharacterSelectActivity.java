package com.example.dnd_character_vault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dnd_character_vault.databinding.ActivityCharacterSelectPageBinding;

public class CharacterSelectActivity extends AppCompatActivity {
    private static final String USER_CHARACTER_SELECT = "com.example.dnd_character_vault.UserCharacterSelect";

    ActivityCharacterSelectPageBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_character_select_page);

        binding = ActivityCharacterSelectPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public static Intent getIntent(Context context, int userID) {
        Intent intent = new Intent(context, CharacterSelectActivity.class);
        intent.putExtra(USER_CHARACTER_SELECT, userID);
        return intent;
    }
}
