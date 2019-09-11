package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.DateSelection;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.RealtimeDatabaseTags.RealtimeDatabaseTags;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDateSelectionScheduleViewModel extends ViewModel {

    private FirebaseDatabase mRealtimeDatabase = FirebaseDatabase.getInstance();

    // should represent the data of the current month
    // schedule view reacts to the mutable live data and is adjusted
    private static MutableLiveData<Map<String, Object>> availableAppointmentDates;

    public LiveData<Map<String, Object>> GetAvailableAppointmentData(Coach coach) {
        if (availableAppointmentDates == null) {
            availableAppointmentDates = new MutableLiveData<Map<String, Object>>();
            LoadAvailableAppointmentDates(coach);
        }
        return availableAppointmentDates;
    }

    private void LoadAvailableAppointmentDates(Coach coach) {
        DatabaseReference ref = mRealtimeDatabase.getReference().child(RealtimeDatabaseTags.coachesCollecion)
                .child(coach.GetUniqueId()).child(RealtimeDatabaseTags.coachesAppointmentsCollection);
        // attach listener to refernce
        // is the listener destroyed when the fragment is destroyed
        // or do we have to manually detach before it iss destroyed
        // will create never ending loop if we are using a listener for just writing to real-time databse
        // only use data listeners when updating UI, use listener to update view models in real-time
        // use transactions to ensure atomic actions of book an appointment flow
        // transactions help prevent concurrency when writing, deleting, and updating realtime-database

        ValueEventListener timeSlotsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Appointments object and use the values to update the UI
                Map<String, Object> appointmentsHashMap = new HashMap<String,Object>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey() != null && snapshot.getValue() != null) {
                        // retrieve all appointments in realtime database
                        // update view Model mutable data with appointments
                        // attach listener so that view model is updated every time
                        appointmentsHashMap.put(snapshot.getKey(),snapshot.getValue());
                    } else {
                        Log.d(DebugTags.DebugTAG, "PlayerDateSelectionScheduleView: snapshot key or value is null");
                    }
                }
                availableAppointmentDates.setValue(appointmentsHashMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(DebugTags.DebugTAG, "PlayerDateScheduleViewModel loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        // onDataChange() called once when it is attached
        // then called for every time after that when data changes
        ref.addValueEventListener(timeSlotsListener);
    }
}
