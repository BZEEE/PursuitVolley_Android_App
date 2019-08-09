package com.beazle.pursuitvolley.Coach.CoachSelection;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirestoreTags.FirestoreTags;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class CoachManager extends ViewModel {

    private FirebaseFirestore mFirestore;

    private static MutableLiveData<List<Coach>> coaches;
    private final MutableLiveData<Coach> selectedCoach = new MutableLiveData<Coach>();

    public void selectCoach(Coach coach) {
        selectedCoach.setValue(coach);
    }

    public LiveData<Coach> getSelectedCoach() {
        return selectedCoach;
    }

    public LiveData<List<Coach>> GetCoaches() {
        if (coaches == null) {
            coaches = new MutableLiveData<List<Coach>>();
            loadCoaches();
        }
        return coaches;
    }

    private void loadCoaches() {
        mFirestore = FirebaseFirestore.getInstance();
        // Do an asynchronous operation to fetch users.
        mFirestore.collection(FirestoreTags.coachCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Coach> coachList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map coachData = document.getData();
                                String coachUniqueId = document.getId();
                                String coachName = (String) coachData.get(FirestoreTags.coachDocumentFullname);
                                String coachAge = (String) coachData.get(FirestoreTags.coachDocumentAge);
                                String coachBio = (String) coachData.get(FirestoreTags.coachDocumentBio);
                                String coachLocation = (String) coachData.get(FirestoreTags.coachDocumentLocation);
                                Coach coach = new Coach(coachUniqueId, coachName, coachAge, coachLocation, coachBio);
                                coachList.add(coach);
                                Log.d(DebugTags.DebugTAG, document.getId() + " => " + document.getData());
                            }
                            coaches.postValue(coachList);
                        } else {
                            Log.d(DebugTags.DebugTAG, "Error getting documents: ", task.getException());
                            Log.d(DebugTags.DebugTAG, "cannot show coaches to player, handles errors accordingly, show certain ones to user. Eg: no internet connection, so cant connect to firebase");
                        }
                    }
                });
    }
}
