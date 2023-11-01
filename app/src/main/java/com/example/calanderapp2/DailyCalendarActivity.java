package com.example.calanderapp2;

import static com.example.calanderapp2.CalendarUtility.selectedDate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DailyCalendarActivity extends AppCompatActivity implements  CalendarAdapter.OnItemListener{
    private TextView monthDayText;
    private TextView dayOfWeek;
    private ListView hourListView;
    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String>adapterItems;
    String[] views= {"Month","Week"};
    AutoCompleteTextView autoCompleteTextViewMenu;
    ArrayAdapter<String>adapterItemsMenu;

    String[] menuOptions= {"View Calendars","Saved Contacts"};

    //private Button contactButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();

        autoCompleteTextView = findViewById(R.id.selectView);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,views);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        monthlyAction(view);
                        break;
                    case 1:
                        weeklyAction(view);
                        break;


                }
            }
        });

        autoCompleteTextViewMenu = findViewById(R.id.Menu);
        adapterItemsMenu = new ArrayAdapter<String>(this,R.layout.list_item,menuOptions);
        autoCompleteTextViewMenu.setAdapter(adapterItemsMenu);
        autoCompleteTextViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        //view calendars UC 5;
                        viewCalendars(view);

                        break;
                    case 1:
                        //saved contacts page UC 6;
                        break;


                }
            }
        });

        /*contacts app button - to be implemented later
        contactButton = (Button) findViewById(R.id.contactButton);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactsActivity();
            }
        });
        */

    }

    /*for contacts app button - to be implemented later
    public void openContactsActivity() {
        Intent intent = new Intent(this, AddContactFromContactsAppActivity.class);
        startActivity(intent);

    }
    */

    public void viewCalendars(View viw) {
        Intent intent = new Intent(this, CalendarListActivity.class);
        startActivity(intent);

    }

    public void onItemClick(int position, LocalDate date) {
        CalendarUtility.selectedDate = date;
        setDay();
    }


    private void initWidgets() {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeek = findViewById(R.id.dayOfWeek);
        hourListView = findViewById(R.id.hourListView);
    }
    @Override
    protected void onResume(){
        super.onResume();
        setDay();
    }

    private void setDay() {
        monthDayText.setText(CalendarUtility.monthDayFromDate(selectedDate));
        String dayInWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeek.setText(dayInWeek);
        setHourAdapter();
    }

    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(),hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList() {
        ArrayList<HourEvent> hourList = new ArrayList<>();
        for(int hour=0;hour<24;hour++){
            LocalTime time = LocalTime.of(hour,0);
            ArrayList<Event> events = Event.eventsForDateAndTime(selectedDate,time);
            HourEvent hourEvent = new HourEvent(time,events);
            hourList.add(hourEvent);
        }
        return hourList;

    }

    public void previousDay(View view) {
        CalendarUtility.selectedDate = CalendarUtility.selectedDate.minusDays(1);
        setDay();
    }

    public void nextDay(View view) {
        CalendarUtility.selectedDate = CalendarUtility.selectedDate.plusDays(1);
        setDay();
    }

    public void newEvent(View view) {
        //startactivity
    }


    public void monthlyAction(View view) {

        startActivity(new Intent(this,MainActivity.class));
    }

    public void weeklyAction(View view) {

        startActivity(new Intent(this,WeekViewActivity.class));
    }



    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyCalendarActivity.class));
    }

}