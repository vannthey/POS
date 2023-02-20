package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserAccount {
    @PrimaryKey(autoGenerate = true)
    public
    int userId;

    @ColumnInfo
    public
    String Firstname;

    @ColumnInfo
    public
    String Lastname;

    @ColumnInfo
    public
    String Username;

    @ColumnInfo
    public
    String Password;

    @ColumnInfo
    public
    Boolean isAdmin;

    @ColumnInfo
    public
    Boolean isManager;

    @ColumnInfo
    public
    Boolean isSeller;

    @ColumnInfo
    public
    Boolean isCashier;

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

    public UserAccount(String firstname, String lastname, String username, String password, Boolean isAdmin, Boolean isManager, Boolean isSeller, Boolean isCashier, Boolean canDiscount, Boolean canUpdate, Boolean canAddItem, Boolean canAddCategory, Boolean canDeleteItem, String createDate) {
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

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId=" + userId +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", isAdmin=" + isAdmin +
                ", isManager=" + isManager +
                ", isSeller=" + isSeller +
                ", isCashier=" + isCashier +
                ", canDiscount=" + canDiscount +
                ", canUpdate=" + canUpdate +
                ", canAddItem=" + canAddItem +
                ", canAddCategory=" + canAddCategory +
                ", canDeleteItem=" + canDeleteItem +
                ", createDate='" + createDate + '\'' +
                '}';
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

    public String getPassword() {
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
}