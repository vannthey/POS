package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    int categoryId;

    @ColumnInfo
    String categoryName;

    @ColumnInfo
    String creator;

    @ColumnInfo
    String createDate;

    public Category(String categoryName, String creator, String createDate) {
        this.categoryName = categoryName;
        this.creator = creator;
        this.createDate = createDate;
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
        return createDate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
