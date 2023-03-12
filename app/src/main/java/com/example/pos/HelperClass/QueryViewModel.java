package com.example.pos.HelperClass;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.pos.Database.AppDatabase;

public class QueryViewModel extends AndroidViewModel {
    int categoryId;
    AppDatabase appDatabase;

    public QueryViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }
}
