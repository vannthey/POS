package com.example.pos.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Customer;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.Entity.Unit;
import com.example.pos.Database.Entity.UserAccount;

import java.util.List;

@Dao
public interface POSDao {

    /*
    Operation On Product
     */
    @Insert
    void createProduct(Product product);

    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllProduct();

    @Query("DELETE FROM Product WHERE productId LIKE :productId")
    void deleteProductById(int productId);

    @Query("Update Product SET productName=:productName,productQty=:productQty,productUnitId=:productUnitId," +
            "productCode=:productCode,productCost=:productCost,productPrice=:productPrice,productTax=:productTax," +
            "inventoryId=:inventoryId,categoryId=:categoryId,supplierId=:supplierId,imagePath=:imagePath," +
            "creator=:creator,createDate=:createDate WHERE productId LIKE :productId")
    void updateProductById(String productName, int productQty, int productUnitId, long productCode, double productCost,
                           double productPrice, double productTax, int inventoryId, int categoryId, int supplierId,
                           String imagePath, String creator, String createDate, int productId);

    /*
    Operation On Category
     */
    @Insert
    void createCategory(Category category);

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAllCategory();

    @Query("Update Category SET categoryName=:categoryName WHERE categoryId LIKE :categoryId")
    void updateCategoryById(String categoryName, int categoryId);

    @Query("DELETE FROM Category WHERE categoryId LIKE :categoryId")
    void deleteCategoryById(int categoryId);

    /*
    Operation On Sale Transaction
     */

    @Insert
    void createSaleTransaction(SaleTransaction transactionList);

    @Query("SELECT * FROM SaleTransaction")
    LiveData<List<SaleTransaction>> getAllSaleTransaction();

    @Query("Update SaleTransaction SET productPrice=:price, productQty=:qty,productDiscount=:dist " +
            "WHERE productId " +
            "LIKE " +
            ":id")
    void editProductOnSaleById(double price, int qty, double dist, int id);

    @Query("DELETE FROM SaleTransaction WHERE saleId LIKE :saleId")
    void deleteSaleTransactionById(int saleId);

    @Query("DELETE FROM SaleTransaction")
    void deleteAfterPay();

    /*
    Operation On Supplier
     */
    @Query("Update Supplier SET supplierName=:supplierName,supplierAddress=:supplierAddress," +
            "supplierSex=:supplierSex,supplierPhoneNumber=:supplierPhoneNumber WHERE supplierId " +
            "Like :Id")
    void updateSupplierById(String supplierName, String supplierAddress, String supplierSex,
                            String supplierPhoneNumber, int Id);

    @Insert
    void createSupplier(Supplier supplier);

    @Query("SELECT * FROM Supplier")
    LiveData<List<Supplier>> getAllSupplier();


    @Query("DELETE FROM Supplier WHERE supplierId Like :Id")
    void deleteSupplierById(int Id);

    /*
    Operation On User
     */
    @Query("Update UserAccount SET Firstname=:FirstName,Lastname=:LastName,Username=:UserName," +
            "Password=:Password,DOB=:DOB,Address=:Address,Sex=:Sex,UserRole=:Role," +
            "ProfilePath=:profilePath," +
            "canDiscount=:canDiscount,canChangePrice=:canChangePrice WHERE userId LIKE :Id")
    void updateUserById(String FirstName, String LastName, String UserName, String Password,
                        String DOB, String Address, String Sex, String Role, String profilePath,
                        boolean canDiscount, boolean canChangePrice, int Id);

    @Query("DELETE FROM UserAccount WHERE userId LIKE :userId")
    void deleteUserById(int userId);

    @Insert
    void createUser(UserAccount userAccount);

    @Query("SELECT * FROM UserAccount")
    LiveData<List<UserAccount>> userAccount();

    @Query("SELECT * FROM UserAccount Where Username =:username AND Password =:password")
    List<UserAccount> checkUser(String username, String password);

    /*
    Operation On Unit
     */
    @Insert
    void createUnit(Unit unit);

    @Query("SELECT * FROM Unit")
    LiveData<List<Unit>> getAllUnit();

    @Query("Update Unit SET unitTitle=:unitTitle WHERE unitId LIKE :unitId")
    void updateUnitById(String unitTitle, int unitId);

    @Query("DELETE FROM Unit WHERE unitId Like :unitId")
    void deleteUnitById(int unitId);

    /*
    Operation On Inventory
     */
    @Insert
    void createInventory(Inventory inventory);

    @Query("Delete From Inventory Where inventoryId like :inventoryId")
    void deleteInventoryById(int inventoryId);

    @Query("Update Inventory set inventoryAddress=:inventoryAddress, inventoryName=:inventoryName Where inventoryId " +
            "Like :inventoryId")
    void updateInventoryById(String inventoryAddress, String inventoryName, int inventoryId);

    @Query("SELECT * FROM Inventory")
    LiveData<List<Inventory>> getAllInventory();

    /*
    Operation On Customer
     */
    @Insert
    void createCustomer(Customer customer);

    @Query("delete from Customer Where customerId like :customerId")
    void deleteCustomerById(int customerId);

    @Query("update Customer set customerName=:customerName, customerSex=:customerSex," +
            "customerPhoneNumber=:customerPhoneNumber,customerAddress=:customerAddress," +
            "customerDiscount=:customerDiscount where " +
            "customerId like " +
            ":customerId")
    void updateCustomerById(String customerName, String customerSex, String customerPhoneNumber, double customerDiscount,
                            String customerAddress,
                            int customerId);

    @Query("select * from Customer")
    LiveData<List<Customer>> getAllCustomer();
}
