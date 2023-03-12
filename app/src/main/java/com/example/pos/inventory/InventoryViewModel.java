package com.example.pos.inventory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.Inventory;

import java.util.List;

public class InventoryViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    public InventoryViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Inventory>> getAllInventory() {
        return appDatabase.getDao().getAllInventory();
    }

    public void createInventory(Inventory inventory) {
        new Thread(() -> appDatabase.getDao().createInventory(inventory)).start();
    }

    public void deleteInventoryById(int inventoryId) {
        new Thread(() -> appDatabase.getDao().deleteInventoryById(inventoryId)).start();
    }

    public void updateInventoryById(String inventoryAddress, String inventoryName, int inventoryId) {
        new Thread(() -> appDatabase.getDao().updateInventoryById(inventoryAddress, inventoryName, inventoryId)).start();
    }
}
