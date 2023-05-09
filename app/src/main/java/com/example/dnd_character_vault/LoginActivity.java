package com.example.dnd_character_vault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.databinding.ActivityLoginPageBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN_ACTIVITY_USER = "com.example.dnd_character_vault.LoginActivityUser";

    ActivityLoginPageBinding binding;

    private Button mLogin;

    private EditText mUsername;
    private EditText mPassword;

    DnDVaultDAO mDnDVaultDAO;
    private List<User> mUserList;

    private User mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUsername = binding.enterUsernameText;
        mPassword = binding.enterPasswordText;
        mLogin = binding.signInButtonLoginscreen;

        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mUserLoginDAO();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking to ensure both fields were filled in
                if(mUsername.getText().toString().length() == 0){
                    Toast.makeText(LoginActivity.this, "Please enter a username to login", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPassword.getText().toString().length() == 0){
                    Toast.makeText(LoginActivity.this, "Please enter a password to login", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validating that the user exists in the DB with the proper username / password
                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    } else{
                        // currentUser = mDnDVaultDAO.getUserByUserName(mUsername.getText().toString());
                        MainActivity.currentUser = mCurrentUser;
                        Intent intent = LandingActivity.IntentFactory(getApplicationContext(), mCurrentUser.getLogId());
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean checkForUserInDatabase(){
        mCurrentUser = mDnDVaultDAO.getUserByUserName(mUsername.getText().toString());
        if(mCurrentUser == null){
            Toast.makeText(this, "No user with username: " + mUsername.getText().toString() + " was found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword(){
        return mCurrentUser.getPassword().equals(mPassword.getText().toString());
    }

    public static Intent IntentFactory(Context context, int userID){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra(LOGIN_ACTIVITY_USER,userID);
        return intent;
    }
}
