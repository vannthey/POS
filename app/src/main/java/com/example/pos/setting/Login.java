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
    private final String SaveUserRole = "SaveUserRole";
    private final String SavePassword = "Password";
    private final String DefaultUsername = "Admin";
    private final String DefaultUser = "DefaultUser";
    private final String DefaultPassword = "Admin";


    private final String SaveUserFullName = "SaveUserFullName";

    String Username;
    String Password;
    ActivityLoginBinding binding;
    List<UserAccount> userAccounts;
    SharedPreferences sharedPreferences, sharedDefaultUser;
    SharedPreferences.Editor editor,editorDefault;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(this::btnLogin);
        sharedPreferences = getSharedPreferences(SaveUserLogin, MODE_PRIVATE);
        Username = sharedPreferences.getString(SaveUsername, "");
        Password = sharedPreferences.getString(SavePassword, "");
        // Toast.makeText(this, "" + Username + Password, Toast.LENGTH_SHORT).show();
        handler = new Handler();
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
        sharedPreferences = getSharedPreferences(SaveUserLogin, MODE_PRIVATE);
        sharedDefaultUser = getSharedPreferences(DefaultUser, MODE_PRIVATE);
        editorDefault = sharedDefaultUser.edit();
        editor = sharedPreferences.edit();
        Username = String.valueOf(binding.txtUsername.getText());
        Password = String.valueOf(binding.txtPassword.getText());
        if (Username.equals(DefaultUsername) && Password.equals(DefaultPassword)) {
            editorDefault.putString(DefaultUsername, "Admin");
            editorDefault.commit();
            startActivity(new Intent(this, MainActivity.class));
        }
        new Thread(() -> {
            userAccounts =
                    POSDatabase.getInstance(getApplicationContext()).getDao().checkUser(Username,
                            Password);
            for (int i = 0; i < userAccounts.size(); i++) {
                if (Username.equals(userAccounts.get(i).getUsername()) && Password.equals(userAccounts.get(i).getPassword())) {
                    editor.putString(SaveUserFullName,
                            userAccounts.get(i).getUsername() + " " + userAccounts.get(i).getLastname());
                    editor.putString(SaveUsername, Username);
                    editor.putString(SavePassword, Password);
                    editor.putString(SaveUserRole, userAccounts.get(i).getUserRole());
                    editor.commit();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            }
        }).start();
    }
}