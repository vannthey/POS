package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Item {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    int itemId;

    @ColumnInfo
    String itemName;

    @ColumnInfo
    int itemQty;

    @ColumnInfo
    long itemCode;

    @ColumnInfo
    double itemCost;

    @ColumnInfo
    double itemPrice;

    @ColumnInfo
    int warehouseId;

    @ColumnInfo
    int categoryId;

    @ColumnInfo
    String itemCreator;

    @ColumnInfo
    String createDate;

    public Item(String itemName, int itemQty, long itemCode, double itemCost, double itemPrice, int warehouseId, int categoryId, String itemCreator, String createDate) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemCode = itemCode;
        this.itemCost = itemCost;
        this.itemPrice = itemPrice;
        this.warehouseId = warehouseId;
        this.categoryId = categoryId;
        this.itemCreator = itemCreator;
        this.createDate = createDate;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    public long getItemCode() {
        return itemCode;
    }

    public double getItemCost() {
        return itemCost;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getItemCreator() {
        return itemCreator;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemQty=" + itemQty +
                ", itemCode=" + itemCode +
                ", itemCost=" + itemCost +
                ", itemPrice=" + itemPrice +
                ", warehouseId=" + warehouseId +
                ", categoryId=" + categoryId +
                ", itemCreator='" + itemCreator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
