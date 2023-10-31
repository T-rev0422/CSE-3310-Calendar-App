package com.example.calanderapp2;

import static com.example.calanderapp2.CalendarUtility.selectedDate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DailyCalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, AdapterView.OnItemSelectedListener{
    private TextView monthDayText;
    private TextView dayOfWeek;
    private ListView hourListView;
    //private Button contactButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.views, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinner.getOnItemSelectedListener());

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


    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(i==0) { //MonthlyAction

            monthlyAction(view);
        }
        else if(i==2) { //WeeklyAction

            weeklyAction(view);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}