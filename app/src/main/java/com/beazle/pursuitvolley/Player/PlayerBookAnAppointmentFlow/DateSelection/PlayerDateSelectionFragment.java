package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.DateSelection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.PlayerBookAnAppointmentActivity;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.PlayerBookAnAppointmentViewModel;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.TimeSelection.PlayerTimeSelectionFragment;
import com.beazle.pursuitvolley.R;

import java.util.Date;

public class PlayerDateSelectionFragment extends Fragment {

    private static FragmentManager fragmentManager;
    private PlayerDateSelectionScheduleViewModel playerDateSelectionScheduleViewModel;
    private static PlayerBookAnAppointmentViewModel playerBookAnAppointmentViewModel;
    PlayerDateSelectionScheduleView availableDatesSchedule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_available_dates_selection_schedule, container, false);

        availableDatesSchedule = view.findViewById(R.id.playerDateSelectionScheduleView);

        fragmentManager = getFragmentManager();
        playerDateSelectionScheduleViewModel = ViewModelProviders.of(this).get(PlayerDateSelectionScheduleViewModel.class);
        playerBookAnAppointmentViewModel = PlayerBookAnAppointmentActivity.GetPlayerBookAnAppointmentViewModel();

        // PlayerDateSelectionScheduleViewAdapter playerDateSelectionScheduleViewAdapter = new PlayerDateSelectionScheduleViewAdapter(context, dates, calender, availableSlots);
        // scheduleGridView.setAdapter(playerDateSelectionScheduleViewAdapter);

        // Get access to date schedule view model
        Coach selectedCoach = playerBookAnAppointmentViewModel.GetAppointmentCoachInformation();
        // load coach available dates data in schedule view
        playerDateSelectionScheduleViewModel.GetAvailableAppointmentData(selectedCoach).observe(this, dates -> {
            availableDatesSchedule.SyncAvailableAppointmentDates(dates);
        });

        // populate grid vew with selected coach available dates data
        // have schedule view observe the model

        return view;
    }

    public static void GoToPlayerTimeSelectionFragment(Date date) {
        // set data from selected coach to view model, so we have a receipt of information about the appointment
        playerBookAnAppointmentViewModel.SetAppointmentDateInformation(date);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerTimeSelectionFragment fragment = new PlayerTimeSelectionFragment();
        fragmentTransaction.add(R.id.bookAnAppointmentflowFragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
