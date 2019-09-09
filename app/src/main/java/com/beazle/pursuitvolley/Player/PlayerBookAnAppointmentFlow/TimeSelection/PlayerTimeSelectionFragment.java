package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.TimeSelection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.Checkout.PlayerAppointmentCheckoutFragment;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.DateSelection.PlayerDateSelectionFragment;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.PlayerBookAnAppointmentActivity;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.PlayerBookAnAppointmentViewModel;
import com.beazle.pursuitvolley.R;

public class PlayerTimeSelectionFragment extends Fragment {

    private static FragmentManager fragmentManager;
    private static PlayerBookAnAppointmentViewModel playerBookAnAppointmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_available_time_selection_schedule, container, false);
        playerBookAnAppointmentViewModel = PlayerBookAnAppointmentActivity.GetPlayerBookAnAppointmentViewModel();

        fragmentManager = getFragmentManager();
        return view;
    }

    public static void GoToPlayerCheckoutFragment(String time) {
        // set data from selected coach to view model, so we have a receipt of information about the appointment
        playerBookAnAppointmentViewModel.SetAppointmentTimeInformation(time);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerAppointmentCheckoutFragment fragment = new PlayerAppointmentCheckoutFragment();
        fragmentTransaction.add(R.id.bookAnAppointmentflowFragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
