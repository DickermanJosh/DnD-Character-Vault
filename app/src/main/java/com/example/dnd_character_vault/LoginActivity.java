package com.example.dnd_character_vault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.databinding.ActivityLoginPageBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN_ACTIVITY_USER = "com.example.dnd_character_vault.LoginActivityUser";

    ActivityLoginPageBinding binding;

    Button mLogin;

    EditText mUsername;
    EditText mPassword;

    DnDVaultDAO mDnDVaultDAO;
    List<User> mUserList;

    User currentUser;

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
                currentUser = mDnDVaultDAO.getUserByUserName(mUsername.getText().toString());
                //if(mUserList.contains(currentUser)){
                    MainActivity.currentUser = currentUser;
                    Intent intent = LandingActivity.getIntent(getApplicationContext(),currentUser.getLogId());
                    startActivity(intent);
                //} else{
                //    Log.d("USER_DOES_NOT_EXIST","Wrong username of password!");
                //}
            }
        });

    }

    public static Intent getIntent(Context context, int userID){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra(LOGIN_ACTIVITY_USER,userID);
        return intent;
    }
}
