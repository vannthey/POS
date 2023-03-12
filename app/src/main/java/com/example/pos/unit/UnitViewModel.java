package com.example.pos.unit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.Unit;

import java.util.List;

public class UnitViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    public UnitViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Unit>> getAllUnit() {
        return appDatabase.getDao().getAllUnit();
    }

    public void createUnit(Unit unit) {
        new Thread(()->{
            appDatabase.getDao().createUnit(unit);
        }).start();

    }

    public void updateUnitById(String unitTitle, int unitId) {
        new Thread(()->{
            appDatabase.getDao().updateUnitById(unitTitle, unitId);
        }).start();

    }

    public void deleteUnitById(int unitId) {
        new Thread(()->{
            appDatabase.getDao().deleteUnitById(unitId);
        }).start();
    }
}
