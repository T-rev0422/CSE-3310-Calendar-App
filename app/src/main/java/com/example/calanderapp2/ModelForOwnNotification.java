package com.example.calanderapp2;

public class ModelForOwnNotification {
    String eventName, reminderDate, reminderTime, eventDate, eventTime;
    public ModelForOwnNotification() {
    }
    public ModelForOwnNotification(String eventName, String eventDate, String eventTime, String reminderDate, String reminderTime) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
    }
    public String getEventName() {
        return eventName;
    }
    public String getEventDate() {
        return eventDate;
    }
    public String getEventTime() {
        return eventTime;
    }
    public String getReminderTime() {
        return reminderTime;
    }
    public String getReminderDate() {
        return reminderDate;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    public void setEventTome(String eventTimee) {
        this.eventName = eventTime;
    }
    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public void setReminderTime(String time) {
        this.reminderTime = time;
    }
}
