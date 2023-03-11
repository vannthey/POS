package com.example.pos.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.POSDatabase;
import com.example.pos.MainActivity;
import com.example.pos.Configure.SharedPrefHelper;
import com.example.pos.account.ManageAccountActivity;
import com.example.pos.databinding.ActivityLoginBinding;

import java.util.List;

public class Login extends AppCompatActivity {
    String Username;
    String Password;
    String UserRole;
    String ProfilePath;
    ActivityLoginBinding binding;
    List<UserAccount> userAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(this::btnLogin);
    }

    private void btnLogin(View view) {
        Username = String.valueOf(binding.txtUsername.getText());
        Password = String.valueOf(binding.txtPassword.getText());
        if (Username.equals("Admin") && Password.equals("Admin")) {
            SharedPrefHelper.getInstance().SaveDefaultUser(Username, Password, this);
            startActivity(new Intent(this, ManageAccountActivity.class));
        }
        new Thread(() -> {
            userAccounts =
                    POSDatabase.getInstance(getApplicationContext()).getDao().checkUser(Username,
                            Password);
            for (int i = 0; i < userAccounts.size(); i++) {
                if (Username.equals(userAccounts.get(i).getUsername()) && Password.equals(userAccounts.get(i).getPassword())) {
                    UserRole = userAccounts.get(i).getUserRole();
                    ProfilePath = userAccounts.get(i).getProfilePath();
                    SharedPrefHelper.getInstance().SaveUserLogin(Username, Password,
                            UserRole, ProfilePath, this);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            }
        }).start();
    }
}