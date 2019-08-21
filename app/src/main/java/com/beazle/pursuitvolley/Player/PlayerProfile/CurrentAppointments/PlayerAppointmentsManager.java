package com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PlayerAppointmentsManager {
    private static List<PlayerAppointment> currentAppointmentsList = new ArrayList<>();

    public static void AddAppointmentToList(PlayerAppointment appt) {
        currentAppointmentsList.add(appt);
    }

    public static void RemoveAppointmentFromList(PlayerAppointment appt) {
        Iterator itr = currentAppointmentsList.iterator();
        while (itr.hasNext())
        {
            PlayerAppointment x = (PlayerAppointment) itr.next();
            if (x == appt) {
                itr.remove();
            }
        }
    }

    public static void ClearCurrentAppointmentList() {
        currentAppointmentsList.clear();
    }

    public static List<PlayerAppointment> GetCurrentAppointmentsList() {
        return currentAppointmentsList;
    }
}
