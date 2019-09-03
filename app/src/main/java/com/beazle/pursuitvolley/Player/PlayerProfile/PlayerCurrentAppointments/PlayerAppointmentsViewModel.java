package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerCurrentAppointments;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerAppointmentsViewModel extends ViewModel {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    private MutableLiveData<List<PlayerAppointment>> playerAppointmentsData;

    public LiveData<List<PlayerAppointment>> GetData() {
        if (playerAppointmentsData == null) {
            playerAppointmentsData = new MutableLiveData<List<PlayerAppointment>>();
            LoadPlayerAppointmentsData();
        }
        return playerAppointmentsData;
    }

    private void LoadPlayerAppointmentsData() {
        // load data asynchronously for player's appointments from firebase

        FirebaseUser currentUser = mAuth.getCurrentUser();

        mFirestore.collection(FirestoreTags.playerCollection).document(currentUser.getUid())
                .collection(FirestoreTags.playerCurrentAppointmentsCollection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                // we may want to cache the data for both (player) and (coach) at certain points though
                                List<PlayerAppointment> appointmentList = new ArrayList<PlayerAppointment>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // firestore requires at least one document to be initialized in a collection
                                    // so we initialize a dummy placeholder document
                                    // then show the upcoming event to the user
                                    // add it to the current appointments manager
                                    Map appointment = document.getData();
                                    PlayerAppointment playerAppointment = new PlayerAppointment(
                                            (String) appointment.get(FirestoreTags.currentAppointmentsCoachName),
                                            (String) appointment.get(FirestoreTags.playerCurrentAppointmentsDate),
                                            (String) appointment.get(FirestoreTags.playerCurrentAppointmentsBeginTime),
                                            (String) appointment.get(FirestoreTags.playerCurrentAppointmentsEndTime),
                                            (String) appointment.get(FirestoreTags.playerCurrentAppointmentsLocation)
                                    );
                                    appointmentList.add(playerAppointment);
                                }

                                playerAppointmentsData.setValue(appointmentList);
                            } else {
                                // no documents in collection
                                // task.getResult() is null
                            }
                        } else {
                            // error getting documents (current appointment booking for players)
                            // collection might not exist yet until player makes first appointment
                        }
                    }
                });
    }
}
