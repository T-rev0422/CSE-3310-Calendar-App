package com.example.calanderapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MultipleDates extends AppCompatActivity {
    Button saveButton2, reminderDateButtonDate, reminderTimeButton;
    TextView dateDetails;
    String notifyTime;
    int counter=0;
    String eventDate,eventTime,eventDate2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_dates);

        String eventName = getIntent().getStringExtra("title"); //title = name of event
        eventDate2 = getIntent().getStringExtra("date"); //date = date of event




        eventTime = getIntent().getStringExtra("time"); //time = time of event
        //String person = getIntent().getStringExtra("person");
        dateDetails = (TextView) findViewById(R.id.dateDetails);
        String dateDet =  "Event Name: " + eventName  + "\nEvent Time: " + eventTime;

        dateDetails.setText(dateDet);

        reminderDateButtonDate = (Button) findViewById(R.id.reminderDateButtonDate);
        //reminderTimeButton = (Button) findViewById(R.id.reminderTimeButton);
        saveButton2 = (Button) findViewById(R.id.saveButtonFromDates);

        reminderDateButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( reminderDateButtonDate.getText().toString().equals("date")) {
                    Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                }
                else {
                    selectDate(); //formats and sets date on the reminderDateButton
                }
            }
        });

        saveButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeStr = eventTime;
                //if the user enters eg 2:00 instead of 02:00
                if(timeStr.length() == 4) {
                    timeStr = '0' + timeStr;
                }
                eventDate=reminderDateButtonDate.getText().toString();
                LocalTime time= LocalTime.parse(timeStr);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d-MM-yyyy");

                //convert String to LocalDate
                DateFormat inputFormat = new SimpleDateFormat("dd MMMM yyyy");
                DateFormat inputFormat2 = new SimpleDateFormat("d-MM-yyyy");
                DateFormat outputFormat = new SimpleDateFormat("d-MM-yyyy");

                Date dateD,dateDRem = null;
                try {
                    dateD = inputFormat.parse(eventDate2);
                    dateDRem = inputFormat2.parse(eventDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                assert dateD != null;
                assert dateDRem != null;

                String dateStr1 = outputFormat.format(dateD);
                String dateStr2 = outputFormat.format(dateDRem);

                LocalDate date = LocalDate.parse(dateStr1, formatter2);

                LocalDate date2 = LocalDate.parse(dateStr2, formatter2);
                LocalDate dateTemp = date2;
                dateTemp = dateTemp.minusDays(1);

                while(!dateTemp.equals(date)) {
                    dateTemp = dateTemp.minusDays(1);

                    counter++;
                }
                date2.minusDays(counter);


                String currentCalendarId = CalendarModel.getInstance().getCurrentCalendarId();

                Event newEvent2 = new Event(eventName, date2, time, currentCalendarId);
                Event newEvent = new Event(eventName, date, time, currentCalendarId);
                Event.eventList.add(newEvent);
                Event.eventList.add(newEvent2);
                startActivity(new Intent(MultipleDates.this,DailyCalendarActivity.class));


            }
        });
    }

    private void selectDate() {                                                                     //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                reminderDateButtonDate.setText(day + "-" + (month + 1) + "-" + year);                             //sets the selected date as test for button
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}



