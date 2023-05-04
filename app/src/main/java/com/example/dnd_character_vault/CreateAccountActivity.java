package com.example.dnd_character_vault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.dnd_character_vault.DB.UserLoginDAO;
import com.example.dnd_character_vault.DB.UserLoginDataBase;
import com.example.dnd_character_vault.databinding.ActivityCreateAccountPageBinding;
import com.example.dnd_character_vault.databinding.ActivityLoginPageBinding;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String CREATE_ACCOUNT_ACTIVITY_USER = "com.example.dnd_character_vault.CreateAccountActivityUser";

    ActivityCreateAccountPageBinding binding;

    Button mCreateAcc;

    EditText mUsername;
    EditText mPassword;

    UserLoginDAO mUserLoginDAO;
    List<User> mUserList;
    public static User currentUser;
    public static String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);

        binding = ActivityCreateAccountPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUsername = binding.enterUsernameTextCreateAccPage;
        mPassword = binding.enterPasswordTextCreateAccPage;
        mCreateAcc = binding.createNewAccButtonCreateAccPage;

        mUserLoginDAO = Room.databaseBuilder(this, UserLoginDataBase.class, UserLoginDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mUserLoginDAO();

        mCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUser = new User(mUsername.toString(), mPassword.toString(), false);
                MainActivity.currentUser = currentUser;
                Intent intent = LandingActivity.getIntent(getApplicationContext(), currentUser.getLogId());
                addUserToDB(mUsername.getText().toString(), mPassword.getText().toString());
                startActivity(intent);

            }
        });

    }

    public static Intent getIntent(Context context, int userID) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        intent.putExtra(CREATE_ACCOUNT_ACTIVITY_USER, userID);
        return intent;
    }

    public void addUserToDB(String name, String password) {
        mUserLoginDAO.insert(new User(name, password, false));
    }
}
