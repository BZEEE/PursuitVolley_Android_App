package com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachAppointments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachAppointment;
import com.beazle.pursuitvolley.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CoachScheduleAppointmentsPage extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private RecyclerView eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_schedule_appointments_page);

        // there should be a recycler view in this activity
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // get appointments for a specific coach on specific day
        List coachAppointments = new ArrayList<CoachAppointment>();

        eventsList = findViewById(R.id.coachScheduleEventsPageRecyclerView);
        eventsList.setLayoutManager(new LinearLayoutManager(this));
        eventsList.setAdapter( new CoachScheduleAppointmentsPageRecyclerViewAdapter(this, ));


//        GetScheduleForSpecificcDay();

    }
}
