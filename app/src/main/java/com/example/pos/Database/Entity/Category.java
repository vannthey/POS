package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public
    int categoryId;

    @ColumnInfo
    public
    String categoryName;

    @ColumnInfo
    public
    int supplierId;

    @ColumnInfo
    public
    String creator;

    @ColumnInfo
    public
    String createDate;

    public Category(String categoryName, int supplierId, String creator, String createDate) {
        this.categoryName = categoryName;
        this.supplierId = supplierId;
        this.creator = creator;
        this.createDate = createDate;
    }

    public Category() {
    }


    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", supplierId=" + supplierId +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
