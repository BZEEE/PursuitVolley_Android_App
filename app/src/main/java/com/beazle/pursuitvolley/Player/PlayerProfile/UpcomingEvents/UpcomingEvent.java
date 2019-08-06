package com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents;

public class UpcomingEvent {
    // represents all the data of a upcoming event instance for the player
    // which will then give them the option to sign up
    // such as location, time, age groups, etc
    private String eventTitle;
    private String eventDate;
    private String eventBeginTime;
    private String eventEndTime;
    private String eventLocation;

    public UpcomingEvent(String eventTitle, String eventDate, String eventBeginTime, String eventEndTime, String eventLocation) {
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventBeginTime = eventBeginTime;
        this.eventEndTime = eventEndTime;
        this.eventLocation = eventLocation;
    }

    public String GetEventTitle() {
        return eventTitle;
    }

    public String GetEventDate() {
        return eventDate;
    }

    public String GetEventBeginTime() {
        return eventBeginTime;
    }

    public String GetEventEndTime() {
        return eventEndTime;
    }

    public String GetEventLocation() {
        return eventLocation;
    }

    public void SetEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void SetEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void SetEventBeginTime(String eventBeginTime) {
        this.eventBeginTime = eventBeginTime;
    }

    public void SetEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public void SetEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
