package com.example.pos.Database.Relationship;

import androidx.room.ColumnInfo;

public class UnitWithProducts {
    @ColumnInfo
    public int unitId;
    @ColumnInfo
    public String unitTitle;

    @ColumnInfo
    public
    int productId;

    @ColumnInfo
    String productName;

    public UnitWithProducts(int unitId, String unitTitle, int productId, String productName) {
        this.unitId = unitId;
        this.unitTitle = unitTitle;
        this.productId = productId;
        this.productName = productName;
    }

    public int getUnitId() {
        return unitId;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "UnitWithProducts{" +
                "unitId=" + unitId +
                ", unitTitle='" + unitTitle + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                '}';
    }
}
