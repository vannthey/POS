package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"productName"}, unique = true))
public class SaleTransaction {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int saleId;
    @ColumnInfo
    public int productId;
    @ColumnInfo
    public String productName;

    @ColumnInfo
    public String productImagePath;

    @ColumnInfo
    public int productQty;
    @ColumnInfo
    public int productUnitId;
    @ColumnInfo
    public double productPrice;

    @ColumnInfo
    public double productDiscount;

    @ColumnInfo
    public double productAmount;

    public SaleTransaction(int productId, String productName, String productImagePath, int productQty, int productUnitId, double productPrice, double productDiscount, double productAmount) {
        this.productId = productId;
        this.productName = productName;
        this.productImagePath = productImagePath;
        this.productQty = productQty;
        this.productUnitId = productUnitId;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productAmount = productAmount;
    }

    @Override
    public String toString() {
        return "SaleTransaction{" +
                "saleId=" + saleId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productImagePath='" + productImagePath + '\'' +
                ", productQty=" + productQty +
                ", productUnitId=" + productUnitId +
                ", productPrice=" + productPrice +
                ", productDiscount=" + productDiscount +
                ", productAmount=" + productAmount +
                '}';
    }

    public int getSaleId() {
        return saleId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public int getProductQty() {
        return productQty;
    }

    public int getProductUnitId() {
        return productUnitId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public double getProductDiscount() {
        return productDiscount;
    }

    public double getProductAmount() {
        return productAmount;
    }
}
