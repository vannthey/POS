package com.example.pos.Database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.pos.Database.Entity.UserAccount;

@Dao
public interface POSDao {

    @Insert
    void createUser(UserAccount userAccount);

}
