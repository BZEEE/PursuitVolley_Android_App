package com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.Coach.CoachSelection.CoachSelectionActivity;
import com.beazle.pursuitvolley.Coach.CoachSelection.CoachSelectionRecyclerViewAdapter;
import com.beazle.pursuitvolley.FirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents.UpcomingEvent;
import com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents.UpcomingEventsManager;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class PlayerCurrentAppointmentsFragment extends Fragment {

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_current_appointments, container, false);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        CurrentAppointmentsManager.ClearCurrentAppointmentList();

        mFirestore.collection(FirestoreTags.playerCollection).document(currentUser.getUid())
                .collection(FirestoreTags.playerCurrentAppointmentsCollection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!document.getId().equals(FirestoreTags.documentPlaceholderTAG)) {
                                    // firestore requires at least one document to be initialized in a collection
                                    // so we initialize a dummy placeholder document
                                    // then show the upcoming event to the user
                                    // add it to the current appointments manager
                                    Map data = document.getData();
                                    CurrentAppointment appointment = new CurrentAppointment(
                                            (String) data.get(FirestoreTags.currentAppointmentsCoachName),
                                            (String) data.get(FirestoreTags.playerCurrentAppointmentsDate),
                                            (String) data.get(FirestoreTags.playerCurrentAppointmentsBeginTime),
                                            (String) data.get(FirestoreTags.playerCurrentAppointmentsEndTime),
                                            (String) data.get(FirestoreTags.playerCurrentAppointmentsLocation)
                                    );
                                    CurrentAppointmentsManager.AddAppointmentToList(appointment);
                                }
                            }
                        } else {
                            // error getting documents
                        }
                    }
                });

        List<CurrentAppointment> currentAppointmentsList = CurrentAppointmentsManager.GetCurrentAppointmentsList();

        RecyclerView recyclerView = view.findViewById(R.id.playerProfileFragmentCurrentAppointmentsRecyclerView);
        CurrentAppointmentsRecyclerViewAdapter adapter = new CurrentAppointmentsRecyclerViewAdapter(getContext(), currentAppointmentsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Button bookAnAppointmentButton = view.findViewById(R.id.playerProfileFragmentCurrentAppointmentsButton);
        bookAnAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToCoachSelectionActivity();
            }
        });

        return view;
    }

    private void GoToCoachSelectionActivity() {
        startActivity(new Intent(getActivity(), CoachSelectionActivity.class));
    }
}
