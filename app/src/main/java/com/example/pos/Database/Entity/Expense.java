package com.example.pos.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Expense {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int id;

    @ColumnInfo
    public String expenseName;

    @ColumnInfo
    public double expensePrice;

    @ColumnInfo
    public double expensePaid;

    @ColumnInfo
    public double expanseDue;

    @ColumnInfo
    public String expenseDescription;

    @ColumnInfo
    public String creatorName;

    @ColumnInfo
    public String createDate;

    public Expense(String expenseName, double expensePrice, double expensePaid, double expanseDue, String expenseDescription, String creatorName, String createDate) {
        this.expenseName = expenseName;
        this.expensePrice = expensePrice;
        this.expensePaid = expensePaid;
        this.expanseDue = expanseDue;
        this.expenseDescription = expenseDescription;
        this.creatorName = creatorName;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public double getExpensePrice() {
        return expensePrice;
    }

    public double getExpensePaid() {
        return expensePaid;
    }

    public double getExpanseDue() {
        return expanseDue;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Expense{" + "id=" + id + ", expenseName='" + expenseName + '\'' + ", expensePrice=" + expensePrice + ", expensePaid=" + expensePaid + ", expanseDue=" + expanseDue + ", expenseDescription='" + expenseDescription + '\'' + ", creatorName='" + creatorName + '\'' + ", createDate='" + createDate + '\'' + '}';
    }
}
