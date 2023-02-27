package com.example.pos.sale;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SaleTransaction {
    @PrimaryKey
    @ColumnInfo
    int saleId;
    @ColumnInfo
    int productId;
    @ColumnInfo
    String productName;
    @ColumnInfo
    int productQty;
    @ColumnInfo
    int productUnitId;
    @ColumnInfo
    int customerId;
    @ColumnInfo
    double productPrice;
    @ColumnInfo
    double saleDiscount;
    @ColumnInfo
    double saleSubtotal;
    @ColumnInfo
    String userName;
}
