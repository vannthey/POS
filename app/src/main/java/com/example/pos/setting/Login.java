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
    String Username;
    String Password;
    ActivityLoginBinding loginBinding;
    List<UserAccount> userAccounts;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.btnLogin.setOnClickListener(this::btnLogin);
        sharedPreferences = getSharedPreferences(SaveUserLogin, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Username = sharedPreferences.getString(SaveUsername, null);
        Password = sharedPreferences.getString(SavePassword, null);
        if (Username!=null && Password!=null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void btnLogin(View view) {
        Username = loginBinding.txtUsername.getText().toString();
        Password = loginBinding.txtPassword.getText().toString();
        Handler handler = new Handler();
        new Thread(() -> {
            userAccounts =
                    POSDatabase.getInstance(getApplicationContext()).getDao().checkUser(Username,
                            Password);
            handler.post(() -> {
                for (int i = 0; i < userAccounts.size(); i++) {
                    if (Username.equals(userAccounts.get(i).Username) && Password.equals(userAccounts.get(i).Password)) {
                        loginBinding.rememberMeLogin.setOnCheckedChangeListener((compoundButton, b) -> {
                            if (loginBinding.rememberMeLogin.isChecked()) {
                                editor.putString(SaveUsername, Username);
                                editor.putString(SavePassword, Password);
                                editor.commit();
                            }
                        });
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }).start();
    }
}