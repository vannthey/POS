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
    String inventoryName;

    @ColumnInfo
    String createDate;

    public Inventory(String inventoryName, String createDate) {
        this.inventoryName = inventoryName;
        this.createDate = createDate;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "warehouseId=" + inventoryId +
                ", warehouseName='" + inventoryName + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
