package com.example.pos.customer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.Customer;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Customer>> getAllCustomer() {
        return appDatabase.getDao().getAllCustomer();
    }

    public void createCustomer(Customer customer) {
        new Thread(() -> appDatabase.getDao().createCustomer(customer)).start();

    }

    void updateCustomerById(String customerName, String customerSex, String customerPhoneNumber, double customerDiscount,
                            String customerAddress, String customerProfile,
                            int customerId) {

        new Thread(() -> appDatabase.getDao().updateCustomerById(customerName, customerSex, customerPhoneNumber,
                customerDiscount, customerAddress,customerProfile,
                customerId)).start();
    }

    public void deleteCustomerById(int customerId) {
        new Thread(() -> appDatabase.getDao().deleteCustomerById(customerId)).start();

    }

}
