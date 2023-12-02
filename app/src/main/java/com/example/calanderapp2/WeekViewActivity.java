package com.example.calanderapp2;

import static com.example.calanderapp2.CalendarUtility.daysInWeek;
import static com.example.calanderapp2.CalendarUtility.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    AutoCompleteTextView autoCompleteTextView;


    ArrayAdapter<String>adapterItems;
    String[] views= {"Month","Day"};
    AutoCompleteTextView autoCompleteTextViewMenu;
    ArrayAdapter<String>adapterItemsMenu;

    String[] menuOptions= {"View Calendars","Saved Contacts", "View Reminders"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();

        //Retrieve calendarId from Intent
        Intent intent = getIntent();
        String calendarId = intent.getStringExtra("calendarId");

        setWeek();

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
                        dailyAction(view);
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
                        openContactsActivity();
                        break;
                    case 2:
                        //view own reminders notification list
                        viewReminders();
                        break;


                }
            }
        });

    }
    public void viewReminders() {
        startActivity(new Intent(this, ReminderList.class));
    }
    public void viewCalendars(View viw) {
        Intent intent = new Intent(this, CalendarListActivity.class);
        startActivity(intent);

    }

    public void openContactsActivity() {
        startActivity(new Intent(this, AddContactFromContactsAppActivity.class));


    }
    private void initWidgets() {
        //get both views
        monthYearText = findViewById(R.id.monthYearHeader);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        eventListView = findViewById(R.id.eventListView);

    }
    private void setWeek() {
        monthYearText.setText(monthYearFromDate(CalendarUtility.selectedDate));
        ArrayList<LocalDate> days = daysInWeek(CalendarUtility.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }

    private void setEventAdapter() {
        String currentCalendarId = CalendarModel.getInstance().getCurrentCalendarId();
        ArrayList<Event> events = Event.eventsForDateAndCalendarId(CalendarUtility.selectedDate, currentCalendarId);

        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), events);
        eventListView.setAdapter(eventAdapter);
    }



    public void previousWeek(View view) {
        CalendarUtility.selectedDate = CalendarUtility.selectedDate.minusWeeks(1);
        setWeek();
    }

    public void nextWeek(View view) {
        CalendarUtility.selectedDate = CalendarUtility.selectedDate.plusWeeks(1);
        setWeek();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtility.selectedDate = date;
        setWeek();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setEventAdapter();
    }

    public void newEvent(View view) {

       // startActivity(new Intent(this,EventEditActivity.class));
    }



    public void monthlyAction(View view) {

        startActivity(new Intent(this,MainActivity.class));
    }


    public void weeklyAction(View view) {
        startActivity(new Intent(this,WeekViewActivity.class));
    }


    public void dailyAction(View view) {

        startActivity(new Intent(this, DailyCalendarActivity.class));
    }





}