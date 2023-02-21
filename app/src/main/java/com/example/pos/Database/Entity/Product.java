package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
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
    long productCode;

    @ColumnInfo
    double productCost;

    @ColumnInfo
    double productPrice;

    @ColumnInfo
    int inventoryId;

    @ColumnInfo
    int categoryId;

    @ColumnInfo
    String creator;

    @ColumnInfo
    String createDate;

    public Product(String productName, int productQty, long productCode, double productCost, double productPrice, int inventoryId, int categoryId, String creator, String createDate) {
        this.productName = productName;
        this.productQty = productQty;
        this.productCode = productCode;
        this.productCost = productCost;
        this.productPrice = productPrice;
        this.inventoryId = inventoryId;
        this.categoryId = categoryId;
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

    public long getProductCode() {
        return productCode;
    }

    public double getProductCost() {
        return productCost;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public int getCategoryId() {
        return categoryId;
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
                "itemId=" + productId +
                ", itemName='" + productName + '\'' +
                ", itemQty=" + productQty +
                ", itemCode=" + productCode +
                ", itemCost=" + productCost +
                ", itemPrice=" + productPrice +
                ", warehouseId=" + inventoryId +
                ", categoryId=" + categoryId +
                ", itemCreator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
