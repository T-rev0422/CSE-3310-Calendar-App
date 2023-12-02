package com.example.calanderapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ForContactsAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> nameList;
    ArrayList<String> phoneList;


    LayoutInflater inflater;

    public ForContactsAdapter(Context applicationContext, ArrayList<String> nameList,  ArrayList<String> phoneList) {
        this.context = context;
        this.nameList = nameList;
        this.phoneList = phoneList;

        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.contact_list_view, null);
        TextView contactNameView = (TextView) view.findViewById(R.id.nameListView);
        TextView contactNumberView = (TextView) view.findViewById(R.id.phoneListView);

        String contactName =  "Contact Name: " + nameList.get(i);
        String contactNumber = "Contact Number: " + phoneList.get(i);

        contactNameView.setText(contactName);
        contactNumberView.setText(contactNumber);

        return view;
    }
}
