package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    int categoryId;

    @ColumnInfo
    String categoryName;

    @ColumnInfo
    String creator;

    @ColumnInfo
    String CreateDate;

    public Category(String categoryName, String creator, String createDate) {
        this.categoryName = categoryName;
        this.creator = creator;
        CreateDate = createDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", creator='" + creator + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                '}';
    }
}
