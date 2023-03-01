package com.example.pos.Database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pos.Database.Dao.POSDao;
import com.example.pos.Database.Entity.Category;
import com.example.pos.Database.Entity.Customer;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.Entity.PayType;
import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.Entity.Stock;
import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.Entity.Unit;
import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.Entity.SaleTransaction;

@Database(entities = {UserAccount.class, Category.class, Product.class, Inventory.class,
        Supplier.class, PayType.class, Customer.class, Unit.class, Stock.class, SaleTransaction.class},
        version = 1)
public abstract class POSDatabase extends RoomDatabase {

    public abstract POSDao getDao();

    private static volatile POSDatabase INSTANCE;

    public static POSDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), POSDatabase.class,
                    "POS_DATABASE").fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

    ;

}
