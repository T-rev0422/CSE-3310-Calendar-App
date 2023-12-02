package com.example.calanderapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class EventContacts extends AppCompatActivity {
    private static ArrayList<ModelForContactReminderSMS> dataHolder = new ArrayList<ModelForContactReminderSMS>();
    private static ArrayList<String>eventDateList = new ArrayList<>();
    private static ArrayList<String> eventNameList = new ArrayList<>();
    private static ArrayList<String> eventTimeList = new ArrayList<>();
    private static ArrayList<String> phoneList = new ArrayList<>();
    private static ArrayList<String> eventContacts = new ArrayList<>();
    private static ArrayList<String> nameList = new ArrayList<>();
    ListView simpleListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_contacts);
        String eventName = getIntent().getStringExtra("name");
        Cursor cursor = new dbManagerForContactReminderSMS(getApplicationContext()).readAllReminders();//Cursor To Load data From the database
        dataHolder.clear();
        eventDateList.clear();
        eventNameList.clear();
        eventTimeList.clear();
        phoneList.clear();
        nameList.clear();
        while (cursor.moveToNext()) {
            ModelForContactReminderSMS modelForContactReminderSMS = new ModelForContactReminderSMS(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5));
            dataHolder.add(modelForContactReminderSMS);
        }


        for(int i = 0; i < dataHolder.size(); i++) {
            eventNameList.add(dataHolder.get(i).getEventName());
            phoneList.add(dataHolder.get(i).getPhone());
            eventDateList.add(dataHolder.get(i).getEventDate());
            eventTimeList.add(dataHolder.get(i).getEventTime());
            nameList.add(dataHolder.get(i).getPerson());

        }
        for(int i = 0; i < dataHolder.size(); i++) {
            if(dataHolder.get(i).getEventName().equals(eventName)){
                eventContacts.add(dataHolder.get(i).getPhone());
            }
        }

        simpleListView = (ListView) findViewById(R.id.simpleListView3);
        ForEventContactAdapter forEventContactAdapter = new ForEventContactAdapter(getApplicationContext(), eventNameList, nameList, phoneList);


        simpleListView.setAdapter(forEventContactAdapter);

        Button back= findViewById(R.id.backFromContacts);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHolder.clear();
                eventDateList.clear();
                eventNameList.clear();
                eventTimeList.clear();
                phoneList.clear();
                finish();

            }

        });

    }
}