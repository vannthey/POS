package com.example.pos.Configure;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
    private final String SavedUserLogin = "UserLogin";
    private final String DefaultUsername = "Admin";
    private final String DefaultUser = "DefaultUser";
    private final String DefaultPassword = "Admin";
    private final String customizeCustomerId = "CustomerById";
    private final String saleCustomerIndex = "CustomerIndex";
    private final String customerId = "CustomerId";
    private static SharedPrefHelper INSTANCE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static synchronized SharedPrefHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SharedPrefHelper();
        }
        return INSTANCE;
    }

    public String getDefaultUser(Context context) {
        sharedPreferences = context.getSharedPreferences(DefaultUser, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DefaultUsername, "");
    }

    public String getDefaultUserPassword(Context context) {
        sharedPreferences = context.getSharedPreferences(DefaultUser, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DefaultPassword, "");
    }

    public void ClearDefaultUser(Context context) {

        sharedPreferences = context.getSharedPreferences(DefaultUser, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

    public void SaveDefaultUser(String txtUsername, String txtPassword, Context context) {
        sharedPreferences = context.getSharedPreferences(DefaultUser, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(DefaultPassword, txtPassword);
        editor.putString(DefaultUsername, txtUsername);
        editor.apply();
    }

    public String getSaveUserLoginName(Context context) {
        sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        String savedUsername = "Username";
        return sharedPreferences.getString(savedUsername, "");
    }

    public String getSaveUserLoginRole(Context context) {
        sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        String Role = "UserRole";
        return sharedPreferences.getString(Role, "");
    }

    public String getSaveUserProfilePath(Context context) {
        sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        String Path = "ProfilePath";
        return sharedPreferences.getString(Path, "");
    }


    public String getSaveUserLoginPassword(Context context) {
        sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        String savedPassword = "Password";
        return sharedPreferences.getString(savedPassword, "");
    }

    public void ClearUser(Context context) {

        sharedPreferences = context.getSharedPreferences(SavedUserLogin, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

    public void SaveUserLogin(String txtUsername, String txtPassword, String txtUserRole,
                              String txtProfilePath,
                              Context context) {
        sharedPreferences = context.getSharedPreferences(SavedUserLogin,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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

    public void SaveCustomerSaleIndex(int i, Context c) {
        sharedPreferences = c.getSharedPreferences(saleCustomerIndex, 0);
        editor = sharedPreferences.edit();
        String customerIndex = "CustomerIndex";
        editor.putInt(customerIndex, i);
        editor.apply();
    }

    public int getSaveCustomerIndex(Context c) {
        sharedPreferences = c.getSharedPreferences(saleCustomerIndex, 0);
        String customerIndex = "CustomerIndex";
        return sharedPreferences.getInt(customerIndex, 0);
    }
}
