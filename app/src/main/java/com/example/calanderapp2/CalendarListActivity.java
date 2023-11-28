package com.example.calanderapp2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CalendarListActivity extends AppCompatActivity {

    private Spinner calendarSpinner;
    private List<CalendarModel> calendarList;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        calendarSpinner = findViewById(R.id.calendarSpinner);

        //Initialize the list of created calendars and spinner
        calendarList = retrieveCalendars();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCalendarNames());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calendarSpinner.setAdapter(spinnerAdapter);
        //Setup a way to go back to a previous screen
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go back to previous screen
                onBackPressed();
            }
        });
    }


    //Handle the "Create New Calendar" button click
    public void createNewCalendar(View view) {
        //Create an AlertDialog to input the new calendar name
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New Calendar");

        //Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

        //Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().trim();
                if (!name.isEmpty()) {
                    // Add the new calendar name to the spinner
                    spinnerAdapter.add(name);
                    spinnerAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void previousView() {
        super.onBackPressed();
    }

    // Replace this with your actual data retrieval method
    private List<CalendarModel> retrieveCalendars() {
        return new ArrayList<>();
    }

    private List<String> getCalendarNames() {
        List<String> names = new ArrayList<>();
        for (CalendarModel calendar : calendarList) {
            names.add(calendar.getCalendarName());
        }
        return names;
    }
}
