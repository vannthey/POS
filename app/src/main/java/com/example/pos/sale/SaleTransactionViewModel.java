package com.example.pos.sale;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.Entity.SaleTransaction;
import com.example.pos.Database.POSDatabase;

import java.util.List;

public class SaleTransactionViewModel extends AndroidViewModel {
    POSDatabase posDatabase;

    public SaleTransactionViewModel(@NonNull Application application) {
        super(application);
        posDatabase = POSDatabase.getInstance(application.getApplicationContext());
    }

    public void createSaleTransaction(SaleTransaction transactionList) {
        new Thread(() -> posDatabase.getDao().createSaleTransaction(transactionList)).start();
    }

    public LiveData<List<SaleTransaction>> getAllSaleTransaction() {
        return posDatabase.getDao().getAllSaleTransaction();
    }

    public void editProductOnSaleById(double price, int qty, double dist, int id) {
        new Thread(() -> posDatabase.getDao().editProductOnSaleById(price, qty, dist, id)).start();
    }

    public void deleteSaleTransactionById(int saleId) {
        new Thread(() -> posDatabase.getDao().deleteSaleTransactionById(saleId)).start();
    }

    public void deleteAfterPay() {
        new Thread(() -> posDatabase.getDao().deleteAfterPay()).start();
    }
}
