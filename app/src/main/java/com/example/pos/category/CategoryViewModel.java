package com.example.pos.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pos.Database.AppDatabase;
import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Relationship.CategoryWithProducts;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
    }

    public void createCategory(Category category) {
        new Thread(() -> appDatabase.getDao().createCategory(category)).start();
    }

    public LiveData<List<Category>> getAllCategory() {
        return appDatabase.getDao().getAllCategory();
    }

    public void updateCategoryById(String categoryName, int categoryId) {
        new Thread(() -> appDatabase.getDao().updateCategoryById(categoryName, categoryId)).start();
    }

    public void deleteCategoryById(int categoryId) {
        new Thread(() -> appDatabase.getDao().deleteCategoryById(categoryId)).start();
    }

    //CategoryWithProduct
    public LiveData<List<CategoryWithProducts>> getCategoryWithProducts() {
        return appDatabase.getDao().getCategoryWithProducts();
    }

}
