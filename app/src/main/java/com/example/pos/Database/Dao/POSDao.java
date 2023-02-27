package com.example.pos.Database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.Entity.Unit;
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

    @Insert
    void createSupplier(Supplier supplier);

    @Insert
    void createProduct(Product product);

    @Insert
    void createUnit(Unit unit);

    @Query("SELECT * FROM UserAccount")
    List<UserAccount> userAccount();

    @Query("SELECT * FROM Inventory")
    List<Inventory> getAllInventory();

    @Query("SELECT * FROM UserAccount Where Username =:username AND Password =:password")
    List<UserAccount> checkUser(String username, String password);

    @Query("SELECT * FROM Category")
    List<Category> getAllCategory();

//    @Transaction
//    @Query("SELECT Category.categoryId,Supplier.supplierId,Category.categoryName,Supplier.supplierName FROM Category" +
//            " JOIN " +
//            "Supplier ON " +
//            "Category" +
//            ".categoryId = Supplier.supplierId")
//    List<CategoryWithSupplier> getAllCategoryFtSupplier();

    @Query("SELECT * FROM Product")
    List<Product> getAllProduct();

    @Query("SELECT * FROM Supplier")
    List<Supplier> getAllSupplier();


    @Query("DELETE FROM Supplier WHERE supplierId Like :Id")
    void deleteSupplierById(int Id);

    @Query("Update Supplier SET supplierName=:supplierName,supplierAddress=:supplierAddress," +
            "supplierSex=:supplierSex,supplierPhoneNumber=:supplierPhoneNumber WHERE supplierId " +
            "Like :Id")
    void updateSupplierById(String supplierName, String supplierAddress, String supplierSex,
                            String supplierPhoneNumber, int Id);

    @Query("SELECT * FROM Unit")
    List<Unit> getAllUnit();


    @Transaction
    @Query("Update UserAccount SET Firstname=:FirstName,Lastname=:LastName,Username=:UserName," +
            "Password=:Password,DOB=:DOB,Address=:Address,Sex=:Sex,UserRole=:Role," +
            "canDiscount=:canDiscount," +
            "canUpdate=:canUpdate,canAddItem=:canAddItem,canAddCategory=:canAddCategory," +
            "canDeleteItem=:canDeleteItem WHERE userId LIKE :Id")
    void updateUserById(String FirstName, String LastName, String UserName, String Password,
                        String DOB, String Address, String Sex, String Role, boolean canDiscount,
                        boolean canUpdate,
                        boolean canAddItem, boolean canAddCategory, boolean canDeleteItem, int Id);

    @Query("DELETE FROM UserAccount WHERE userId LIKE :userId")
    void deleteUserById(int userId);
}
