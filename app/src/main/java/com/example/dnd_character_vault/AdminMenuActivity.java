package com.example.dnd_character_vault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dnd_character_vault.DB.DnDAppDataBase;
import com.example.dnd_character_vault.DB.DnDVaultDAO;
import com.example.dnd_character_vault.databinding.ActivityAdminMenuBinding;
import com.example.dnd_character_vault.databinding.ActivityCharacterCreateBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminMenuActivity extends AppCompatActivity {

    private static final String ADMIN_MENU_ID = "com.example.dnd_character_vault.AdminMenuID";

    ActivityAdminMenuBinding binding;

    private ListView mUserList;
    private Button mDeleteUserButton;
    private Button mCreateUserButton;
    private Button mBackButton;
    private EditText mDeleteUserEditText;

    private List<User> mAllUsers = new ArrayList<>();
    private Map<Integer,Integer> userIDtoListPositionMap = new HashMap<>();

    private int selectedUserID;
    private DnDVaultDAO mDnDVaultDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        binding = ActivityAdminMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDeleteUserButton.setVisibility(View.INVISIBLE);

        setupDataBase();

        wireupDisplay();

        displayUserList();

        mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUserID = userIDtoListPositionMap.get(i);

            }
        });
    }

    private void displayUserList(){

        mAllUsers.clear();
        userIDtoListPositionMap.clear();
        mAllUsers = mDnDVaultDAO.getAllUsers();

        for(int i = 0; i < mAllUsers.size(); i++){
            userIDtoListPositionMap.put(i,mAllUsers.get(i).getLogId());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,mAllUsers);
        mUserList.setAdapter(arrayAdapter);
    }

    private void wireupDisplay() {
        mUserList = binding.allUsersListview;
        mDeleteUserButton = binding.deleteUserButton;
        mCreateUserButton = binding.createNewUserButton;
        mBackButton = binding.backButton;
        mDeleteUserEditText = binding.deleteUserEditText;
    }

    private void setupDataBase() {
        mDnDVaultDAO = Room.databaseBuilder(this, DnDAppDataBase.class, DnDAppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().mDnDVaultDAO();
    }

    public static Intent IntentFactory(Context context, int userID){
        Intent intent = new Intent(context, AdminMenuActivity.class);
        intent.putExtra(ADMIN_MENU_ID,userID);
        return intent;
    }
}