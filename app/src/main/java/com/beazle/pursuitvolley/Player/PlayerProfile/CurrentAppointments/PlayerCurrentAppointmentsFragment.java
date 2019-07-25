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
import com.beazle.pursuitvolley.R;

import java.util.List;

public class PlayerCurrentAppointmentsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_current_appointments, container, false);

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
