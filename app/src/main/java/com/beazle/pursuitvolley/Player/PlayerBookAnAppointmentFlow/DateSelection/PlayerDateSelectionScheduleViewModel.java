package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.DateSelection;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PlayerDateSelectionScheduleViewModel extends ViewModel {

    private FirebaseFirestore mFirestore;

    // should represent the data of the current month
    // schedule view reacts to the mutable live data and is adjusted
    private static MutableLiveData<List<String>> currentMonthData;

    public LiveData<List<String>> GetMonthData(Date date) {
        if (currentMonthData == null) {
            currentMonthData = new MutableLiveData<List<String>>();
            LoadAvailableTimeSlots(date);
        }
        return currentMonthData;
    }

    public void LoadAvailableTimeSlots(Coach coach, Date date) {
        mFirestore = FirebaseFirestore.getInstance();
        // Fetch Coaches from Firestore
        mFirestore.collection(FirestoreTags.coachCollection).document(coach.GetUniqueId())
                .collection(FirestoreTags.coachAppointmentsCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map data = document.getData();
                                }

                            } else {
                                // task.getResult() is null
                            }
                        } else {
                            // task is unsuccessful
                        }
                    }
                });
    }
}
