package com.example.calanderapp2;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ReminderList extends AppCompatActivity {
    FloatingActionButton mCreateRem;

    private static ArrayList<ModelForOwnNotification> dataHolder = new ArrayList<ModelForOwnNotification>();
    private static ArrayList<String> eventNameList =  new ArrayList<>();
    private static ArrayList<String>eventDateList = new ArrayList<>();
    private static ArrayList<String> eventTimeList = new ArrayList<>();
    private static ArrayList<String> reminderTimeList = new ArrayList<>();
    private static ArrayList<String> reminderDateList = new ArrayList<>();

    ListView simpleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        String eventName = getIntent().getStringExtra("title");
        String eventDate = getIntent().getStringExtra("date");
        String eventTime = getIntent().getStringExtra("time");


        Cursor cursor = new dbManagerForOwnReminderNotification(getApplicationContext()).readAllReminders();//Cursor To Load data From the database
        while (cursor.moveToNext()) {
            ModelForOwnNotification modelForOwnNotification = new ModelForOwnNotification(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

            dataHolder.add(modelForOwnNotification);

        }


        for(int i = 0; i < dataHolder.size(); i++) {

            eventNameList.add(dataHolder.get(i).eventName);
            eventDateList.add(dataHolder.get(i).eventDate);
            eventTimeList.add(dataHolder.get(i).eventTime);
            reminderDateList.add(dataHolder.get(i).reminderDate);
            reminderTimeList.add(dataHolder.get(i).reminderTime);


        }

        simpleListView = (ListView) findViewById(R.id.simpleListView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), eventNameList, eventDateList, eventTimeList, reminderDateList, reminderTimeList);


        simpleListView.setAdapter(customAdapter);

        Button back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataHolder.clear();
                eventNameList.clear();
                eventDateList.clear();
                eventTimeList.clear();
                reminderDateList.clear();
                reminderTimeList.clear();

                finish();

            }

        });

    }


}

