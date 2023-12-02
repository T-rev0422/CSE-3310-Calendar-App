package com.example.calanderapp2;


public class ModelForContactReminderSMS {
    String eventName, eventDate, eventTime, phone;
    public ModelForContactReminderSMS() {
    }
    public ModelForContactReminderSMS(String eventName, String eventDate, String eventTime, String phone) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.phone = phone;
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
    public String getPhone() {
        return phone;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setTime(String eventTime) {
        this.eventTime = eventTime;
    }
}

