package com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments;

public class CurrentAppointment {

    // represents all the data of a current appointment instance for the player
    // such as coach name, time, location etc
    private String appointmentCoachName;
    private String appointmentDate;
    private String appointmentBeginTime;
    private String appointmentEndTime;
    private String appointmentLocation;

    public CurrentAppointment(
            String appointmentCoachName,
            String appointmentDate,
            String appointmentBeginTime,
            String appointmentEndTime,
            String appointmentLocation) {
        this.appointmentCoachName = appointmentCoachName;
        this.appointmentDate = appointmentDate;
        this.appointmentBeginTime = appointmentBeginTime;
        this.appointmentEndTime = appointmentEndTime;
        this.appointmentLocation = appointmentLocation;
    }

    public String GetAppointmentCoachName() {
        return appointmentCoachName;
    }

    public String GetAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentBeginTime() {
        return appointmentBeginTime;
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public String GetAppointmentLocation() {
        return appointmentLocation;
    }

    public void SetAppointmentCoachName(String appointmentCoachName) {
        this.appointmentCoachName = appointmentCoachName;
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
