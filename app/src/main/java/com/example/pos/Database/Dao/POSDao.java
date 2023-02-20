package com.example.pos.Database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pos.Database.Entity.UserAccount;

import java.util.List;

@Dao
public interface POSDao {

    @Insert
    void createUser(UserAccount userAccount);

    @Query("SELECT * FROM UserAccount")
    List<UserAccount> userAccount();

}
