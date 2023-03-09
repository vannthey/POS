package com.example.pos.customer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.Entity.Customer;
import com.example.pos.Database.POSDatabase;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {
    POSDatabase posDatabase;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        posDatabase = POSDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Customer>> getAllCustomer() {
        return posDatabase.getDao().getAllCustomer();
    }

    public void createCustomer(Customer customer) {
        new Thread(() -> posDatabase.getDao().createCustomer(customer)).start();

    }

    void updateCustomerById(String customerName, String customerSex, String customerPhoneNumber, double customerDiscount,
                            String customerAddress, String customerProfile,
                            int customerId) {

        new Thread(() -> posDatabase.getDao().updateCustomerById(customerName, customerSex, customerPhoneNumber,
                customerDiscount, customerAddress,customerProfile,
                customerId)).start();
    }

    public void deleteCustomerById(int customerId) {
        new Thread(() -> posDatabase.getDao().deleteCustomerById(customerId)).start();

    }

}
