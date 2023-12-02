package com.example.calanderapp2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

    public class ForEventContactAdapter extends BaseAdapter {
        Context context;
        ArrayList<String> nameList;
        ArrayList<String> phoneList;
        ArrayList<String> eventNameList;


        LayoutInflater inflater;

        public ForEventContactAdapter(Context applicationContext,ArrayList<String> eventNameList, ArrayList<String> nameList,  ArrayList<String> phoneList ) {
            this.context = context;
            this.nameList = nameList;
            this.phoneList = phoneList;
            this.eventNameList=eventNameList;

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
            view = inflater.inflate(R.layout.event_contact_list_view, null);
            TextView eventNameView = (TextView) view.findViewById(R.id.eventNameView);
            TextView nameView = (TextView) view.findViewById(R.id.nameView);
            TextView phoneView = (TextView) view.findViewById(R.id.phoneView);
            String eventName =  "Event Name: " + eventNameList.get(i);
            String contactName =  "Contact Name: " + nameList.get(i);
            String contactNumber = "Contact Number: " + phoneList.get(i);

            eventNameView.setText(eventName);
            nameView.setText(contactName);
            phoneView.setText(contactNumber);

            return view;
        }
    }


