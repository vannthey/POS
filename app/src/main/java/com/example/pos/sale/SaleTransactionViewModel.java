package com.example.pos.sale;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.SaleTransaction;

import java.util.List;

public class SaleTransactionViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    double Total = 0;
    double discount = 0;
    double subTotal = 0;
    double afterDiscount = 0;

    public SaleTransactionViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public void createSaleTransaction(SaleTransaction transactionList) {
        new Thread(() -> appDatabase.getDao().createSaleTransaction(transactionList)).start();
    }

    public LiveData<List<SaleTransaction>> getAllSaleTransaction() {
        return appDatabase.getDao().getAllSaleTransaction();
    }

    public void editProductOnSaleById(double price, int qty, double dist, double productAmount, int id) {
        new Thread(() -> appDatabase.getDao().editProductOnSaleById(price, qty, dist, productAmount, id)).start();
    }

    public void deleteSaleTransactionById(int saleId) {
        new Thread(() -> appDatabase.getDao().deleteSaleTransactionById(saleId)).start();
    }

    public void deleteAfterPay() {
        new Thread(() -> appDatabase.getDao().deleteAfterPay()).start();
    }
}
