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
import com.example.dnd_character_vault.databinding.ActivityCreateAccountPageBinding;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String CREATE_ACCOUNT_ACTIVITY_USER = "com.example.dnd_character_vault.CreateAccountActivityUser";

    ActivityCreateAccountPageBinding binding;

    Button mCreateAcc;

    EditText mUsername;
    EditText mPassword;

    DnDVaultDAO mDnDVaultDAO;
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

        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mUserLoginDAO();

        mCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking to ensure both fields were filled in
                if(mUsername.getText().toString().length() == 0){
                    Toast.makeText(CreateAccountActivity.this, "Please enter a username to login", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPassword.getText().toString().length() == 0){
                    Toast.makeText(CreateAccountActivity.this, "Please enter a password to login", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!checkForExistingAccount()){
                    return;
                }

                currentUser = new User(mUsername.toString(), mPassword.toString(), false);
                MainActivity.currentUser = currentUser;
                Intent intent = LandingActivity.IntentFactory(getApplicationContext(), currentUser.getLogId());
                addUserToDB(mUsername.getText().toString(), mPassword.getText().toString());
                startActivity(intent);

            }
        });

    }

    private boolean checkForExistingAccount(){
        currentUser = mDnDVaultDAO.getUserByUserName(mUsername.getText().toString());
        if(currentUser != null){
            Toast.makeText(this, "Username: " + mUsername.getText().toString() + " is already taken, please try another.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Intent IntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        intent.putExtra(CREATE_ACCOUNT_ACTIVITY_USER, userID);
        return intent;
    }

    public void addUserToDB(String name, String password) {
        mDnDVaultDAO.insert(new User(name, password, false));
    }
}
