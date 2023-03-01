package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int customerId;

    @ColumnInfo
    public String customerName;

    @ColumnInfo
    public String customerSex;

    @ColumnInfo
    public String customerPhoneNumber;

    @ColumnInfo
    public String customerNote;

    @ColumnInfo
    public String creator;

    @ColumnInfo
    public String createDate;

    public Customer(String customerName, String customerSex, String customerPhoneNumber, String customerNote, String creator, String createDate) {
        this.customerName = customerName;
        this.customerSex = customerSex;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerNote = customerNote;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerSex() {
        return customerSex;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerSex='" + customerSex + '\'' +
                ", customerPhoneNumber='" + customerPhoneNumber + '\'' +
                ", customerNote='" + customerNote + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
