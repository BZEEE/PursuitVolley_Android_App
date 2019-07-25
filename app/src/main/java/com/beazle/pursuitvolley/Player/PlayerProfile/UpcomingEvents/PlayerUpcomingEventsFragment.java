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
import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.CurrentAppointment;
import com.beazle.pursuitvolley.R;

import java.util.List;

public class PlayerUpcomingEventsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_upcoming_events, container, false);

        List<UpcomingEvent> upcomingEventsList = UpcomingEventsManager.GetUpcomingEventsList();

        RecyclerView recyclerView = view.findViewById(R.id.playerProfileFragmentUpcomingEventsRecyclerView);
        UpcomingEventsRecyclerViewAdapter adapter = new UpcomingEventsRecyclerViewAdapter(getContext(), upcomingEventsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
