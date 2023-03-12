package com.example.pos.account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.UserAccount;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public void updateUserById(String FirstName, String LastName, String UserName, String Password,
                               String DOB, String Address, String Sex, String Role, String profilePath,
                               boolean canDiscount, boolean canChangePrice, int Id) {
        new Thread(() -> appDatabase.getDao().updateUserById(FirstName, LastName, UserName, Password, DOB, Address, Sex, Role, profilePath,
                canDiscount, canChangePrice, Id)).start();

    }

    public void deleteUserById(int userId) {
        new Thread(() -> appDatabase.getDao().deleteUserById(userId)).start();

    }

    public void createUser(UserAccount userAccount) {
        new Thread(() -> appDatabase.getDao().createUser(userAccount)).start();
    }

    public LiveData<List<UserAccount>> userAccount() {
        return appDatabase.getDao().userAccount();
    }
}
