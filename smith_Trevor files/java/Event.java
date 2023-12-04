package com.example.calendarapp2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
public class Event {
    public static ArrayList<Event> eventList = new ArrayList<>();
    public static String EVENT_EDIT_EXTRA = "eventEdit";

    public static ArrayList<Event> eventsForDateAndCalendarId(LocalDate date, String calendarId) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventList) {
            if (event.getDate().equals(date) && event.getCalendarId().equals(calendarId)) {
                events.add(event);
            }
        }

        return events;
    }


    public static ArrayList<Event> eventsForDateAndTimeAndCalendarId(LocalDate date, LocalTime time, String calendarId) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventList) {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if (event.getDate().equals(date) && eventHour == cellHour && event.getCalendarId().equals(calendarId)) {
                events.add(event);
            }
        }

        return events;
    }


    public static ArrayList<Event> eventsForCalendarId(String calendarId) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventList) {
            if (event.calendarId.equals(calendarId)) {
                events.add(event);
            }
        }

        return events;
    }

    private int id;
    private String eventName;
    private LocalDate date;
    private LocalTime time;
    private String calendarId;

    private Date deleted;

    public Event(int id, String eventName, LocalDate date, LocalTime time, String calendarId, Date deleted) {
        this.id = id;
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.calendarId = calendarId;
        this.deleted = deleted;
    }

    public Event(int id, String eventName, LocalDate date, LocalTime time, String calendarId) {
        this.id = id;
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.calendarId = calendarId;
        this.deleted = null;
    }

    public static Event getEventForID(int passedEventID){
        for(Event event : eventList){
            if(event.getId() == passedEventID)
                return event;
        }
        return null;
    }

    public static ArrayList<Event> nonDeletedNotes()
    {
        ArrayList<Event> nonDeleted = new ArrayList<>();
        for(Event event : eventList)
        {
            if(event.getDeleted() == null)
                nonDeleted.add(event);
        }

        return nonDeleted;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId){
        this.calendarId = calendarId;
    }

    public Date getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Date deleted)
    {
        this.deleted = deleted;
    }
}