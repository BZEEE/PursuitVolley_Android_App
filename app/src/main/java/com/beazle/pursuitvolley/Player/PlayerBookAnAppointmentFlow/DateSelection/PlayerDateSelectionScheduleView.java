package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.DateSelection;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachAppointment;
import com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachAppointmentsPage.CoachScheduleAppointmentsPage;
import com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule.CoachScheduleGridViewAdapter;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.R;
import com.beazle.pursuitvolley.RealtimeDatabaseTags.RealtimeDatabaseTags;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlayerDateSelectionScheduleView extends LinearLayout {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mRealtimeDatabase;

    ImageButton previousButton, nextButton;
    TextView currentDate;
    GridView scheduleGridView;
    private static final int MAX_CALENDER_DAYS = 35;
    Calendar calender = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    PlayerDateSelectionScheduleViewAdapter playerDateSelectionScheduleViewAdapter;
    List<Date> dates = new ArrayList<>();
    List<Integer> availableSlots = new ArrayList<Integer>();

    public PlayerDateSelectionScheduleView(Context context) {
        super(context);
        this.context = context;
    }

    public PlayerDateSelectionScheduleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mAuth = FirebaseAuth.getInstance();
        mRealtimeDatabase = FirebaseDatabase.getInstance();

        previousButton = findViewById(R.id);
        nextButton = findViewById(R.id);
        scheduleGridView = findViewById(R.id);

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
                // player has selected a date now we retrieve available time slots for that date and that coach
            }
        });
    }

    public PlayerDateSelectionScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.coach_schedule_layout, this);
        nextButton = view.findViewById(R.id.playerDateSelectionScheduleViewNextButton);
        previousButton = view.findViewById(R.id.playerDateSelectionScheduleViewPreviousButton);
        currentDate = view.findViewById(R.id.playerDateSelectionScheduleCurrentDate);
        scheduleGridView = view.findViewById(R.id.playerDateSelectionScheduleGridView);
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

        playerDateSelectionScheduleViewAdapter = new PlayerDateSelectionScheduleViewAdapter(context, dates, calender, availableSlots);
        scheduleGridView.setAdapter(playerDateSelectionScheduleViewAdapterr);

        // refersh calender data from database ()
        RefreshSchedule();
    }

    private void RefreshSchedule() {
        // get coach appointment data from cloud firestore
        // the appointment date is the document ID
        coachAppointments.clear();

        String coachUniqueId = mAuth.getCurrentUser().getUid();

        DatabaseReference coacesRef = mRealtimeDatabase.getReference().child(RealtimeDatabaseTags.coachesCollecion).child(coachUniqueId);

        coacesRef.ge

        coacesRef.child(RealtimeDatabaseTags.coachDataFullname).setValue(fullname)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Log.d(DebugTags.DebugTAG, "Successfully updated coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataFullname);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Log.d(DebugTags.DebugTAG, "Failed to update coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataFullname);
                    }
                });



    }
}
