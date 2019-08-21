package com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachAppointments.CoachScheduleAppointmentsPage;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.PlayerAppointment;
import com.beazle.pursuitvolley.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CoachScheduleView extends LinearLayout {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    ImageButton previousButton, nextButton;
    TextView currentDate;
    GridView scheduleGridView;
    private static final int MAX_CALENDER_DAYS = 42;
    Calendar calender = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    CoachScheduleGridViewAdapter coachScheduleGridViewAdapter;
    List<Date> dates = new ArrayList<>();
    List<CoachAppointment> coachAppointments = new ArrayList<>();

    public CoachScheduleView(Context context, Context context1) {
        super(context);
        this.context = context1;
    }

    public CoachScheduleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        InitializeLayout();
        SetUpCalender();

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender.add(Calendar.MONTH, -1);
                SetUpCalender();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender.add(Calendar.MONTH, 1);
                SetUpCalender();
            }
        });

        scheduleGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get all the events for that specific day, display in a new activity with a recycler view
                context.startActivity(new Intent(context, CoachScheduleAppointmentsPage.class));
            }
        });
    }

    public CoachScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.coach_schedule_layout, this);
        nextButton = view.findViewById(R.id.nextButton);
        previousButton = view.findViewById(R.id.previousButton);
        currentDate = view.findViewById(R.id.coachScheduleCurrentDate);
        scheduleGridView = view.findViewById(R.id.scheduleGridView);
    }

    private void SetUpCalender() {
        String date = dateFormat.format(calender.getTime());
        currentDate.setText(date);

        dates.clear();
        Calendar monthCalender = (Calendar) calender.clone();
        monthCalender.set(Calendar.MONTH, 1);
        int firstDayOfMonth = monthCalender.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalender.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (dates.size() < MAX_CALENDER_DAYS) {
            dates.add(monthCalender.getTime());
            monthCalender.add(Calendar.MONTH, 1);
        }

        coachScheduleGridViewAdapter = new CoachScheduleGridViewAdapter(context, dates, calender, coachAppointments);
        scheduleGridView.setAdapter(coachScheduleGridViewAdapter);

        // refersh calender data from database ()
        RefreshSchedule();
    }

    private void RefreshSchedule() {
        // get coach appointment data from cloud firestore
        // the appointment date is the document ID
        coachAppointments.clear();



    }
}
