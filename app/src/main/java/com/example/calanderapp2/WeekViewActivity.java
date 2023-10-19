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
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeek();

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.views, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinner.getOnItemSelectedListener());
    }
    private void initWidgets() {
        //get both views
        monthYearText = findViewById(R.id.monthYearHeader);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
    }
    private void setWeek() {
        monthYearText.setText(monthYearFromDate(CalendarUtility.selectedDate));
        ArrayList<LocalDate> days = daysInWeek(CalendarUtility.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
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

    public void newEvent(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(i==0){ //monthlyAction
            monthlyAction(view);
        }
        else if(i==1) { //DailyAction
            //dailyAction(view);
        }
        else if(i==2) { //WeeklyAction
            setWeek();
        }
        else if(i==3){ //YearlyAction
            return;
        }
    }

    public void monthlyAction(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}