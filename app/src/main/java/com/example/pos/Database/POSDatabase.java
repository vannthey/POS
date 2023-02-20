package com.example.pos.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pos.Database.Dao.POSDao;
import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Item;
import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.Entity.Warehouse;

@Database(entities = {UserAccount.class, Category.class, Item.class, Warehouse.class}, version = 1)
public abstract class POSDatabase extends RoomDatabase {

    public abstract POSDao getDao();

    private static volatile POSDatabase INSTANCE;

    public static POSDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), POSDatabase.class, "POS_DATABASE").fallbackToDestructiveMigration().build();

        }

        return INSTANCE;
    }

    ;

}
