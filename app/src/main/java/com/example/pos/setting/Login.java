package com.example.pos.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.POSDatabase;
import com.example.pos.MainActivity;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.databinding.ActivityLoginBinding;

import java.util.List;

public class Login extends AppCompatActivity {
    String Username;
    String Password;
    String UserRole;
    ActivityLoginBinding binding;
    List<UserAccount> userAccounts;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(this::btnLogin);
        handler = new Handler();

        Username = SharedPreferenceHelper.getInstance().getSaveUserLoginName(this);
        Password = SharedPreferenceHelper.getInstance().getSaveUserLoginPassword(this);
        new Thread(() -> {
            userAccounts =
                    POSDatabase.getInstance(getApplicationContext()).getDao().checkUser(Username,
                            Password);
            for (int i = 0; i < userAccounts.size(); i++) {
                if (Username.equals(userAccounts.get(i).getUsername()) && Password.equals(userAccounts.get(i).getPassword())) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Please LogIn", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();


    }

    private void btnLogin(View view) {
        Username = String.valueOf(binding.txtUsername.getText());
        Password = String.valueOf(binding.txtPassword.getText());
        if (Username.equals("Admin") && Password.equals("Admin")) {
            SharedPreferenceHelper.getInstance().SaveDefaultUser(Username,Password,this);
            startActivity(new Intent(this, MainActivity.class));
        }
        new Thread(() -> {
            userAccounts =
                    POSDatabase.getInstance(getApplicationContext()).getDao().checkUser(Username,
                            Password);
            for (int i = 0; i < userAccounts.size(); i++) {
                if (Username.equals(userAccounts.get(i).getUsername()) && Password.equals(userAccounts.get(i).getPassword())) {
                    UserRole = userAccounts.get(i).getUserRole();
                    SharedPreferenceHelper.getInstance().SaveUserLogin(Username, Password,
                            UserRole, this);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    handler.post(() -> {
                        Toast.makeText(this, "Invalidate Login", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        }).start();
    }
}