package com.example.calanderapp2;

import static com.example.calanderapp2.CalendarUtility.daysInMonth;
import static com.example.calanderapp2.CalendarUtility.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, AdapterView.OnItemSelectedListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //UC 1: Calendar layout
        initWidgets();
        CalendarUtility.selectedDate = LocalDate.now();
        setMonth();

        //UC 2: Dropdown menu button to change views
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.views, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



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



    @Override
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(i==0){ //monthlyAction
            //monthlyAction(view);
        }
        else if(i==1) { //DailyAction

            dailyAction(view);
        }
        else if(i==2) { //WeeklyAction

            weeklyAction(view);

        }
        else if(i==3){ //YearlyAction
            return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}