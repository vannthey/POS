package com.example.pos.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.POSDatabase;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    POSDatabase posDatabase;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        posDatabase = POSDatabase.getInstance(application.getApplicationContext());
    }

    public void createProduct(Product product) {
        new Thread(() -> posDatabase.getDao().createProduct(product)).start();
    }

    public LiveData<List<Product>> getAllProduct() {
        return posDatabase.getDao().getAllProduct();
    }

    public void deleteProductById(int productId) {
        new Thread(() -> posDatabase.getDao().deleteProductById(productId)).start();
    }

    void updateProductById(String productName, int productQty, int productUnitId, long productCode,
                           double productCost, double productPrice, double productTax, int inventoryId,
                           int categoryId, int supplierId, String imagePath, String creator, String createDate,
                           int productId) {
        new Thread(() -> posDatabase.getDao().updateProductById(productName, productQty, productUnitId, productCode, productCost,
                productPrice, productTax, inventoryId, categoryId, supplierId, imagePath, creator, createDate,
                productId)).start();
    }

}
