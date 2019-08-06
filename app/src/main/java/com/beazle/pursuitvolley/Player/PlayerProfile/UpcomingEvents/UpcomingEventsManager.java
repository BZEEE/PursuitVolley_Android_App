package com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class UpcomingEventsManager {

    private static List<UpcomingEvent> upcomingEventsList = new ArrayList<>();

    public static void AddEventToList(UpcomingEvent event) {
        upcomingEventsList.add(event);
    }

    public static void RemoveEventFromList(UpcomingEvent event) {
        Iterator itr = upcomingEventsList.iterator();
        while (itr.hasNext())
        {
            UpcomingEvent x = (UpcomingEvent) itr.next();
            if (x == event) {
                itr.remove();
            }
        }
    }

    public static void ClearUpcomingEventsList() {
        upcomingEventsList.clear();
    }

    public static List<UpcomingEvent> GetUpcomingEventsList() {
        return upcomingEventsList;
    }
}
