package com.example.calanderapp2;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class EventEditActivity extends AppCompatActivity {

    private static final int RESULT_ADD_CONTACT = 0;
    private static final int REQUEST_ADD_CONTACT = 0;
    private EditText eventNameET, eventTimeET, contactName,contactNumber;
    private TextView eventDateTV;
    private Button contactButton;
    private static final int REQUEST_CONTACT = 1;
    //private static final int REQUEST_ADD_CONTACT = 0;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private Button ContactPick;
    private TextView ContactName;

    private TextView PhoneNumber;

    public static String phone;
    public static String name;

    public static HashMap<String, String> contactList = new HashMap<>();
    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextViewMenu;
    ArrayAdapter<String>adapterItemsMenu;
    ArrayAdapter<String>adapterItems;

    String[] menuOptions= {"View Event Contacts","Remind Contacts", "Set Own Reminder", "Multiple Dates"};

    private EditText number;
    private LocalTime time;
    private int buttonCheck=0;
    private static ArrayList<ModelForContactReminderSMS> dataHolder = new ArrayList<ModelForContactReminderSMS>();
    private static ArrayList<String>eventDateList = new ArrayList<>();
    private static ArrayList<String> eventNameList = new ArrayList<>();
    private static ArrayList<String> eventTimeList = new ArrayList<>();
    private static ArrayList<String> phoneList = new ArrayList<>();
    private static ArrayList<String> eventContacts = new ArrayList<>();
    private static ArrayList<String> nameList = new ArrayList<>();
    private static ArrayList<String> numberList = new ArrayList<>();

   // public ArrayList<Model> dataHolder = new ArrayList<Model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        time = LocalTime.now();
        initWidgets();

        eventDateTV.setText("Date: " + CalendarUtility.formattedDate(CalendarUtility.selectedDate));

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
/*
        ContactPick = findViewById(R.id.contact_pick);
        //ContactName = findViewById(R.id.name);
        //PhoneNumber = findViewById(R.id.contact_number);


        ActivityResultLauncher<Intent> pickContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    // Handle the result of picking a contact here
                    Intent data = result.getData();
                    onActivityResult(REQUEST_CONTACT,result.getResultCode() ,data);


                }
        );

        ContactPick.setOnClickListener(view -> {
            pickContactLauncher.launch(pickContact);
            buttonCheck = 1;
        });


        requestContactsPermission();
        updateButton(hasContactsPermission());
*/
        autoCompleteTextViewMenu = findViewById(R.id.eventMenu);
        adapterItemsMenu = new ArrayAdapter<String>(this,R.layout.list_item,menuOptions);
        autoCompleteTextViewMenu.setAdapter(adapterItemsMenu);
        autoCompleteTextViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                switch(j) {
                    case 0:
                        //view all contacts;
                        /*
                        Button viewContacts = findViewById(R.id.viewContacts);
                        viewContacts.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
*/
                                Intent intent = new Intent(getApplicationContext(), EventContacts.class);
                                intent.putExtra("name", name);

                                startActivity(intent);

/*
                            }
                        });
                        */

                        break;
                    case 1:

                        /*
                        Button sendRemInvite = findViewById(R.id.sendReminderInvite);
                        sendRemInvite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                         */
                                Cursor cursor = new dbManagerForContactReminderSMS(getApplicationContext()).readAllReminders();//Cursor To Load data From the database
                                dataHolder.clear();
                                eventDateList.clear();
                                eventNameList.clear();
                                eventTimeList.clear();
                                phoneList.clear();
                                nameList.clear();
                                while (cursor.moveToNext()) {
                                    ModelForContactReminderSMS modelForContactReminderSMS = new ModelForContactReminderSMS(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                                    dataHolder.add(modelForContactReminderSMS);
                                }


                                for(int i = 0; i < dataHolder.size(); i++) {
                                    eventNameList.add(dataHolder.get(i).getEventName());
                                    phoneList.add(dataHolder.get(i).getPhone());
                                    eventDateList.add(dataHolder.get(i).getEventDate());
                                    eventTimeList.add(dataHolder.get(i).getEventTime());
                                    nameList.add(dataHolder.get(i).getPerson());
                                }



                                for(int i = 0; i < dataHolder.size(); i++) {


                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

                                    LocalDate eventDate2 = LocalDate.parse(eventDateList.get(i), formatter);
                                    String dayBefore = eventDate2.minusDays(1).toString();


                                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                                    //send reminder only 1 day before event and only to people who you shared the event to
                                   // if(dayBefore.equals(currentDate) && phone.equals(dataHolder.get(i).getPhone())) {
                                    if(dayBefore.equals(currentDate)) {
                                        String phoneNum = dataHolder.get(i).getPhone();

                                        String message = "This is a reminder message." +
                                                "\nDetails of the event are as follows: \n" + "Event Name: " + dataHolder.get(i).getEventName() + "\nEvent Date: " + dataHolder.get(i).getEventDate() + "\nEvent Time: " + dataHolder.get(i).getEventTime() + ".";


                                        if(!phoneNum.isEmpty() && !message.isEmpty()) {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(phoneNum,null,message,null,null);
                                            Toast.makeText(getApplicationContext(), "Successful" , Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                                        }

                                    }


                                }


                                dataHolder.clear();
                                eventDateList.clear();
                                eventNameList.clear();
                                eventTimeList.clear();
                                phoneList.clear();
                                nameList.clear();
                                /*
                            }

                                 */
                            break;
/*
                        });
*/



                    case 2:
                        //schedule reminder notification for ownself
                        /*
                        Button sendReminder= findViewById(R.id.sendReminder);
                        sendReminder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

*/
                                Intent intent2 = new Intent(getApplicationContext(), ReminderActivity.class);
                                intent2.putExtra("title", eventNameET.getText().toString());

                                intent2.putExtra("date",CalendarUtility.formattedDate(CalendarUtility.selectedDate));
                                intent2.putExtra("time", eventTimeET.getText().toString());
                                intent2.putExtra("person", name);
                                startActivity(intent2);



/*
                            }

                        });
*/
                        break;

                    case 3:
                        String dateTemp = CalendarUtility.formattedDate(CalendarUtility.selectedDate);
                        Intent intent3 = new Intent(getApplicationContext(), MultipleDates.class);
                        intent3.putExtra("title", eventNameET.getText().toString());
                        intent3.putExtra("date", dateTemp);
                        intent3.putExtra("time", eventTimeET.getText().toString());

                        startActivity(intent3);
                        break;




                }


            }
        });


        Button addContacts= findViewById(R.id.contacts);
        final Intent addCon = new Intent(this,AddContactsActivity.class);

        ActivityResultLauncher<Intent> addContactsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    // Handle the result of picking a contact here
                    Intent data = result.getData();
                    onActivityResult2(REQUEST_ADD_CONTACT,result.getResultCode() ,data);


                }
        );
        addContacts.setOnClickListener(view2 -> {
            addContactsLauncher.launch(addCon);
            buttonCheck = 1;
        });






        Button sendInvite = findViewById(R.id.sendInvite);
        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(EventEditActivity.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    //if (buttonCheck == 0) { //user enters the contact number manually
                        //number = findViewById(R.id.number);
                        //number = findViewById(R.id.number);
                        phone = contactNumber.getText().toString();
                        name = contactName.getText().toString();
                   /// } else {
                    //    phone = PhoneNumber.getText().toString(); //user gets contact from contacts app

                   // }

                    sendSms();
                    //Toast.makeText(getApplicationContext(), "name: " + name, Toast.LENGTH_SHORT).show();

                    insertInDB(eventNameET.getText().toString(),CalendarUtility.formattedDate(CalendarUtility.selectedDate),eventTimeET.getText().toString(), phone, name);

                } else {
                    ActivityCompat.requestPermissions(EventEditActivity.this, new String[]{Manifest.permission.SEND_SMS},
                            100);
                }





            }

        });




        Button save= findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timeStr = eventTimeET.getText().toString();
                //if the user enters eg 2:00 instead of 02:00
                if(timeStr.length() == 4) {
                    timeStr = '0' + timeStr;
                }

                time= LocalTime.parse(timeStr);

                String currentCalendarId = CalendarModel.getInstance().getCurrentCalendarId();
                String eventName = eventNameET.getText().toString();
                Event newEvent = new Event(eventName, CalendarUtility.selectedDate, time, currentCalendarId);
                Event.eventList.add(newEvent);

                startActivity(new Intent(EventEditActivity.this,DailyCalendarActivity.class));





            }

        });

        Button back= findViewById(R.id.backFromEvent);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }

        });

    }
    private void insertInDB(String eventName, String eventDate, String eventTime, String phone, String person) {
        Toast.makeText(getApplicationContext(), new dbManagerForContactReminderSMS(this).addReminder(eventName, eventDate,eventTime, phone, person) , Toast.LENGTH_SHORT).show();

    }

    public void sendSms() {
        String phoneNum = phone;
        String message = "You are invited to an event."  +
                ".\nDetails of the event are as follows: \n" + "Event Name: " + eventNameET.getText().toString() + "\nEvent " + eventDateTV.getText().toString() + "\nEvent Time: " + eventTimeET.getText().toString() + ".";
        if(!phone.isEmpty() && !message.isEmpty()) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum,null,message,null,null);
            Toast.makeText(getApplicationContext(), "Successful" , Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateButton(boolean enable)
    {
        ContactPick.setEnabled(enable);

    }
    private boolean hasContactsPermission()
    {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED;
    }

    // Request contact permission if it
    // has not been granted already
    private void requestContactsPermission()
    {
        if (!hasContactsPermission())
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{ android.Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS_PERMISSION && grantResults.length > 0)
        {
            updateButton(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
        if(requestCode == 100 && grantResults.length >0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            sendSms();
        }else {
            Toast.makeText(getApplicationContext(), "Permission denied" , Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult2(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_CONTACT && resultCode == Activity.RESULT_OK) {
            String name2 = data.getStringExtra("name");
            String phone2 = data.getStringExtra("phone");

            contactName = findViewById(R.id.name);
            contactNumber = findViewById(R.id.number);
            contactName.setText(name2);
            contactNumber.setText(phone2);

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CONTACT && data != null) {
            Cursor cur;

            Uri uri = data.getData();
            cur = getContentResolver().query(uri, null, null, null, null);
            if (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        nameList.add(name);
                        numberList.add(phoneNumber);
                        phone = phoneNumber;
                    }
                    phones.close();
                }

            }
            cur.close();



        }

    }

    private void initWidgets(){
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeET = findViewById(R.id.eventTimeET);
    }

    public void saveEventAction(View view){
        String timeStr = eventTimeET.getText().toString();
        //if the user enters eg 2:00 instead of 02:00
        if(timeStr.length() == 4) {
            timeStr = '0' + timeStr;
        }

        time= LocalTime.parse(timeStr);

        String currentCalendarId = CalendarModel.getInstance().getCurrentCalendarId();
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtility.selectedDate, time, currentCalendarId);
        Event.eventList.add(newEvent);
        finish();
    }

}

