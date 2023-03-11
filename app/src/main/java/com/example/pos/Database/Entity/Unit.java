package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices = @Index(value = {"unitTitle"}, unique = true))
public class Unit {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int unitId;
    @ColumnInfo
    public String unitTitle;
    @ColumnInfo
    public String creator;
    @ColumnInfo
    public String createDate;

    public Unit(String unitTitle, String creator, String createDate) {
        this.unitTitle = unitTitle;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getUnitId() {
        return unitId;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "unitId=" + unitId +
                ", unitTitle='" + unitTitle + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
