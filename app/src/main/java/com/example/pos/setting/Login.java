package com.example.pos.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.POSDatabase;
import com.example.pos.HelperClass.SharedPrefHelper;
import com.example.pos.MainActivity;
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

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handler = new Handler();
        binding.btnLogin.setOnClickListener(this::btnLogin);
    }

    private void btnLogin(View view) {
        Username = String.valueOf(binding.txtUsername.getText());
        Password = String.valueOf(binding.txtPassword.getText());
        if (Username.equals("Admin") && Password.equals("Admin")) {
            SharedPrefHelper.getInstance().SaveUserLogin(Username, Password, null, null, this);
            startActivity(new Intent(this, ManageAccountActivity.class));
        }
        new Thread(() -> {
            userAccounts =
                    POSDatabase.getInstance(getApplicationContext()).getDao().checkUser(Username,
                            Password);
            if (userAccounts != null) {
                for (UserAccount u : userAccounts) {
                    if (Username.contains(u.getUsername()) && Password.contains(u.getPassword())) {
                        UserRole = u.getUserRole();
                        ProfilePath = u.getProfilePath();
                        SharedPrefHelper.getInstance().SaveUserLogin(Username, Password,
                                UserRole, ProfilePath, this);
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        handler.post(() -> Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show());
                    }
                }
            }
        }).start();

    }
}