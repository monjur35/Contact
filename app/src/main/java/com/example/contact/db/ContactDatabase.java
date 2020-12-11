package com.example.contact.db;

import android.content.Context;
import android.net.http.SslCertificate;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.contact.DAO.ContactDao;
import com.example.contact.Entities.ContactModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ContactModel.class},version = 2)
public abstract class ContactDatabase extends RoomDatabase {
    private static ContactDatabase db;
    public static ExecutorService dbExecutor= Executors.newFixedThreadPool(5);

    public abstract ContactDao getContactDao();

    private static Migration MIGRATION_1_2=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table 'tbl_contact' add column  'blood_group' text");
        }
    };


    public static ContactDatabase getInstance(Context context){
        if (db!=null){
            return db;
        }
        synchronized (ContactDatabase.class){
            db= Room.databaseBuilder(context,ContactDatabase.class,"contact_db").addMigrations(MIGRATION_1_2).build();
            return db;
        }

    }
}
