package com.example.calanderapp2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbManagerForOwnReminderNotification extends SQLiteOpenHelper {
    private static String dbname = "reminder";                                                      //Table  name to store reminders in sqllite

    public dbManagerForOwnReminderNotification(@Nullable Context context) {

        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //sql query to insert data in sqllite
        String query = "create table tbl_reminder(id integer primary key autoincrement,eventName text,eventDate text,eventTime text,reminderDate text, reminderTime text)";
        sqLiteDatabase.execSQL(query);
    }
//int i, int i1
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i, int i1 ) {

        String query = "DROP TABLE IF EXISTS tbl_reminder";                                         //sql query to check table with the same name or not
        sqLiteDatabase.execSQL(query);                                                              //executes the sql command
        onCreate(sqLiteDatabase);

    }

    public String addReminder(String eventName, String eventDate, String eventTime, String reminderDate, String reminderTime) {

        SQLiteDatabase database = this.getReadableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("eventName", eventName);                                                          //Inserts  data into sqllite database
        contentValues.put("eventDate", eventDate);
        contentValues.put("eventTime", eventTime);
        contentValues.put("reminderDate", reminderDate);
        contentValues.put("reminderTime", reminderTime);

        float result = database.insert("tbl_reminder", null, contentValues);    //returns -1 if data successfully inserts into database

        if (result == -1) {
            return "Failed";
        } else {
            return "Successfully inserted into dbForOwnReminders";

        }

    }

    public Cursor readAllReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
/*
        String q = "DROP TABLE IF EXISTS tbl_reminder";                                         //sql query to check table with the same name or not
        database.execSQL(q);
        String q2 = "create table tbl_reminder(id integer primary key autoincrement,eventName text,eventDate text,eventTime text,reminderDate text, reminderTime text)";
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL(q2);
*/


        String query = "select * from tbl_reminder order by id desc";                               //Sql query to  retrieve  data from the database
        Cursor cursor = database.rawQuery(query, null);


        return cursor;
    }


}