package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.CoachSelection.CoachSelectionFragment;
import com.beazle.pursuitvolley.R;

public class BookAnAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_an_appointment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CoachSelectionFragment fragment = new CoachSelectionFragment();
        fragmentTransaction.add(R.id.bookAnAppointmentflowFragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
