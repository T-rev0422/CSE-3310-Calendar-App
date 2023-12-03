package com.example.calanderapp2;


import static com.example.calanderapp2.EventEditActivity.contactList;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;


public class AddContactFromContactsAppActivity extends AppCompatActivity {
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private Button ContactPick;
    private TextView ContactName;

    private TextView PhoneNumber;

    public static String phone;
    private String name, phoneNumber;

    Intent data;
    private Button backButton;
    private Button add;
    ListView simpleListView;
    private static ArrayList<String> nameList = new ArrayList<>();
    private static ArrayList<String> numberList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_from_contacts_app);

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        //Toast.makeText(getApplicationContext(), contactList.get(0) , Toast.LENGTH_SHORT).show();

        add = findViewById(R.id.addContacts);
        ActivityResultLauncher<Intent> pickContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    // Handle the result of picking a contact here
                   data = result.getData();
                    onActivityResult(REQUEST_CONTACT,result.getResultCode() ,data);


                }
        );

        add.setOnClickListener(view -> {
            pickContactLauncher.launch(pickContact);
        });


        requestContactsPermission();
        updateButton(hasContactsPermission());


        simpleListView = (ListView) findViewById(R.id.simpleListView2);
        ForContactsAdapter forContactsAdapter = new ForContactsAdapter(getApplicationContext(), nameList, numberList);


        simpleListView.setAdapter(forContactsAdapter);

        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(getApplicationContext(), phoneNumber , Toast.LENGTH_SHORT).show();

                setResult(Activity.RESULT_OK,
                        new Intent().putExtra("name", nameList.get(position)).putExtra("phone", numberList.get(position)));

                finish();

            }
        });

        Button back= findViewById(R.id.backFromSavedContacts);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }

        });

/*
        // Intent to pick contacts
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        ContactPick = findViewById(R.id.contact_pick);
        ContactName = findViewById(R.id.contact_name);
        PhoneNumber = findViewById(R.id.contact_number);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthlyAction(v);
            }
        });
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

 */

    }

    public void monthlyAction(View view) {

        startActivity(new Intent(this,MainActivity.class));
    }
    public void updateButton(boolean enable)
    {
        add.setEnabled(enable);

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
//String

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    while (phones.moveToNext()) {
                       phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                       // String
                        //ContactName.setText(name);
                       // PhoneNumber.setText(phoneNumber);

                        nameList.add(name);
                        numberList.add(phoneNumber);
                        simpleListView = (ListView) findViewById(R.id.simpleListView2);
                        ForContactsAdapter forContactsAdapter = new ForContactsAdapter(getApplicationContext(), nameList, numberList);


                        simpleListView.setAdapter(forContactsAdapter);

                        //phone = phoneNumber;
                    }
                    phones.close();
                }

            }
            cur.close();



        }



    }

}