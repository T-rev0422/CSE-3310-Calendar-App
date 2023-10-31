package com.example.calanderapp2;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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


public class AddContactFromContactsAppActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private Button ContactPick;
    private TextView ContactName;

    private TextView PhoneNumber;
    private TextView EmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_from_contacts_app);


        //Intent intent = new Intent(this, AddContactFromContactsAppActivity.class);
        // Intent to pick contacts
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        ContactPick = findViewById(R.id.contact_pick);
        ContactName = findViewById(R.id.contact_name);
        PhoneNumber = findViewById(R.id.contact_number);
        EmailAddress = findViewById(R.id.contact_email);
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
    public void updateButton(boolean enable)
    {
        ContactPick.setEnabled(enable);

        //ContactName.setEnabled(enable);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CONTACT && data != null)
        {



            String[] queryFields = new String[]{ContactsContract.Contacts._ID, ContactsContract.Data.DISPLAY_NAME,  ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Email.DATA };
            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, queryFields, ContactsContract.Contacts._ID, null,
                    null);

            //ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            try
            {

                if (cursor.getCount() == 0) return;
                cursor.moveToFirst();


                String name = cursor.getString(1);
                ContactName.setText(name);

                String phoneNumber = cursor.getString(2);
                PhoneNumber.setText(phoneNumber);

                String emailAddress = cursor.getString(3);
                EmailAddress.setText(emailAddress);



            }
            finally
            {

                cursor.close();
            }
        }
    }
    @Override
    public void onClick(View view) {


    }

}