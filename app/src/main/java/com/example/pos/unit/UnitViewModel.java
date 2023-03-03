package com.example.pos.unit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.Entity.Unit;
import com.example.pos.Database.POSDatabase;

import java.util.List;

public class UnitViewModel extends AndroidViewModel {
    POSDatabase posDatabase;

    public UnitViewModel(@NonNull Application application) {
        super(application);
        posDatabase = POSDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Unit>> getAllUnit() {
        return posDatabase.getDao().getAllUnit();
    }

    public void createUnit(Unit unit) {
        posDatabase.getDao().createUnit(unit);
    }

    public void updateUnitById(String unitTitle, int unitId) {
        posDatabase.getDao().updateUnitById(unitTitle, unitId);
    }

    public void deleteUnitById(int unitId) {
        posDatabase.getDao().deleteUnitById(unitId);
    }
}
