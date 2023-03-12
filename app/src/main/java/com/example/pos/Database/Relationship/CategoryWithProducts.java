package com.example.pos.Database.Relationship;

import androidx.room.ColumnInfo;

public class CategoryWithProducts {
    @ColumnInfo
    public
    int productId;

    @ColumnInfo
    String productName;

    @ColumnInfo
    int categoryId;

    @ColumnInfo
    public String categoryName;

    public CategoryWithProducts(int productId, String productName, int categoryId, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return "CategoryWithProducts{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", customerName='" + categoryName + '\'' +
                '}';
    }
}
