package com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule;

import java.util.ArrayList;
import java.util.List;

public class CoachAppointment {

    // represents all the data of a current appointment instance for the player
    // such as coach name, time, location etc
    private List<String> appointmentPlayerNames = new ArrayList<>();
    private String appointmentDate;
    private String appointmentBeginTime;
    private String appointmentEndTime;
    private String appointmentLocation;

    public CoachAppointment(
            String appointmentDate,
            String appointmentBeginTime,
            String appointmentEndTime,
            String appointmentLocation) {
        this.appointmentDate = appointmentDate;
        this.appointmentBeginTime = appointmentBeginTime;
        this.appointmentEndTime = appointmentEndTime;
        this.appointmentLocation = appointmentLocation;
    }

    public List<String> GetAppointmentPLayerNAmes() {
        return appointmentPlayerNames;
    }

    public String GetAppointmentDate() {
        return appointmentDate;
    }

    public String GetAppointmentBeginTime() {
        return appointmentBeginTime;
    }

    public String GetAppointmentEndTime() {
        return appointmentEndTime;
    }

    public String GetAppointmentLocation() {
        return appointmentLocation;
    }

    public void AddPlayerToAppointment(String playerName) {
        appointmentPlayerNames.add(playerName);
    }

    public void RemovePlayerToAppointment(String playerName) {
        appointmentPlayerNames.remove(playerName);
    }

    public void SetAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void SetAppointmentBeginTime(String appointmentBeginTime) {
        this.appointmentBeginTime = appointmentBeginTime;
    }

    public void SetAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    public void SetAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }
}
