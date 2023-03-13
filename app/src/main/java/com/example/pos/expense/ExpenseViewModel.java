package com.example.pos.expense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.Expense;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public void createExpense(Expense expense) {
        appDatabase.getDao().createExpense(expense);
    }

    public LiveData<List<Expense>> getAllExpense() {
        return appDatabase.getDao().getAllExpense();
    }

    public void deleteExpenseById(int id) {
        appDatabase.getDao().deleteExpenseById(id);
    }

    public void updateExpenseById(String expenseName, double expensePrice, double expensePaid, double expanseDue, String expenseDescription, int id) {
        appDatabase.getDao().updateExpenseById(expenseName, expensePrice, expensePaid, expanseDue, expenseDescription, id);
    }
}
