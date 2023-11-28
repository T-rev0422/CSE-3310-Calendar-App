package com.example.calendarapp2;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET, eventTimeET;
    private TextView eventDateTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        eventDateTV.setText("Date: " + CalendarUtility.formattedDate(CalendarUtility.selectedDate));
    }

    private void initWidgets(){
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeET = findViewById(R.id.eventTimeET);
    }

    public void saveEventAction(View view){
        String eventName = eventNameET.getText().toString();
        String eventTime = eventTimeET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtility.selectedDate, eventTime.toString());
        Event.eventList.add(newEvent);
        finish();
    }

}