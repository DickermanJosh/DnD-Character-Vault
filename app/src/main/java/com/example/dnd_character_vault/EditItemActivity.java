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
import com.example.dnd_character_vault.databinding.ActivityCreateAccountPageBinding;
import com.example.dnd_character_vault.databinding.ActivityEditItemBinding;

public class EditItemActivity extends AppCompatActivity {

    private static final String EDIT_ITEM_ID = "com.example.dnd_character_vault.ItemEditID";

    ActivityEditItemBinding binding;

    private EditText mName;
    private EditText mDesc;
    private EditText mAbilities;
    private EditText mAmountHeld;

    private Button mSaveButton;
    private Button mBackButton;
    private Button mDelete;

    private Item originalItem;

    private DnDVaultDAO mDnDVaultDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDB();

        originalItem = mDnDVaultDAO.getItemByItemID(CharacterEditActivity.selectedItemID);

        wireupDisplay();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyFields();

                mDnDVaultDAO.update(originalItem);

                showToast("Item successfully updated");
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
                mDnDVaultDAO.delete(originalItem);
                showToast(originalItem.getName() + " successfully deleted");

                Intent intent = CharacterEditActivity.IntentFactory(getApplicationContext(),
                        MainActivity.currentUser.getLogId(),
                        CharacterEditActivity.characterID);
                startActivity(intent);
            }
        });

    }

    private void verifyFields() {

        if(!mName.getText().toString().isEmpty()){
            originalItem.setName(mName.getText().toString());
        }
        if(!mDesc.getText().toString().isEmpty()){
            originalItem.setDescription(mDesc.getText().toString());
        }
        if(!mAbilities.getText().toString().isEmpty()){
            originalItem.setAbilities(mAbilities.getText().toString());
        }
        if(!mAmountHeld.getText().toString().isEmpty()){
            originalItem.setAmountHeld(Integer.parseInt(mAmountHeld.getText().toString()));
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
        mName = binding.itemNameEditText;
        mName.setHint("Name: " + originalItem.getName());
        mDesc = binding.itemDescEditText;
        mDesc.setHint("Description: " + originalItem.getDescription());
        mAbilities = binding.itemAbilitiesEditText;
        mAbilities.setHint("Abilities: " + originalItem.getAbilities());
        mAmountHeld = binding.itemAmountEditText;
        mAmountHeld.setHint("Amount: " + originalItem.getAmountHeld());
        mSaveButton = binding.saveButton;
        mBackButton = binding.backButton;
        mDelete = binding.deleteItemButton;
    }


    public static Intent IntentFactory(Context context, int userID, int characterID, int itemID) {
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra(EDIT_ITEM_ID, userID);
        intent.putExtra(EDIT_ITEM_ID,characterID);
        intent.putExtra(EDIT_ITEM_ID,itemID);
        return intent;
    }
}