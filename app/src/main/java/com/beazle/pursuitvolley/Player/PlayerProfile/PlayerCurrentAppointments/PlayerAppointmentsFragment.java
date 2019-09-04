package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerCurrentAppointments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.R;
import com.google.firebase.auth.FirebaseUser;

public class PlayerAppointmentsFragment extends Fragment {

    private FirebaseUser currentUser;

    private PlayerAppointmentsViewModel playerAppointmentViewModel;

    public static PlayerAppointmentsFragment newInstance() {
        return new PlayerAppointmentsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_current_appointments, container, false);

        //apply loaded view model data to fragment UI
        playerAppointmentViewModel = ViewModelProviders.of(this).get(PlayerAppointmentsViewModel.class);

        Button bookAnAppointmentButton = view.findViewById(R.id.playerProfileFragmentCurrentAppointmentsButton);
        bookAnAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToCoachSelectionActivity();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.playerProfileFragmentCurrentAppointmentsRecyclerView);
        PlayerAppointmentsRecyclerViewAdapter adapter = new PlayerAppointmentsRecyclerViewAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        playerAppointmentViewModel.GetData().observe(this, playerAppointmentData -> {
            // let the player appointment Ui be an observer of the view model
            // so when the view model data changes, the UI is updated
            adapter.SetPlayerAppointmentData(playerAppointmentData);
        });

        return view;
    }

    private void GoToCoachSelectionActivity() {
        startActivity(new Intent(getActivity(), CoachSelectionActivity.class));
    }
}
