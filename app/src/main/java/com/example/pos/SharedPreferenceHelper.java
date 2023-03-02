package com.example.pos;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    private final String SavedUserLogin = "UserLogin";

    private final String DefaultUsername = "Admin";
    private final String DefaultUser = "DefaultUser";
    private final String DefaultPassword = "Admin";
    private static SharedPreferenceHelper INSTANCE;

    public static synchronized SharedPreferenceHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferenceHelper();
        }
        return INSTANCE;
    }

    public String getDefaultUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DefaultUser, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DefaultUsername, "");
    }

    public String getDefaultUserPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DefaultUser, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DefaultPassword, "");
    }

    public void SaveDefaultUser(String txtUsername, String txtPassword, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DefaultUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DefaultPassword, txtPassword);
        editor.putString(DefaultUsername, txtUsername);
        editor.apply();
    }

    public String getSaveUserLoginName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        String savedUsername = "Username";
        return sharedPreferences.getString(savedUsername, "");
    }

    public String getSaveUserLoginRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        String Role = "UserRole";
        return sharedPreferences.getString(Role, "");
    }

    public String getSaveUserProfilePath(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        String Path = "ProfilePath";
        return sharedPreferences.getString(Path, "");
    }


    public String getSaveUserLoginPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        String savedPassword = "Password";
        return sharedPreferences.getString(savedPassword, "");
    }

    public void SaveUserLogin(String txtUsername, String txtPassword, String txtUserRole,
                              String txtProfilePath,
                              Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String username = "Username";
        editor.putString(username, txtUsername);
        String password = "Password";
        editor.putString(password, txtPassword);
        String Role = "UserRole";
        editor.putString(Role, txtUserRole);
        String Path = "ProfilePath";
        editor.putString(Path, txtProfilePath);
        editor.apply();

    }

}
