package com.example.calendarapp2;

import java.util.List;
import java.util.ArrayList;

public class CalendarModel {
    private String calendarName;
    private List<Event> events;

    public CalendarModel(String calendarName) {
        this.calendarName = calendarName;
        this.events = new ArrayList<>();
    }
    public String getCalendarName() {
        return calendarName;
    }
}