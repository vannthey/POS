package com.example.pos.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.Product;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public void createProduct(Product product) {
        new Thread(() -> appDatabase.getDao().createProduct(product)).start();
    }

    public LiveData<List<Product>> getAllProduct() {
        return appDatabase.getDao().getAllProduct();
    }

    public void deleteProductById(int productId) {
        new Thread(() -> appDatabase.getDao().deleteProductById(productId)).start();
    }

    void updateProductById(String productName, int productQty, int productUnitId, long productCode,
                           double productCost, double productPrice, double productTax, int inventoryId,
                           int categoryId, int supplierId, String imagePath, String creator, String createDate,
                           int productId) {
        new Thread(() -> appDatabase.getDao().updateProductById(productName, productQty, productUnitId, productCode, productCost,
                productPrice, productTax, inventoryId, categoryId, supplierId, imagePath, creator, createDate,
                productId)).start();
    }

}
