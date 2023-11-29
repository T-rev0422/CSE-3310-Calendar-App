/*package com.example.calanderapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class EventEditActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

    }


}

 */
package com.example.calanderapp2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET, eventTimeET;
    private TextView eventDateTV;
    private Button contactButton;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private Button ContactPick;
    private TextView ContactName;

    private TextView PhoneNumber;

    public static String phone;

    public HashMap<String, String> contactList = new HashMap<>();

    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        eventDateTV.setText("Date: " + CalendarUtility.formattedDate(CalendarUtility.selectedDate));
        Button enterContact = findViewById(R.id.enter);
        enterContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(EventEditActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    number = findViewById(R.id.number);
                    phone=number.getText().toString();
                    sendSms();
                }
                else{
                    ActivityCompat.requestPermissions(EventEditActivity.this,new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });


//+1 555 123 4567


        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        ContactPick = findViewById(R.id.contact_pick);
        ContactName = findViewById(R.id.contact_name);
        PhoneNumber = findViewById(R.id.contact_number);


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
        });


        requestContactsPermission();
        updateButton(hasContactsPermission());

        Button sendInvite = findViewById(R.id.sendInvite);
        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ContextCompat.checkSelfPermission(EventEditActivity.this, Manifest.permission.SEND_SMS)
                       == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
               }
               else{
                   ActivityCompat.requestPermissions(EventEditActivity.this,new String[]{Manifest.permission.SEND_SMS},
                           100);
               }
            }
        });
    }

    private void sendSms() {
        String phoneNum = phone;
        String message = "You are invited to an event called " + eventNameET.getText().toString() +
                ".\nDetails of the event are as follows: \n" + eventDateTV.getText().toString() + "\nTime: " + eventTimeET.getText().toString() + ".";
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CONTACT && data != null) {
            Cursor cursor;

            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {


                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                @SuppressLint("Range") String idRes = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                int intIdRes = Integer.parseInt(idRes);

                if (intIdRes == 1) {
                    Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                            null,
                            null);

                    while (cursor2.moveToNext()) {
                        @SuppressLint("Range") String number = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        ContactName.setText(contactName);
                        PhoneNumber.setText(number);
                        contactList.put(contactName,number);
                        phone = number;

                    }
                    cursor2.close();

                }
                cursor.close();
            }


        }

    }

    private void initWidgets(){
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeET = findViewById(R.id.eventTimeET);
    }

    public void saveEventAction(View view){
        String eventName = eventNameET.getText().toString();
        String eventTime = eventTimeET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtility.selectedDate, eventTime);
        Event.eventList.add(newEvent);
        finish();
    }

}