package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"productName"}, unique = true))
public class Product {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public
    int productId;

    @ColumnInfo
    String productName;

    @ColumnInfo
    int productQty;

    @ColumnInfo
    int productUnitId;

    @ColumnInfo
    long productCode;

    @ColumnInfo
    double productCost;

    @ColumnInfo
    double productPrice;

    @ColumnInfo
    double productTax;

    @ColumnInfo
    int inventoryId;

    @ColumnInfo
    int categoryId;

    @ColumnInfo
    int supplierId;

    @ColumnInfo
    String imagePath;

    @ColumnInfo
    String creator;

    @ColumnInfo
    String createDate;

    public Product(String productName, int productQty, int productUnitId, long productCode, double productCost, double productPrice, double productTax, int inventoryId, int categoryId, int supplierId, String imagePath, String creator, String createDate) {
        this.productName = productName;
        this.productQty = productQty;
        this.productUnitId = productUnitId;
        this.productCode = productCode;
        this.productCost = productCost;
        this.productPrice = productPrice;
        this.productTax = productTax;
        this.inventoryId = inventoryId;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.imagePath = imagePath;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQty() {
        return productQty;
    }

    public int getProductUnitId() {
        return productUnitId;
    }

    public long getProductCode() {
        return productCode;
    }

    public double getProductCost() {
        return productCost;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public double getProductTax() {
        return productTax;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productQty=" + productQty +
                ", productUnitId=" + productUnitId +
                ", productCode=" + productCode +
                ", productCost=" + productCost +
                ", productPrice=" + productPrice +
                ", productTax=" + productTax +
                ", inventoryId=" + inventoryId +
                ", categoryId=" + categoryId +
                ", supplierId=" + supplierId +
                ", imagePath='" + imagePath + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
