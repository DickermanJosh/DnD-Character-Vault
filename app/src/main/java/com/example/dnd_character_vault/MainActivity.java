package com.example.dnd_character_vault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER = "com.example.dnd_character_vault.MainActivityUser";

    ActivityMainBinding binding;

    TextView mLoginDisplay;
    Button mLogin;
    Button mCreateAccount;

    DnDVaultDAO mDnDVaultDAO;

    List<User> mUserList;

    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mLoginDisplay = binding.textViewHeader;
        mLogin = binding.signInButtonLoginscreen;
        mCreateAccount = binding.createNewAccButtonLoginscreen;

        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mUserLoginDAO();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.getIntent(getApplicationContext(),0);
                startActivity(intent);
            }
        });

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateAccountActivity.getIntent(getApplicationContext(),0);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, int userID){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER,userID);
        return intent;
    }
}