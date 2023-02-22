package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public
    int inventoryId;

    @ColumnInfo
    public
    String inventoryName;

    @ColumnInfo
    public
    String inventoryAddress;

    @ColumnInfo
    public
    int creator;
    @ColumnInfo
    public
    String createDate;

    public Inventory(String inventoryName, String inventoryAddress, int creator, String createDate) {
        this.inventoryName = inventoryName;
        this.inventoryAddress = inventoryAddress;
        this.creator = creator;
        this.createDate = createDate;
    }

    public Inventory() {
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public String getInventoryAddress() {
        return inventoryAddress;
    }

    public int getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }
}
