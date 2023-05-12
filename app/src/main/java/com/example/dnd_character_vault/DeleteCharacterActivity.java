package com.example.dnd_character_vault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Delete;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityAdminCreateUserBinding;
import com.example.dnd_character_vault.databinding.ActivityDeleteCharacterBinding;

import org.w3c.dom.Text;

import java.util.List;

public class DeleteCharacterActivity extends AppCompatActivity {

    private static final String DELETE_CHARACTER = "com.example.dnd_character_vault.DELETE_CHARACTER";

    ActivityDeleteCharacterBinding binding;

    private TextView mPrompt;
    private Button mDelete;
    private Button mBack;

    private Character currentCharacter;

    private DnDVaultDAO mDnDVaultDAO;

    public DeleteCharacterActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_character);
        binding = ActivityDeleteCharacterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDataBase();
        getCurrentCharacter();
        wireupDisplay();


        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!getCurrentCharacter()){
                    return;
                }
                mDnDVaultDAO.delete(currentCharacter);
                showToast(currentCharacter.getName() + " was successfully deleted");

                // If the user still has characters in their account they are sent the character select screen
                // Otherwise they are sent the landing page
                if(!mDnDVaultDAO.getCharacterListByUserID(MainActivity.currentUser.getLogId()).isEmpty()){
                    Intent intent = CharacterSelectActivity.IntentFactory(getApplicationContext(),
                            MainActivity.currentUser.getLogId());
                    startActivity(intent);
                } else{
                    Intent intent = LandingActivity.IntentFactory(getApplicationContext(),
                            MainActivity.currentUser.getLogId());
                    startActivity(intent);
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CharacterSelectActivity.IntentFactory(getApplicationContext(),
                        MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });
    }

    private void wireupDisplay() {
        mPrompt = binding.promptTextView;
        mPrompt.setText("Are you sure you want to delete this character?\n" + currentCharacter.getName());
        mDelete = binding.deleteButton;
        mBack = binding.backButton;
    }

    private boolean getCurrentCharacter(){
        currentCharacter = mDnDVaultDAO.getCharacterByCharacterID(CharacterEditActivity.characterID);

        if(currentCharacter != null){
            return true;
        }
        showToast("Character is null");
        return false;
/*        List<Character> userCharacter = mDnDVaultDAO.getCharacterListByUserID(MainActivity.currentUser.getLogId());
        Character potentialCharacter = mDnDVaultDAO.getCharacterByCharacterID(CharacterEditActivity.characterID);

        for(int i = 0; i < userCharacter.size(); i++){
            if (userCharacter.get(i).equals(potentialCharacter)){
                currentCharacter = potentialCharacter;
                return true;
            }
        }
        showToast("Couldn't find character");
        return false;*/
    }

    private void setupDataBase() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    public static Intent IntentFactory(Context context, int userID, int characterID){
        Intent intent = new Intent(context, DeleteCharacterActivity.class);
        intent.putExtra(DELETE_CHARACTER,userID);
        intent.putExtra(DELETE_CHARACTER,characterID);
        return intent;
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}