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
    public LiveData<List<Inventory>> getAllInventory(){
        return posDatabase.getDao().getAllInventory();
    }
    public void createInventory(Inventory inventory){
        posDatabase.getDao().createInventory(inventory);
    }
}
