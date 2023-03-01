package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Supplier {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int supplierId;
    @ColumnInfo
    public String supplierName;
    @ColumnInfo
    public String supplierSex;
    @ColumnInfo
    public String supplierPhoneNumber;
    @ColumnInfo
    public String supplierAddress;
    @ColumnInfo
    public String creator;
    @ColumnInfo
    public String createDate;

    public Supplier(String supplierName, String supplierSex, String supplierPhoneNumber, String supplierAddress, String creator, String createDate) {
        this.supplierName = supplierName;
        this.supplierSex = supplierSex;
        this.supplierPhoneNumber = supplierPhoneNumber;
        this.supplierAddress = supplierAddress;
        this.creator = creator;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", supplierSex='" + supplierSex + '\'' +
                ", supplierPhoneNumber='" + supplierPhoneNumber + '\'' +
                ", supplierAddress='" + supplierAddress + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierSex() {
        return supplierSex;
    }

    public String getSupplierPhoneNumber() {
        return supplierPhoneNumber;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }
}
