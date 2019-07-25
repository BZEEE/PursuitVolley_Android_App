package com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments;

public class CurrentAppointment {

    // represents all the data of a current appointment instance for the player
    // such as coach name, time, location etc
    private String appointmentCoachName;
    private String appointmentDate;
    private String appointmentLocation;

    public CurrentAppointment(String appointmentCoachName, String appointmentDate, String appointmentLocation) {
        this.appointmentCoachName = appointmentCoachName;
        this.appointmentDate = appointmentDate;
        this.appointmentLocation = appointmentLocation;
    }

    public String GetAppointmentCoachName() {
        return appointmentCoachName;
    }

    public String GetAppointmentDate() {
        return appointmentDate;
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

    public void SetAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

}
