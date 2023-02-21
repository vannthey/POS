package com.example.pos.Database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.Entity.Warehouse;

import java.util.List;

@Dao
public interface POSDao {

    @Insert
    void createUser(UserAccount userAccount);

    @Insert
    void createInventory(Warehouse warehouse);
    @Insert
    void createCategory(Category category);

    @Query("SELECT * FROM UserAccount")
    List<UserAccount> userAccount();

    @Query("SELECT * FROM Warehouse")
    List<Warehouse> getAllInventory();

    @Query("SELECT * FROM UserAccount Where Username =:username AND Password =:password")
    List<UserAccount> checkUser(String username,String password);

    @Query("SELECT * FROM Category")
    List<Category> getAllCategory();
}
