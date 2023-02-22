package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserAccount {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public
    int userId;

    @ColumnInfo
    public
    String Firstname;

    @ColumnInfo
    public
    String Lastname;
    @ColumnInfo
    public String Sex;
    @ColumnInfo
    public String DOB;
    @ColumnInfo
    public String Address;

    @ColumnInfo
    public
    String Username;

    @ColumnInfo
    public
    String Password;


    @ColumnInfo
    public
    String UserRole;

    @ColumnInfo
    public
    Boolean canDiscount;

    @ColumnInfo
    public
    Boolean canUpdate;

    @ColumnInfo
    public
    Boolean canAddItem;

    @ColumnInfo
    public
    Boolean canAddCategory;

    @ColumnInfo
    public
    Boolean canDeleteItem;

    @ColumnInfo
    public
    String createDate;

    public UserAccount() {
    }

    public UserAccount(String firstname, String lastname, String sex, String DOB, String address, String username, String password, String userRole, Boolean canDiscount, Boolean canUpdate, Boolean canAddItem, Boolean canAddCategory, Boolean canDeleteItem, String createDate) {
        Firstname = firstname;
        Lastname = lastname;
        Sex = sex;
        this.DOB = DOB;
        Address = address;
        Username = username;
        Password = password;
        UserRole = userRole;
        this.canDiscount = canDiscount;
        this.canUpdate = canUpdate;
        this.canAddItem = canAddItem;
        this.canAddCategory = canAddCategory;
        this.canDeleteItem = canDeleteItem;
        this.createDate = createDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getSex() {
        return Sex;
    }

    public String getDOB() {
        return DOB;
    }

    public String getAddress() {
        return Address;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getUserRole() {
        return UserRole;
    }

    public Boolean getCanDiscount() {
        return canDiscount;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public Boolean getCanAddItem() {
        return canAddItem;
    }

    public Boolean getCanAddCategory() {
        return canAddCategory;
    }

    public Boolean getCanDeleteItem() {
        return canDeleteItem;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId=" + userId +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Sex='" + Sex + '\'' +
                ", DOB='" + DOB + '\'' +
                ", Address='" + Address + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", UserRole='" + UserRole + '\'' +
                ", canDiscount=" + canDiscount +
                ", canUpdate=" + canUpdate +
                ", canAddItem=" + canAddItem +
                ", canAddCategory=" + canAddCategory +
                ", canDeleteItem=" + canDeleteItem +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}