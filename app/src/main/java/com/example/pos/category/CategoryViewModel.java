package com.example.pos.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.POSDatabase;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    POSDatabase posDatabase;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        posDatabase = POSDatabase.getInstance(application.getApplicationContext());
    }

    public void createCategory(Category category) {
        posDatabase.getDao().createCategory(category);
    }

    public LiveData<List<Category>> getAllCategory() {
        return posDatabase.getDao().getAllCategory();
    }

    public void updateCategoryById(String categoryName, int categoryId) {
        posDatabase.getDao().updateCategoryById(categoryName, categoryId);
    }

    public void deleteCategoryById(int categoryId) {
        posDatabase.getDao().deleteCategoryById(categoryId);
    }
}
