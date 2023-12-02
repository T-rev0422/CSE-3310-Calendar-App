package com.example.calanderapp2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;import android.content.pm.PackageManager;

import java.time.LocalTime;
import java.util.ArrayList;

public class AddContactsActivity extends AppCompatActivity {
    private TextView  contactName,contactNumber;
    private EditText eventNameET, eventTimeET;
    private TextView eventDateTV;
    private Button contactButton;
    private static final int REQUEST_ADD_CONTACT = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private Button ContactPick;
    private TextView ContactName;

    private TextView PhoneNumber;

    public static String phone;
    public static String name;
    private int buttonCheck=0;
    private static ArrayList<String> eventDateList = new ArrayList<>();
    private static ArrayList<String> eventNameList = new ArrayList<>();
    private static ArrayList<String> eventTimeList = new ArrayList<>();
    private static ArrayList<String> phoneList = new ArrayList<>();
    private static ArrayList<String> eventContacts = new ArrayList<>();
    private static ArrayList<String> nameList = new ArrayList<>();
    private static ArrayList<String> numberList = new ArrayList<>();

    private Button add;
    ListView simpleListView;
    //int buttonConfirm = 0; // 1 means choose contacts app
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);


        ContactPick = findViewById(R.id.addContactsApp);
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
            buttonCheck = 1;
        });


        requestContactsPermission();
        updateButton(hasContactsPermission());

        Button addSaved= findViewById(R.id.addSavedList);
        final Intent addCon = new Intent(this, AddContactFromContactsAppActivity.class);

        ActivityResultLauncher<Intent> addSavedContactsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    // Handle the result of picking a contact here
                    Intent data = result.getData();
                    onActivityResult2(REQUEST_ADD_CONTACT,result.getResultCode() ,data);


                }
        );
        addSaved.setOnClickListener(view -> {
            addSavedContactsLauncher.launch(addCon);
            buttonCheck = 0;
            /*
            setResult(Activity.RESULT_OK,
                            new Intent().putExtra("name", name).putExtra("phone", phone));
        */
        });

        /*
        Button addSaved= findViewById(R.id.addSavedList);
        addSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AddContactsActivity.this, AddContactFromContactsAppActivity.class));
                //simpleListView.setAdapter(forContactsAdapter);
                /*
                simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        //setResult(Activity.RESULT_OK,
                        //        new Intent().putExtra("name", name).putExtra("phone", phone));
                        //
                        //finish();

                    }
                });



            }

        });
*/
        Button back= findViewById(R.id.backFromContactsPage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }

        });

        Button save= findViewById(R.id.saveButtonFromContacts);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(buttonCheck==0) {
                    setResult(Activity.RESULT_OK,
                            new Intent().putExtra("name", contactName.getText().toString()).putExtra("phone", contactNumber.getText().toString()));

                }
                else{
                    setResult(Activity.RESULT_OK,
                            new Intent().putExtra("name", ContactName.getText().toString()).putExtra("phone", PhoneNumber.getText().toString()));

                }
                finish();
                //startActivity(intent);


               // startActivity(new Intent(EventEditActivity.this,DailyCalendarActivity.class));





            }

        });

    }
    public void onActivityResult2(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_CONTACT && resultCode == Activity.RESULT_OK) {
            String name2 = data.getStringExtra("name");
            String phone2 = data.getStringExtra("phone");




            contactName = findViewById(R.id.contact_name);
            contactNumber = findViewById(R.id.contact_number);
            contactName.setText(name2);
            contactNumber.setText(phone2);

        }
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

    @Override
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
                        ContactName.setText(name);
                        PhoneNumber.setText(phoneNumber);
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
}
