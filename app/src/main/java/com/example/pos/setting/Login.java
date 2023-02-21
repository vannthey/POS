package com.example.pos.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.POSDatabase;
import com.example.pos.MainActivity;
import com.example.pos.databinding.ActivityLoginBinding;

import java.util.List;

public class Login extends AppCompatActivity {

    private final String SaveUserLogin = "UserLogin";
    private final String SaveUsername = "Username";
    private final String SavePassword = "Password";
    private final String RememberUser = "RememberUserLogin";
    String Username;
    String Password;
    boolean rememberUser;
    ActivityLoginBinding loginBinding;
    List<UserAccount> userAccounts;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.btnLogin.setOnClickListener(this::btnLogin);
        sharedPreferences = getSharedPreferences(SaveUserLogin, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rememberUser = sharedPreferences.getBoolean(RememberUser, true);
        if (rememberUser == true) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        }

    }

    private void btnLogin(View view) {
        Username = loginBinding.txtUsername.getText().toString();
        Password = loginBinding.txtPassword.getText().toString();
        loginBinding.rememberMeLogin.setOnCheckedChangeListener((compoundButton, b) -> {
            if (loginBinding.rememberMeLogin.isChecked()) {
                rememberUser = true;
            } else {
                rememberUser = false;
            }
        });
        handler = new Handler();
        new Thread(() -> {
            userAccounts =
                    POSDatabase.getInstance(getApplicationContext()).getDao().checkUser(Username,
                            Password);
            for (int i = 0; i < userAccounts.size(); i++) {
                if (Username.equals(userAccounts.get(i).Username) && Password.equals(userAccounts.get(i).Password)) {
                    editor.putString(SaveUsername, userAccounts.get(i).Username);
                    editor.putString(SavePassword, userAccounts.get(i).Password);
                    editor.putBoolean(RememberUser, rememberUser);
                    sharedPreferences.edit().apply();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }
}