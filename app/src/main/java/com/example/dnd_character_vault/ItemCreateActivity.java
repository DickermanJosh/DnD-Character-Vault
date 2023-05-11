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
import com.example.dnd_character_vault.databinding.ActivityLandingPageBinding;

import java.util.ArrayList;
import java.util.List;

public class ItemCreateActivity extends AppCompatActivity {

    private static final String ITEM_CREATE_USER = "com.example.dnd_character_vault.ItemCreateUser";



    ActivityItemCreateBinding binding;

    private EditText mName;
    private EditText mDesc;
    private EditText mAbilities;
    private EditText mAmountHeld;

    private Button mSaveButton;
    private Button mBackButton;

    private Item createdItem;
    private DnDVaultDAO mDnDVaultDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        binding = ActivityItemCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDB();
        wireupDisplay();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verifyFields()){
                    return;
                }
                createdItem = new Item(MainActivity.currentUser.getLogId(),
                        CharacterEditActivity.characterID,mName.getText().toString(),
                        mDesc.getText().toString(),mAbilities.getText().toString(),
                        Integer.parseInt(mAmountHeld.getText().toString()));

                addItemToDB(createdItem);

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

    private void addItemToDB(Item itemToAdd) {
        mDnDVaultDAO.insert(itemToAdd);
    }

    private void setupDB() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    private boolean verifyFields() {
        List<EditText> itemFields = new ArrayList<>();
        itemFields.add(mName);
        itemFields.add(mDesc);
        itemFields.add(mAbilities);
        itemFields.add(mAmountHeld);

        for(int i = 0; i < itemFields.size(); i++){
            if(itemFields.get(i).getText().toString().isEmpty()){
                showToast("Please fill out all fields before creating your item");
                return false;
            }
        }
        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void wireupDisplay(){
        mName = binding.itemNameEditText;
        mDesc = binding.itemDescEditText;
        mAbilities = binding.itemAbilitiesEditText;
        mAmountHeld = binding.itemAmountEditText;
        mSaveButton = binding.saveButton;
        mBackButton = binding.backButton;
    }


    public static Intent IntentFactory(Context context, int userID, int characterID){
        Intent intent = new Intent(context,ItemCreateActivity.class);
        intent.putExtra(ITEM_CREATE_USER,userID);
        intent.putExtra(ITEM_CREATE_USER,characterID);
        return intent;
    }
}