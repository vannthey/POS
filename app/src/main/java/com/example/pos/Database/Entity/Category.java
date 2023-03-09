package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"categoryName"}, unique = true))
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
    String creator;

    @ColumnInfo
    public
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
