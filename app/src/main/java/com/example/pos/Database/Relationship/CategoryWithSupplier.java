package com.example.pos.Database.Relationship;

import androidx.room.ColumnInfo;

public class CategoryWithSupplier {
    @ColumnInfo
    int categoryId;
    @ColumnInfo
    int supplierId;

    public int getCategoryId() {
        return categoryId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    @ColumnInfo
    public String categoryName;
    @ColumnInfo
    public String supplierName;

    public CategoryWithSupplier(String categoryName, String supplierName) {
        this.categoryName = categoryName;
        this.supplierName = supplierName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    @Override
    public String toString() {
        return "CategoryWithSupplier{" +
                "categoryId=" + categoryId +
                ", supplierId=" + supplierId +
                ", categoryName='" + categoryName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                '}';
    }
}
