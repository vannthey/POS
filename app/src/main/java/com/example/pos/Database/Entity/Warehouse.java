package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Warehouse {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    int warehouseId;

    @ColumnInfo
    String warehouseName;

    @ColumnInfo
    String createDate;

    public Warehouse(String warehouseName, String createDate) {
        this.warehouseName = warehouseName;
        this.createDate = createDate;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}