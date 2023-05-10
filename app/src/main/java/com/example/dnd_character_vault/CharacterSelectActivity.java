package com.example.dnd_character_vault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityCharacterSelectPageBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterSelectActivity extends AppCompatActivity {
    private static final String USER_CHARACTER_SELECT = "com.example.dnd_character_vault.UserCharacterSelect";

    ActivityCharacterSelectPageBinding binding;

    private ListView mCharacterListView;

    private Button mBackButton;

    Map<Integer,Integer> mCharacterListPositionMap = new HashMap<>();

    private DnDVaultDAO mDnDVaultDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select_page);

        binding = ActivityCharacterSelectPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mCharacterListView = binding.characterListview;
        mBackButton = binding.backButton;

        setupDataBase();

        displayCharacterList();

        mCharacterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = CharacterEditActivity.IntentFactory(getApplicationContext(),
                        MainActivity.currentUser.getLogId(),mCharacterListPositionMap.get(i));
                startActivity(intent);
                }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingActivity.IntentFactory(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });

    }

    private void setupDataBase() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    private void displayCharacterList() {

        List<String> characterListDisplay = new ArrayList<>();
        List<Character> characterList;
        characterList = mDnDVaultDAO.getCharacterListByUserID(MainActivity.currentUser.getLogId());


        for(int i = 0; i < characterList.size(); i++){
            characterListDisplay.add(characterList.get(i).toString());
            int characterID = characterList.get(i).getCharacterID();
            mCharacterListPositionMap.put(i,characterID);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,characterListDisplay);
        mCharacterListView.setAdapter(arrayAdapter);
    }
    public static Intent IntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, CharacterSelectActivity.class);
        intent.putExtra(USER_CHARACTER_SELECT, userID);
        return intent;
    }
}
