package com.example.calanderapp2;


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
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


public class AddContactFromContactsAppActivity extends AppCompatActivity {
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private Button ContactPick;
    private TextView ContactName;

    private TextView PhoneNumber;

    public static String phone;

    public HashMap<String, String> contactList = new HashMap<>();
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_from_contacts_app);


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

    }

    public void monthlyAction(View view) {

        startActivity(new Intent(this,MainActivity.class));
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

}