package com.example.calanderapp2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalendarListActivity extends AppCompatActivity {

    private Spinner calendarSpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private Set<String> calendarSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        calendarSpinner = findViewById(R.id.calendarSpinner);

        //Initialize the list of created calendars and spinner
        calendarSet = retrieveCalendars();
        //Check if "My Calendar" is in the set, and add it if not
        if (!calendarSet.contains("My Calendar")) {
            calendarSet.add("My Calendar");
            saveCalendars(calendarSet);
        }

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(calendarSet));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calendarSpinner.setAdapter(spinnerAdapter);

        //Setup a way to go back to a previous screen
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go back to the previous screen
                onBackPressed();
            }
        });

        // Setup the OK button under the spinner
        Button okButton = findViewById(R.id.okayButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the selected calendarId from the spinner
                String selectedCalendarId = (String) calendarSpinner.getSelectedItem();

                //Pass the selected calendarId to CalendarModel
                CalendarModel.getInstance().setCurrentCalendarId(selectedCalendarId);

                //Start the MainActivity (Month view)
                Intent intent = new Intent(CalendarListActivity.this, MainActivity.class);
                startActivity(intent);
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
                    //Add the new calendar name to the spinner
                    spinnerAdapter.add(name);
                    spinnerAdapter.notifyDataSetChanged();

                    //Add the calendar name to SharedPreferences
                    calendarSet.add(name);
                    saveCalendars(calendarSet);
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

    //Retrieve calendar names from SharedPreferences
    private Set<String> retrieveCalendars() {
        SharedPreferences preferences = getSharedPreferences("Calendars", MODE_PRIVATE);
        return preferences.getStringSet("calendarSet", new HashSet<>());
    }

    //Save calendar names to SharedPreferences
    private void saveCalendars(Set<String> calendars) {
        SharedPreferences preferences = getSharedPreferences("Calendars", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("calendarSet", calendars);
        editor.apply();
    }
}