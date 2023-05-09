package com.example.dnd_character_vault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static final String PREFERENCES_KEY = "com.example.dnd_character_vault.PreferenceKey";
    ActivityMainBinding binding;

    TextView mLoginDisplay;
    Button mLogin;
    Button mCreateAccount;
    public static User currentUser;
    private int mUserId = -1;
    private SharedPreferences mPreferences = null;
    DnDVaultDAO mDnDVaultDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mLoginDisplay = binding.textViewHeader;
        mLogin = binding.signInButtonLoginscreen;
        mCreateAccount = binding.createNewAccButtonLoginscreen;

        getDatabase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.IntentFactory(getApplicationContext(),mUserId);
                startActivity(intent);
            }
        });

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateAccountActivity.IntentFactory(getApplicationContext(),mUserId);
                startActivity(intent);
            }
        });
    }

    private void getDatabase(){
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mUserLoginDAO();
    }

    private void loginUser(int userId) {
        currentUser = mDnDVaultDAO.getUserByUserId(userId);
        invalidateOptionsMenu();
    }

    private void checkForUser() {
        // Do we have a user in the intent?
        mUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER, -1);
        // Do we have a user in the preferences?
        if(mUserId != -1){
            return;
        }

        if(mPreferences == null){
            getPrefs();
        }
        mUserId = mPreferences.getInt(MAIN_ACTIVITY_USER,-1);

        if(mUserId != -1){
            return;
        }

        // Do we have any users at all?
        List<User> users = mDnDVaultDAO.getAllUsers();
        if(users.size() <= 0){
            User defaultUser = new User("test1","test1",false);
            User defaultAdmin = new User("admin2","admin2",true);
            mDnDVaultDAO.insert(defaultUser,defaultAdmin);

        }
        //Intent intent = CharacterSelectActivity.IntentFactory(this,-1);
        //startActivity(intent);
    }

    private void addUserToPreference(int userId) {
        if(mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(MAIN_ACTIVITY_USER,userId);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(MAIN_ACTIVITY_USER,-1);
    }

    private void clearUserFromPref(){
        addUserToPreference(-1);
    }

    public static Intent IntentFactory(Context context, int userID){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER,userID);
        return intent;
    }
}