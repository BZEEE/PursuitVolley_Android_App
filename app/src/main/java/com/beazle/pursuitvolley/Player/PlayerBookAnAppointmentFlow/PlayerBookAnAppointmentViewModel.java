package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;

import java.util.Date;
import java.util.List;

public class PlayerBookAnAppointmentViewModel extends ViewModel {
    private Coach coach;
    private Date date;
    private String time;
    private String location;

//    private static MutableLiveData<List<Coach>> coaches;
//
//    public LiveData<List<Coach>> GetCoaches(Context context) {
//        if (coaches == null) {
//            coaches = new MutableLiveData<List<Coach>>();
//            this.context = context;
//            loadCoaches();
//        }
//        return coaches;
//    }

    public void SetAppointmentCoachInformation(Coach c) {
        coach = c;
    }

    public void SetAppointmentDateInformation(Date d) {
        date = d;
    }

    public void SetAppointmentTimeInformation(String t) {
        time = t;
    }

    public void SetAppointmentLocationInformation(String l) {
        location = l;
    }

    public Coach GetAppointmentCoachInformation() {
        return coach;
    }

    public Date GetAppointmentDateInformation() {
        return date;
    }

    public String GetAppointmentTimeInformation() {
        return time;
    }

    public String GetAppointmentLocationInformation() {
        return location;
    }
}
