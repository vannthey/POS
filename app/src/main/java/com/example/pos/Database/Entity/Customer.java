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
    public String customerAddress;

    @ColumnInfo
    public String customerProfile;
    @ColumnInfo
    public Double customerDiscount;

    @ColumnInfo
    public String creator;

    @ColumnInfo
    public String createDate;

    public Customer(String customerName, String customerSex, String customerPhoneNumber, String customerAddress, String customerProfile, Double customerDiscount, String creator, String createDate) {
        this.customerName = customerName;
        this.customerSex = customerSex;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.customerProfile = customerProfile;
        this.customerDiscount = customerDiscount;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerProfile() {
        return customerProfile;
    }

    public Double getCustomerDiscount() {
        return customerDiscount;
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
                ", customerAddress='" + customerAddress + '\'' +
                ", customerProfile='" + customerProfile + '\'' +
                ", customerDiscount=" + customerDiscount +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
