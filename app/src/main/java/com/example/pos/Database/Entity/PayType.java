package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PayType {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int payId;

    @ColumnInfo
    public String payType;

    @ColumnInfo
    public String creator;

    @ColumnInfo
    public String createDate;

    public PayType(String payType, String creator, String createDate) {
        this.payType = payType;
        this.creator = creator;
        this.createDate = createDate;
    }

    public PayType() {
    }

    public int getPayId() {
        return payId;
    }

    public String getPayType() {
        return payType;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "PayType{" +
                "payId=" + payId +
                ", payType='" + payType + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
