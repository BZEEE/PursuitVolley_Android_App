package com.beazle.pursuitvolley.Coach;

import com.beazle.pursuitvolley.Coach.Coach;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CoachManager {
    private static List<Coach> coachList = new ArrayList<>();

    public static void AddCoachToList(Coach coach) {
        coachList.add(coach);
    }

    public static void RemoveCoachFromList(Coach coach) {
        Iterator itr = coachList.iterator();
        while (itr.hasNext())
        {
            Coach x = (Coach) itr.next();
            if (x == coach) {
                itr.remove();
            }
        }
    }

    public static List<Coach> GetCoachList() {
        return coachList;
    }

    public static Coach GetSpecificCoach(String uid) {
        Iterator itr = coachList.iterator();
        while (itr.hasNext())
        {
            Coach coach = (Coach) itr.next();
            if (coach.GetUniqueId() == uid) {
                return coach;
            }
        }
        return null;
    }
}
