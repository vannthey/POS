package com.example.pos.Database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.Entity.UserAccount;

import java.util.List;

@Dao
public interface POSDao {

    @Insert
    void createUser(UserAccount userAccount);

    @Insert
    void createInventory(Inventory warehouse);
    @Insert
    void createCategory(Category category);

    @Query("SELECT * FROM UserAccount")
    List<UserAccount> userAccount();

    @Query("SELECT * FROM Inventory")
    List<Inventory> getAllInventory();

    @Query("SELECT * FROM UserAccount Where Username =:username AND Password =:password")
    List<UserAccount> checkUser(String username,String password);

    @Query("SELECT * FROM Category")
    List<Category> getAllCategory();

    @Query("SELECT * FROM Product")
    List<Product> getAllProduct();
}
