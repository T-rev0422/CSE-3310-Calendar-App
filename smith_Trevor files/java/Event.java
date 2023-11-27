package com.example.calendarapp2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    public static ArrayList<Event> eventList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, String eventTime)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventList)
        {
            if(event.getDate().equals(date) && event.getEventTime().equals(eventTime))
                events.add(event);
        }

        return events;
    }

    private String eventName;
    private LocalDate date;
    private String eventTime;
    private String calendarId;

    public Event(String eventName, LocalDate date, String eventTime)
    {
        this.eventName = eventName;
        this.date = date;
        this.eventTime = eventTime;
    }

    private LocalDate getDate() {
        return date;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventName() {
        return eventName;
    }

    public String calendarId() {
        return calendarId;
    }
}