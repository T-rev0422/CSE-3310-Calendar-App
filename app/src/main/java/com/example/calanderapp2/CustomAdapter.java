package com.example.calanderapp2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> eventNameList;
    ArrayList<String> reminderDateList;
    ArrayList<String>  reminderTimeList;
    ArrayList<String>  eventDateList;
    ArrayList<String>  eventTimeList;

    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, ArrayList<String> eventNameList,  ArrayList<String> eventDateList, ArrayList<String>  eventTimeList, ArrayList<String> reminderDateList, ArrayList<String> reminderTimeList) {
        this.context = context;
        this.eventNameList = eventNameList;
        this.reminderDateList = reminderDateList;
        this.eventDateList = eventDateList;
        this.eventTimeList = eventTimeList;
        this.reminderTimeList = reminderTimeList;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return eventNameList.size();
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
        view = inflater.inflate(R.layout.list_view, null);
        TextView eventName = (TextView) view.findViewById(R.id.eventNameListView);
        TextView reminderDate = (TextView) view.findViewById(R.id.reminderDateListView);
        TextView eventDate = (TextView) view.findViewById(R.id.eventDateListView);
        TextView eventTime = (TextView) view.findViewById(R.id.eventTimeListView);
        TextView reminderTime = (TextView) view.findViewById(R.id.reminderTimeListView);
        String eventName2 =  "Event Name: " + eventNameList.get(i);
        String eventDate2 = "Event Date: " + eventDateList.get(i);
        String eventTime2 = "Event Time: " + eventTimeList.get(i);
        String reminderDate2 = "Reminder Date: " + reminderDateList.get(i);
        String reminderTime2 = "Reminder Time: " + reminderTimeList.get(i);

        eventName.setText(eventName2);
        eventDate.setText(eventDate2);
        eventTime.setText(eventTime2);
        reminderDate.setText(reminderDate2);
        reminderTime.setText(reminderTime2);
        return view;
    }
}