package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Unit {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int unitId;
    @ColumnInfo
    public String unitTitle;
    public int unitQty;
    @ColumnInfo
    public String creator;
    @ColumnInfo
    public String createDate;

    public Unit(String unitTitle, int unitQty, String creator, String createDate) {
        this.unitTitle = unitTitle;
        this.unitQty = unitQty;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getUnitId() {
        return unitId;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public int getUnitQty() {
        return unitQty;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }
}
