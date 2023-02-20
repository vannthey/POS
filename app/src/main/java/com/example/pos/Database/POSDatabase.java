//package com.example.pos.Database;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//import com.example.pos.Database.Dao.POSDao;
//import com.example.pos.Database.Entity.UserAccount;
//
//@Database(entities = {UserAccount.class}, version = 1)
//public abstract class POSDatabase extends RoomDatabase {
//
//    public abstract POSDao getDao();
//
//    private static volatile POSDatabase INSTANCE;
//
//    public static POSDatabase getInstance(Context context) {
//
//        if (INSTANCE == null) {
//
//            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), POSDatabase.class, "POS_DATABASE").fallbackToDestructiveMigration().build();
//
//        }
//
//        return INSTANCE;
//    }
//
//    ;
//
//}
