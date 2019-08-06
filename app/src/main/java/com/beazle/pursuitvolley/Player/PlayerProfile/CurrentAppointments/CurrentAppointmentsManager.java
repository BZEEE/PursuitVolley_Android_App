package com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CurrentAppointmentsManager {
    private static List<CurrentAppointment> currentAppointmentsList = new ArrayList<>();

    public static void AddAppointmentToList(CurrentAppointment appt) {
        currentAppointmentsList.add(appt);
    }

    public static void RemoveAppointmentFromList(CurrentAppointment appt) {
        Iterator itr = currentAppointmentsList.iterator();
        while (itr.hasNext())
        {
            CurrentAppointment x = (CurrentAppointment) itr.next();
            if (x == appt) {
                itr.remove();
            }
        }
    }

    public static void ClearCurrentAppointmentList() {
        currentAppointmentsList.clear();
    }

    public static List<CurrentAppointment> GetCurrentAppointmentsList() {
        return currentAppointmentsList;
    }
}
