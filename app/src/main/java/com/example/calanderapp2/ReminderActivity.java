package com.example.calanderapp2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class ReminderActivity extends AppCompatActivity {
    Button saveButton, reminderDateButton, reminderTimeButton;
    TextView eventNameTV;
    String notifyTime;
    String eventDate2,eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        String name = getIntent().getStringExtra("title"); //title = name of event
        eventDate2 = getIntent().getStringExtra("date"); //date = date of event
        eventTime = getIntent().getStringExtra("time"); //time = time of event
        String person = getIntent().getStringExtra("person");
        eventNameTV = (TextView) findViewById(R.id.reminderDetails);
        String eventName = "Event Name: " + name;
        String eventDate = "Event Date: " + eventDate2;
        eventNameTV.setText(eventName);

        reminderDateButton = (Button) findViewById(R.id.reminderDateButton);
        reminderTimeButton = (Button) findViewById(R.id.reminderTimeButton);
        saveButton = (Button) findViewById(R.id.saveButtonFromReminder);


        reminderTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();   //formats and sets time on the reminderTimeButton
            }
        });
        reminderDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(); //formats and sets date on the reminderDateButton
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //title = event Name
                //date = reminder date
                //time = reminder time
                String title = eventNameTV.getText().toString();
                String date = reminderDateButton.getText().toString().trim();
                String time = reminderTimeButton.getText().toString().trim();

                //if no date or time is chosen then ask them choose
                if (time.equals("time") || date.equals("date")) {
                    Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                }
                else {
                    //insert into database the name of event, date, time,
                    insertInDb(name, eventDate2, eventTime, date, time);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");

                    //convert String to LocalDate
                    LocalDate localDate = LocalDate.parse(date, formatter);

                    DateFormat dateFormat = new SimpleDateFormat("h:mm aa");
                    String dateString = dateFormat.format(new Date()).toString();

                    //only send notification when the date and time matches date and time right now
                    if(localDate.equals(LocalDate.now()) && dateString.equals(time)) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(ReminderActivity.this, "My Notification");

                        builder.setContentTitle("Reminder Notification");
                        builder.setSmallIcon(R.drawable.ic_launcher_background);
                        builder.setContentText("Reminder! " + title + ", is happening on " + eventDate + ".");
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(ReminderActivity.this);
                        if (ActivityCompat.checkSelfPermission(ReminderActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        managerCompat.notify(1, builder.build());
                    }
//
                    finish();
                }



            }


        });


        Button back= findViewById(R.id.backFromReminder);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }
    private void insertInDb(String name, String eventDate2, String eventTime, String date, String time) {
        //insert into database
         new dbManagerForOwnReminderNotification(this).addReminder(name, eventDate2, eventTime, date,time);

    }
    private void selectTime() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                notifyTime = i + ":" + i1;                                                        //temp variable to store the time to set alarm
                reminderTimeButton.setText(FormatTime(i, i1));                                                //sets the button text as selected time
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }
    private void selectDate() {                                                                     //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                reminderDateButton.setText(day + "-" + (month + 1) + "-" + year);                             //sets the selected date as test for button
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr format and assigns am or pm
        String time;
        time = "";
        String formattedMinute;
        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }
        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }
        return time;
    }

}