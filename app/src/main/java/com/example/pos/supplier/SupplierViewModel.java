package com.example.pos.supplier;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.Supplier;

import java.util.List;

public class SupplierViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    public SupplierViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Supplier>> getAllSupplier() {
        return appDatabase.getDao().getAllSupplier();
    }

    public void createSupplier(Supplier supplier) {

        new Thread(() -> appDatabase.getDao().createSupplier(supplier)).start();


    }

    public void deleteSupplierById(int Id) {
        new Thread(() -> appDatabase.getDao().deleteSupplierById(Id)).start();
    }

    public void updateSupplierById(String supplierName, String supplierAddress, String supplierSex,
                                   String supplierPhoneNumber, int Id) {
        new Thread(() -> appDatabase.getDao().updateSupplierById(supplierName, supplierAddress, supplierSex,
                supplierPhoneNumber, Id)).start();

    }

}
