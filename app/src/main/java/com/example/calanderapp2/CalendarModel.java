package com.example.calanderapp2;

import java.util.List;
import java.util.ArrayList;

public class CalendarModel {
    private static CalendarModel instance;
    private String currentCalendarId;

    private CalendarModel() {
        // Private constructor to prevent instantiation
    }

    public static CalendarModel getInstance() {
        if (instance == null) {
            instance = new CalendarModel();
            instance.setCurrentCalendarId("My Calendar");
        }
        return instance;
    }

    public String getCurrentCalendarId() {
        return currentCalendarId;
    }

    public void setCurrentCalendarId(String calendarId) {
        this.currentCalendarId = calendarId;
    }
}

