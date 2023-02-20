package com.example.pos.Entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class UerAccount {
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
    Boolean isSaler;

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

    public UerAccount(String firstname, String lastname, String username, long password, Boolean isAdmin, Boolean isManager, Boolean isSaler, Boolean isCashier, Boolean canDiscount, Boolean canUpdate, Boolean canAddItem, Boolean canAddCategory, Boolean canDeleteItem) {
        Firstname = firstname;
        Lastname = lastname;
        Username = username;
        Password = password;
        this.isAdmin = isAdmin;
        this.isManager = isManager;
        this.isSaler = isSaler;
        this.isCashier = isCashier;
        this.canDiscount = canDiscount;
        this.canUpdate = canUpdate;
        this.canAddItem = canAddItem;
        this.canAddCategory = canAddCategory;
        this.canDeleteItem = canDeleteItem;
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

    public Boolean getSaler() {
        return isSaler;
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

    @Override
    public String toString() {
        return "UerAccount{" +
                "userId=" + userId +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Username='" + Username + '\'' +
                ", Password=" + Password +
                ", isAdmin=" + isAdmin +
                ", isManager=" + isManager +
                ", isSaler=" + isSaler +
                ", isCashier=" + isCashier +
                ", canDiscount=" + canDiscount +
                ", canUpdate=" + canUpdate +
                ", canAddItem=" + canAddItem +
                ", canAddCategory=" + canAddCategory +
                ", canDeleteItem=" + canDeleteItem +
                '}';
    }
}