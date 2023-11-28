package com.example.calanderapp2;

import android.content.Context;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;


import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, List<Event>events) {

        super(context,0, events);
    }


}
