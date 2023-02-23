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
    String creator;
    @ColumnInfo
    public
    String createDate;

    public Inventory(String inventoryName, String inventoryAddress, String creator, String createDate) {
        this.inventoryName = inventoryName;
        this.inventoryAddress = inventoryAddress;
        this.creator = creator;
        this.createDate = createDate;
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

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", inventoryName='" + inventoryName + '\'' +
                ", inventoryAddress='" + inventoryAddress + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
