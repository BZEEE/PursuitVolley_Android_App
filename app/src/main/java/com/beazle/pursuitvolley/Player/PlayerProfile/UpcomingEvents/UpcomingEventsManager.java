package com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class UpcomingEventsManager {

    private static List<PlayerUpcomingEvent> playerUpcomingEventsList = new ArrayList<>();

    public static void AddEventToList(PlayerUpcomingEvent event) {
        playerUpcomingEventsList.add(event);
    }

    public static void RemoveEventFromList(PlayerUpcomingEvent event) {
        Iterator itr = playerUpcomingEventsList.iterator();
        while (itr.hasNext())
        {
            PlayerUpcomingEvent x = (PlayerUpcomingEvent) itr.next();
            if (x == event) {
                itr.remove();
            }
        }
    }

    public static void ClearUpcomingEventsList() {
        playerUpcomingEventsList.clear();
    }

    public static List<PlayerUpcomingEvent> GetUpcomingEventsList() {
        return playerUpcomingEventsList;
    }
}
