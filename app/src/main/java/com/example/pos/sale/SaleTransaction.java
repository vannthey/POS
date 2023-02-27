package com.example.pos.sale;

import androidx.room.Entity;

@Entity
public class SaleTransaction {
    int saleId;
    String saleName;
    int saleQty;
    double salePrice;
    double saleDiscount;
    double saleSubtotal;
}
