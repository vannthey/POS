package com.example.pos.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Customer;
import com.example.pos.Database.Entity.Expense;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.Entity.Unit;
import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.Relationship.CategoryWithProducts;
import com.example.pos.Database.Relationship.UnitWithProducts;

import java.util.List;

@Dao
public interface POSDao {

    /*
    Operation Category With Products
     */
    @Transaction
    @Query("SELECT Product.productId,Product.productName,Category.categoryId,Category.categoryName From Category Join" +
            " Product " +
            " Where " +
            "Category.categoryId=Product.productId ")
    LiveData<List<CategoryWithProducts>> getCategoryWithProducts();

    /*
    Operation Unit With Products
     */
    @Transaction
    @Query("select Unit.unitId,Unit.unitTitle,Product.productId,Product.productName from Unit Join Product Where Unit" +
            ".unitId = " +
            "Product" +
            ".productId")
    LiveData<List<UnitWithProducts>> getUnitWithProduct();

    /*
    Operation On Product
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProduct(Product product);

    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllProduct();

    @Query("DELETE FROM Product WHERE productId LIKE :productId")
    void deleteProductById(int productId);

    @Query("Update Product SET productName=:productName,productQty=:productQty,productUnitId=:productUnitId," +
            "productCode=:productCode,productCost=:productCost,productPrice=:productPrice,productTax=:productTax," +
            "inventoryId=:inventoryId,categoryId=:categoryId,supplierId=:supplierId," +
            "imagePath=:imagePath," + "creator=:creator,createDate=:createDate WHERE productId LIKE :productId")
    void updateProductById(String productName, int productQty, int productUnitId, long productCode,
                           double productCost, double productPrice, double productTax, int inventoryId,
                           int categoryId, int supplierId, String imagePath, String creator, String createDate,
                           int productId);

    @Query("Update Product Set productQty=:productQty Where productId like :productId")
    void updateProductQtyAfterSale(int productQty, int productId);

    @Query("select productQty from Product where productId like :productId")
    int getProductQtyById(int productId);

    /*
    Operation On Category
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createCategory(Category category);

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAllCategory();

    @Query("Update Category SET categoryName=:categoryName WHERE categoryId LIKE :categoryId")
    void updateCategoryById(String categoryName, int categoryId);

    @Query("DELETE FROM Category WHERE categoryId LIKE :categoryId")
    void deleteCategoryById(int categoryId);

    @Query("select categoryId from Category where categoryName like :categoryName")
    int getCategoryId(String categoryName);

    /*
    Operation On Sale Transaction
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createSaleTransaction(SaleTransaction transactionList);

    @Query("SELECT * FROM SaleTransaction")
    LiveData<List<SaleTransaction>> getAllSaleTransaction();

    @Query("Update SaleTransaction SET productPrice=:price, productQty=:qty,productDiscount=:dist,productAmount=:productAmount " +
            "WHERE " +
            "productId " + "LIKE " + ":id")
    void editProductOnSaleById(double price, int qty, double dist, double productAmount, int id);

    @Query("DELETE FROM SaleTransaction WHERE saleId LIKE :saleId")
    void deleteSaleTransactionById(int saleId);

    @Query("DELETE FROM SaleTransaction")
    void deleteAfterPay();


    /*
    Operation On Supplier
     */
    @Query("Update Supplier SET supplierName=:supplierName,supplierAddress=:supplierAddress," + "supplierSex=:supplierSex,supplierPhoneNumber=:supplierPhoneNumber WHERE supplierId " + "Like :Id")
    void updateSupplierById(String supplierName, String supplierAddress, String supplierSex, String supplierPhoneNumber, int Id);

    @Insert
    void createSupplier(Supplier supplier);

    @Query("SELECT * FROM Supplier")
    LiveData<List<Supplier>> getAllSupplier();


    @Query("DELETE FROM Supplier WHERE supplierId Like :Id")
    void deleteSupplierById(int Id);

    /*
    Operation On User
     */
    @Query("Update UserAccount SET Firstname=:FirstName,Lastname=:LastName,Username=:UserName," + "Password=:Password,DOB=:DOB,Address=:Address,Sex=:Sex,UserRole=:Role," + "ProfilePath=:profilePath," + "canDiscount=:canDiscount,canChangePrice=:canChangePrice WHERE userId LIKE :Id")
    void updateUserById(String FirstName, String LastName, String UserName, String Password, String DOB, String Address, String Sex, String Role, String profilePath, boolean canDiscount, boolean canChangePrice, int Id);

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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createInventory(Inventory inventory);

    @Query("Delete From Inventory Where inventoryId like :inventoryId")
    void deleteInventoryById(int inventoryId);

    @Query("Update Inventory set inventoryAddress=:inventoryAddress, inventoryName=:inventoryName Where inventoryId " + "Like :inventoryId")
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

    @Query("update Customer set customerName=:customerName, customerSex=:customerSex," + "customerPhoneNumber=:customerPhoneNumber,customerAddress=:customerAddress,customerProfile=:customerProfile," + "customerDiscount=:customerDiscount where " + "customerId like " + ":customerId")
    void updateCustomerById(String customerName, String customerSex, String customerPhoneNumber, double customerDiscount, String customerAddress, String customerProfile, int customerId);

    @Query("select * from Customer")
    LiveData<List<Customer>> getAllCustomer();

    /*
    Operation On Expense
     */
    @Insert
    void createExpense(Expense expense);

    @Query("select * from Expense")
    LiveData<List<Expense>> getAllExpense();

    @Query("Delete from Expense Where id like :id")
    void deleteExpenseById(int id);

    @Query("Update Expense set expenseName = expenseName, " +
            "expensePrice=expensePrice, " +
            "expensePaid=expensePrice," +
            "expanseDue=expanseDue," +
            "expenseDescription=expenseDescription where id like :id ")
    void updateExpenseById(String expenseName,double expensePrice,double expensePaid,double expanseDue,String expenseDescription,int id);

}
