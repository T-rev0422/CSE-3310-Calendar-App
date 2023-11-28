package com.example.calanderapp2;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtility.formattedDate(CalendarUtility.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtility.formattedTime(time));
    }

    private void initWidgets(){
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeET);
    }

    public void saveEventAction(View view){
        String currentCalendarId = CalendarModel.getInstance().getCurrentCalendarId();
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtility.selectedDate, time, currentCalendarId);
        Event.eventList.add(newEvent);
        finish();
    }

}