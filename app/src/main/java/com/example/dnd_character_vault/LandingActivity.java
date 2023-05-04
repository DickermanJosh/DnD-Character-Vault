package com.example.dnd_character_vault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dnd_character_vault.DB.UserLoginDAO;
import com.example.dnd_character_vault.DB.UserLoginDataBase;
import com.example.dnd_character_vault.databinding.ActivityLandingPageBinding;
import com.example.dnd_character_vault.databinding.ActivityLoginPageBinding;

public class LandingActivity extends AppCompatActivity {

    private static final String LANDING_ACTIVITY_USER = "com.example.dnd_character_vault.LandingActivityUser";

    ActivityLandingPageBinding binding;

    TextView mWelcome;
    Button mLogout;
    Button mAdminThings;

    Button mCharacterSelect;

    UserLoginDAO mUserLoginDAO;

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

        mUserLoginDAO = Room.databaseBuilder(this, UserLoginDataBase.class, UserLoginDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mUserLoginDAO();

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
                Intent intent = MainActivity.getIntent(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });

        mCharacterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CharacterSelectActivity.getIntent(getApplicationContext(),MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, int userID){
        Intent intent = new Intent(context,LandingActivity.class);
        intent.putExtra(LANDING_ACTIVITY_USER,userID);
        return intent;
    }
}
