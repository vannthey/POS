package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;


public class UserAccount {
    @PrimaryKey(autoGenerate = true)
    int userId;

    @ColumnInfo
    String Firstname;

    @ColumnInfo
    String Lastname;

    @ColumnInfo
    String Username;

    @ColumnInfo
    long Password;

    @ColumnInfo
    Boolean isAdmin;

    @ColumnInfo
    Boolean isManager;

    @ColumnInfo
    Boolean isSeller;

    @ColumnInfo
    Boolean isCashier;

    @ColumnInfo
    Boolean canDiscount;

    @ColumnInfo
    Boolean canUpdate;

    @ColumnInfo
    Boolean canAddItem;

    @ColumnInfo
    Boolean canAddCategory;

    @ColumnInfo
    Boolean canDeleteItem;

    @ColumnInfo
    String createDate;

    public UserAccount(String firstname, String lastname, String username, long password, Boolean isAdmin, Boolean isManager, Boolean isSeller, Boolean isCashier, Boolean canDiscount, Boolean canUpdate, Boolean canAddItem, Boolean canAddCategory, Boolean canDeleteItem, String createDate) {
        Firstname = firstname;
        Lastname = lastname;
        Username = username;
        Password = password;
        this.isAdmin = isAdmin;
        this.isManager = isManager;
        this.isSeller = isSeller;
        this.isCashier = isCashier;
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

    public String getUsername() {
        return Username;
    }

    public long getPassword() {
        return Password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public Boolean getManager() {
        return isManager;
    }

    public Boolean getSeller() {
        return isSeller;
    }

    public Boolean getCashier() {
        return isCashier;
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
        return "UserAccount{" + "userId=" + userId + ", Firstname='" + Firstname + '\'' + ", Lastname='" + Lastname + '\'' + ", Username='" + Username + '\'' + ", Password=" + Password + ", isAdmin=" + isAdmin + ", isManager=" + isManager + ", isSeller=" + isSeller + ", isCashier=" + isCashier + ", canDiscount=" + canDiscount + ", canUpdate=" + canUpdate + ", canAddItem=" + canAddItem + ", canAddCategory=" + canAddCategory + ", canDeleteItem=" + canDeleteItem + ", createDate='" + createDate + '\'' + '}';
    }
}