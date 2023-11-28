package com.example.calendarapp2;

import static com.example.calendarapp2.CalendarUtility.daysInMonth;
import static com.example.calendarapp2.CalendarUtility.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;



import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextViewMenu;
    ArrayAdapter<String>adapterItemsMenu;
    ArrayAdapter<String>adapterItems;
    String[] views= {"Day","Week"};
    String[] menuOptions= {"View Calendars","Saved Contacts"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UC 1: Calendar layout
        initWidgets();
        CalendarUtility.selectedDate = LocalDate.now();
        setMonth();

        //UC 2: Dropdown menu button to change views
        autoCompleteTextView = findViewById(R.id.selectView);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,views);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        dailyAction(view);
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




    }

    public void viewCalendars(View viw) {
        Intent intent = new Intent(this, CalendarListActivity.class);
        startActivity(intent);

    }

    private void initWidgets() {
        //get both views
        monthYearText = findViewById(R.id.monthYearHeader);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);


    }
    private void setMonth() {
        monthYearText.setText(monthYearFromDate(CalendarUtility.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonth(CalendarUtility.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonth(View view) {
        CalendarUtility.selectedDate = CalendarUtility.selectedDate.minusMonths(1);
        setMonth();
    }

    public void nextMonth(View view) {
        CalendarUtility.selectedDate = CalendarUtility.selectedDate.plusMonths(1);
        setMonth();
    }




    public void onItemClick(int position, LocalDate date) {
        if(date != null) {
            CalendarUtility.selectedDate = date;
            setMonth();
        }
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


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}