package com.example.calanderapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends ArrayAdapter<HourEvent> {
    public HourAdapter(@NonNull Context context, List<HourEvent>hourEvents) {
        super(context,0, hourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        HourEvent hourEvent = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);

        setHour(convertView,hourEvent.time);
        setEvents(convertView,hourEvent.events);
        return convertView;
    }

    private void setEvents(View convertView, ArrayList<Event> events) {
        TextView event1 = convertView.findViewById(R.id.event1);
        TextView event2 = convertView.findViewById(R.id.event2);
        TextView event3 = convertView.findViewById(R.id.event3);

        //if no events for this hour
        if(events.size() == 0) {
            hideEvent(event1);
            hideEvent(event2);
            hideEvent(event3);
        }
        else if(events.size() == 1) {
            setEvent(event1,events.get(0));
            hideEvent(event2);
            hideEvent(event3);
        }
        else if(events.size() == 2) {
            setEvent(event1,events.get(0));
            setEvent(event2,events.get(1));
            hideEvent(event3);
        }
        else if(events.size() == 3) {
            setEvent(event1,events.get(0));
            setEvent(event2,events.get(1));
            setEvent(event3,events.get(2));
        }
        else{
            setEvent(event1,events.get(0));
            setEvent(event2,events.get(1));
            event3.setVisibility(View.VISIBLE);
            String eventsNotShown = String.valueOf(events.size()-2);
            eventsNotShown += " More events";
            event3.setText(eventsNotShown);

        }

    }

    private void setEvent(TextView view, Event event) {
        view.setText(event.getEventName());
        view.setVisibility(View.VISIBLE);
    }

    private void hideEvent(TextView view) {
        view.setVisibility(View.INVISIBLE);
    }

    private void setHour(View convertView, LocalTime time) {
        TextView timeView = convertView.findViewById(R.id.time);
        timeView.setText(CalendarUtility.formattedShortTime(time));

    }
}
