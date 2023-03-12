package com.example.pos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.HelperClass.SharedPrefHelper;
import com.example.pos.account.ManageAccountActivity;
import com.example.pos.databinding.ActivitySpashScreenBinding;
import com.example.pos.setting.Login;

import java.util.List;

public class StartUpScreen extends AppCompatActivity {
    ActivitySpashScreenBinding binding;
    String Username;
    String Password;
    List<UserAccount> userAccounts;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handler = new Handler();
        Username = SharedPrefHelper.getInstance().getSaveUserLoginName(this);
        Password = SharedPrefHelper.getInstance().getSaveUserLoginPassword(this);
        handler.postDelayed(() -> {
            //default user Admin Admin
            if (Username.equals("Admin") && Password.equals("Admin")) {
                handler.post(() -> {
                    startActivity(new Intent(this, ManageAccountActivity.class));
                    this.finish();
                });
                //compare user from database
            }
            if (!Username.isEmpty() || !Password.isEmpty()) {
                new Thread(() -> {
                    userAccounts =
                            AppDatabase.getInstance(getApplicationContext()).getDao().checkUser(Username,
                                    Password);
                    for (UserAccount u : userAccounts) {
                        if (u.getUsername().contains(Username) && u.getPassword().contains(Password)) {
                            handler.post(() -> {
                                startActivity(new Intent(this, MainActivity.class));
                                this.finish();
                            });
                        } else {
                            //user invalidated
                            handler.post(() -> {
                                startActivity(new Intent(this, Login.class));
                                this.finish();
                            });
                        }
                    }//for each
                }).start();
            } else {
                startActivity(new Intent(this, Login.class));
                this.finish();
            }
        }, 1000);
    }
}