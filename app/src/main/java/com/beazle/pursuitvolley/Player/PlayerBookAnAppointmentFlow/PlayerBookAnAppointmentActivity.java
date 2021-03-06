package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.CoachSelection.CoachSelectionFragment;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.DateSelection.PlayerDateSelectionFragment;
import com.beazle.pursuitvolley.R;

public class PlayerBookAnAppointmentActivity extends AppCompatActivity {
    private static PlayerBookAnAppointmentViewModel playerBookAnAppointmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_an_appointment);

        playerBookAnAppointmentViewModel = ViewModelProviders.of(this).get(PlayerBookAnAppointmentViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CoachSelectionFragment fragment = new CoachSelectionFragment();
        fragmentTransaction.add(R.id.bookAnAppointmentflowFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public static PlayerBookAnAppointmentViewModel GetPlayerBookAnAppointmentViewModel() {
        return playerBookAnAppointmentViewModel;
    }

}
