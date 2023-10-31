package com.example.calanderapp2;

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

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }

    private String eventName;
    private LocalDate date;
    private LocalTime time;
    private String calendarId;

    public Event(String eventName, LocalDate date, LocalTime time, String calendarId)
    {
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.calendarId = calendarId;
    }

    private LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getEventName() {
        return eventName;
    }

    public String calendarId() {
        return calendarId;
    }
}
