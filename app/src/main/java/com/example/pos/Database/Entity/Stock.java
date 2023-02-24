package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Stock {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo
    public int stockId;
    @ColumnInfo
    public String stockTitle;
    @ColumnInfo
    public String creator;
    @ColumnInfo
    public String createDate;

    public Stock(String stockTitle, String creator, String createDate) {
        this.stockTitle = stockTitle;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getStockId() {
        return stockId;
    }

    public String getStockTitle() {
        return stockTitle;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }
}
