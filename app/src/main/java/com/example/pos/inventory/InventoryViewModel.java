package com.example.pos.inventory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.POSDatabase;

import java.util.List;

public class InventoryViewModel extends AndroidViewModel {
    POSDatabase posDatabase;

    public InventoryViewModel(@NonNull Application application) {
        super(application);
        posDatabase = POSDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Inventory>> getAllInventory() {
        return posDatabase.getDao().getAllInventory();
    }

    public void createInventory(Inventory inventory) {
        new Thread(() -> posDatabase.getDao().createInventory(inventory)).start();
    }

    public void deleteInventoryById(int inventoryId) {
        new Thread(() -> posDatabase.getDao().deleteInventoryById(inventoryId)).start();
    }

    public void updateInventoryById(String inventoryAddress, String inventoryName, int inventoryId) {
        new Thread(() -> posDatabase.getDao().updateInventoryById(inventoryAddress, inventoryName, inventoryId)).start();
    }
}
