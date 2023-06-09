package com.example.dnd_character_vault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityLandingPageBinding;

public class LandingActivity extends AppCompatActivity {

    private static final String LANDING_ACTIVITY_USER = "com.example.dnd_character_vault.LandingActivityUser";

    ActivityLandingPageBinding binding;

    private TextView mWelcome;
    private Button mLogout;
    private Button mAdminThings;

    private Button mCharacterSelect;

    private Button mCreateNewCharacterButton;

    DnDVaultDAO mDnDVaultDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mWelcome = binding.landingWelcomeTextview;
        mLogout = binding.logoutButton;
        mAdminThings = binding.adminButton;
        mCharacterSelect = binding.CharacterSelectButton;
        mCreateNewCharacterButton = binding.createNewCharacterButton;

        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();

        // Not displaying name correctly from CreateAccount, but it's fine from Login
        mWelcome.setText("Welcome, " + MainActivity.currentUser.getUserName().toString());

        if(MainActivity.currentUser.isAdmin()){
            mAdminThings.setVisibility(View.VISIBLE);
        } else{
            mAdminThings.setVisibility(View.INVISIBLE);
        }

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.IntentFactory(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });

        mCharacterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If there are no characters in the user's list a Toast will appear
                if(mDnDVaultDAO.getCharacterListByUserID(MainActivity.currentUser.getLogId()).isEmpty()){
                    Toast.makeText(LandingActivity.this, "You don't have any existing characters yet!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If the user has any saved characters they will be allowed to continue to the activity
                Intent intent = CharacterSelectActivity.IntentFactory(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });

        mCreateNewCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CharacterCreateActivity.IntentFactory(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });

        mAdminThings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminMenuActivity.IntentFactory(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });
    }

    public static Intent IntentFactory(Context context, int userID){
        Intent intent = new Intent(context,LandingActivity.class);
        intent.putExtra(LANDING_ACTIVITY_USER,userID);
        return intent;
    }
}
