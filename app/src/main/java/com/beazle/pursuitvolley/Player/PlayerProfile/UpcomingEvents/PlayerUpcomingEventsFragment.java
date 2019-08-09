package com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.Coach.CoachSelection.CoachSelectionRecyclerViewAdapter;
import com.beazle.pursuitvolley.FirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.CurrentAppointment;
import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.CurrentAppointmentsManager;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class PlayerUpcomingEventsFragment extends Fragment {

    private FirebaseFirestore mFirestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_upcoming_events, container, false);

        mFirestore = FirebaseFirestore.getInstance();

        UpcomingEventsManager.ClearUpcomingEventsList();

        mFirestore.collection(FirestoreTags.upcomingEventsCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!document.getId().equals(FirestoreTags.documentPlaceholderTAG)) {
                                    // firestore requires at least one document to be initialized in a collection
                                    // so we initialize a dummy placeholder document
                                    // then show the upcoming event to the user
                                    // add it to the upcoming events manager
                                    Map data = document.getData();
                                    UpcomingEvent event = new UpcomingEvent(
                                            (String) data.get(FirestoreTags.upcomingEventTitle),
                                            (String) data.get(FirestoreTags.upcomingEventDate),
                                            (String) data.get(FirestoreTags.upcomingEventBeginTime),
                                            (String) data.get(FirestoreTags.upcomingEventEndTime),
                                            (String) data.get(FirestoreTags.upcomingEventLocation)
                                    );
                                    UpcomingEventsManager.AddEventToList(event);
                                }
                            }
                        } else {
                            // error getting documents
                        }
                    }
                });

        List<UpcomingEvent> upcomingEventsList = UpcomingEventsManager.GetUpcomingEventsList();

        RecyclerView recyclerView = view.findViewById(R.id.playerProfileFragmentUpcomingEventsRecyclerView);
        UpcomingEventsRecyclerViewAdapter adapter = new UpcomingEventsRecyclerViewAdapter(getContext(), upcomingEventsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
