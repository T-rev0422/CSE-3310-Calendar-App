package com.example.calanderapp2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbManagerForContactReminderSMS extends SQLiteOpenHelper {
    private static String dbname = "reminderForContacts";                                                      //Table  name to store reminders in sqllite

    public dbManagerForContactReminderSMS(@Nullable Context context) {

        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //sql query to insert data in sqllite
        String query = "create table tbl_reminderContacts(id integer primary key autoincrement,eventName text,eventDate text,eventTime text,phone text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i, int i1 ) {

        String query = "DROP TABLE IF EXISTS tbl_reminderContacts";                                         //sql query to check table with the same name or not
        sqLiteDatabase.execSQL(query);                                                              //executes the sql command
        onCreate(sqLiteDatabase);

    }

    public String addReminder(String eventName, String eventDate, String eventTime, String phone) {

        SQLiteDatabase database = this.getReadableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("eventName", eventName);                                                          //Inserts  data into sqllite database
        contentValues.put("eventDate", eventDate);
        contentValues.put("eventTime", eventTime);
        contentValues.put("phone", phone);

        float result = database.insert("tbl_reminderContacts", null, contentValues);    //returns -1 if data successfully inserts into database

        if (result == -1) {
            return "Failed";
        } else {

            return "Successfully inserted into dbForContactReminders";

        }

    }

    public Cursor readAllReminders() {
        SQLiteDatabase database = this.getWritableDatabase();
/*
        String q = "DROP TABLE IF EXISTS tbl_reminderContacts";                                         //sql query to check table with the same name or not
        database.execSQL(q);
        String q2 = "create table tbl_reminderContacts(id integer primary key autoincrement,eventName text,eventDate text,eventTime text,phone text)";
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL(q2);
*/


        String query = "select * from tbl_reminderContacts order by id desc";                               //Sql query to  retrieve  data from the database
        Cursor cursor = database.rawQuery(query, null);


        return cursor;
    }


}
