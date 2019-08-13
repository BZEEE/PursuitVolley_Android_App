package com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule;

public class Events {
    String EVENT, TIME, DATE, MONTH, YEAR;

    public Events(String EVENT, String TIME, String DATE, String MONTH, String YEAR) {
        this.EVENT = EVENT;
        this.TIME = TIME;
        this.DATE = DATE;
        this.MONTH = MONTH;
        this.YEAR = YEAR;
    }

    public String GetEVENT() {
        return EVENT;
    }

    public String GetTIME() {
        return TIME;
    }

    public String GetDATE() {
        return DATE;
    }

    public String GetMONTH() {
        return MONTH;
    }

    public String GetYEAR() {
        return YEAR;
    }

    public void SetEVENT(String EVENT) {
        this.EVENT = EVENT;
    }

    public void SetTIME(String TIME) {
        this.TIME = TIME;
    }

    public void SetDATE(String DATE) {
        this.DATE = DATE;
    }

    public void SetMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public void SetYEAR(String YEAR) {
        this.YEAR = YEAR;
    }
}
