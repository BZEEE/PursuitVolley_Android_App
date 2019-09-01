package com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.Coach.CoachSelection.CoachSelectionActivity;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class PlayerAppointmentsFragment extends Fragment {

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

        PlayerAppointmentsManager.ClearCurrentAppointmentList();

        mFirestore.collection(FirestoreTags.playerCollection).document(currentUser.getUid())
                .collection(FirestoreTags.playerCurrentAppointmentsCollection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // firestore requires at least one document to be initialized in a collection
                                // so we initialize a dummy placeholder document
                                // then show the upcoming event to the user
                                // add it to the current appointments manager
                                Map data = document.getData();
                                PlayerAppointment playerAppointment = new PlayerAppointment(
                                        (String) data.get(FirestoreTags.currentAppointmentsCoachName),
                                        (String) data.get(FirestoreTags.playerCurrentAppointmentsDate),
                                        (String) data.get(FirestoreTags.playerCurrentAppointmentsBeginTime),
                                        (String) data.get(FirestoreTags.playerCurrentAppointmentsEndTime),
                                        (String) data.get(FirestoreTags.playerCurrentAppointmentsLocation)
                                );
                                PlayerAppointmentsManager.AddAppointmentToList(playerAppointment);

                            }
                        } else {
                            // error getting documents
                            // collection might not exist yet until player makes first appointment
                        }
                    }
                });

        List<PlayerAppointment> currentAppointmentsList = PlayerAppointmentsManager.GetCurrentAppointmentsList();

        RecyclerView recyclerView = view.findViewById(R.id.playerProfileFragmentCurrentAppointmentsRecyclerView);
        PlayerAppointmentsRecyclerViewAdapter adapter = new PlayerAppointmentsRecyclerViewAdapter(getContext(), currentAppointmentsList);
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