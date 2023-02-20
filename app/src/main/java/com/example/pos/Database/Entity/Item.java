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
    int categoryId;

    @ColumnInfo
    String itemCreator;

    @ColumnInfo
    String createDate;

    public Item(String itemName, int categoryId, String itemCreator, String createDate) {
        this.itemName = itemName;
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
                ", categoryId=" + categoryId +
                ", itemCreator='" + itemCreator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
