package com.example.dnd_character_vault;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dnd_character_vault.DB.DnDAppDataBase;

import java.util.List;

@Entity(tableName = DnDAppDataBase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mLogId;

    private String mUserName;
    private String mPassword;

    //private List<Character> mCharacters;
    private boolean isAdmin;

    public User(String userName, String password, boolean isAdmin) {
        mUserName = userName;
        mPassword = password;
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User{" +
                "mLogId=" + mLogId +
                ", mUserName=" + mUserName +
                ", mPassword=" + mPassword +
                ", isAdmin=" + isAdmin +
                '}';
    }

    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int logId) {
        mLogId = logId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
