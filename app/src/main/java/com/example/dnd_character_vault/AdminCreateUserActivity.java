package com.example.dnd_character_vault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityAdminCreateUserBinding;
import com.example.dnd_character_vault.databinding.ActivityAdminMenuBinding;

public class AdminCreateUserActivity extends AppCompatActivity {

    private static final String ADMIN_CREATE_USER_ID = "com.example.dnd_character_vault.AdminCreateUserID";


    ActivityAdminCreateUserBinding binding;

    private EditText mName;
    private EditText mPassword;
    private CheckBox mIsAdmin;
    private Button mCreateAccount;
    private Button mBack;

    private boolean mUserisAdmin;

    private DnDVaultDAO mDnDVaultDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_user);
        binding = ActivityAdminCreateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDataBase();

        wireupDisplay();

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!verifiyFields()){
                    return;
                }

                if(mIsAdmin.isChecked()){
                    mUserisAdmin = true;
                } else{
                    mUserisAdmin = false;
                }

                User newUser = new User(mName.getText().toString(),
                        mPassword.getText().toString(), mUserisAdmin);

                mDnDVaultDAO.insert(newUser);

                showToast("User Successfully Created");

                mName.setText(null);
                mPassword.setText(null);

            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminMenuActivity.IntentFactory(getApplicationContext(),
                        MainActivity.currentUser.getLogId());
                startActivity(intent);
            }
        });

    }

    private boolean verifiyFields(){

        if(mName.getText().toString().isEmpty()){
            showToast("Enter an Account Name");
            return false;
        }
        if(mPassword.getText().toString().isEmpty()){
            showToast("Enter an Account Password");
            return false;
        }
        return true;
    }

    private void wireupDisplay() {
        mName = binding.enterUsernameTextCreateAccPage;
        mPassword = binding.enterPasswordTextCreateAccPage;
        mIsAdmin = binding.isAdminCheckBox;
        mCreateAccount = binding.createNewAccButtonAdmin;
        mBack = binding.backButton;
    }

    private void setupDataBase() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    public static Intent IntentFactory(Context context, int userID){
        Intent intent = new Intent(context, AdminCreateUserActivity.class);
        intent.putExtra(ADMIN_CREATE_USER_ID,userID);
        return intent;
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}