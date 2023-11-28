package com.example.calendarapp2;

import java.time.LocalTime;
import java.util.ArrayList;

public class HourEvent {
    String eventTime;
    ArrayList<Event> events;

    public HourEvent(String eventTime, ArrayList<Event> events) {
        this.eventTime = eventTime;
        this.events=events;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}