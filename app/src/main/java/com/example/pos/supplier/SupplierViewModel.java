package com.example.pos.supplier;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.POSDatabase;

import java.util.List;

public class SupplierViewModel extends AndroidViewModel {
    POSDatabase posDatabase;

    public SupplierViewModel(@NonNull Application application) {
        super(application);
        posDatabase = POSDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Supplier>> getAllSupplier() {
        return posDatabase.getDao().getAllSupplier();
    }

    public void createSupplier(Supplier supplier) {

        new Thread(() -> posDatabase.getDao().createSupplier(supplier)).start();


    }

    public void deleteSupplierById(int Id) {
        new Thread(() -> posDatabase.getDao().deleteSupplierById(Id)).start();
    }

    public void updateSupplierById(String supplierName, String supplierAddress, String supplierSex,
                                   String supplierPhoneNumber, int Id) {
        new Thread(() -> posDatabase.getDao().updateSupplierById(supplierName, supplierAddress, supplierSex,
                supplierPhoneNumber, Id)).start();

    }

}
