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
import com.beazle.pursuitvolley.DateFormatter.DateFormatter;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.PlayerBookAnAppointmentActivity;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.PlayerBookAnAppointmentViewModel;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PlayerDateSelectionScheduleView extends LinearLayout {

    ImageButton previousButton, nextButton;
    TextView currentDate;
    GridView scheduleGridView;
    private static final int MAX_CALENDER_DAYS = 35;
    Calendar calender = Calendar.getInstance(Locale.ENGLISH);
    Context context;

    PlayerDateSelectionScheduleViewAdapter playerDateSelectionScheduleViewAdapter;
    List<Date> dates = new ArrayList<>();
    List<Integer> appointmentDatesCountForCurrentMonth = new ArrayList<>();
    Map<String, Object> allAvailableAppointmentDates = new HashMap<>();

    public PlayerDateSelectionScheduleView(Context context) {
        super(context);
        this.context = context;
    }

    public PlayerDateSelectionScheduleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

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
                Date date = dates.get(position);
                PlayerDateSelectionFragment.GoToPlayerTimeSelectionFragment(date);
            }
        });
    }

    public PlayerDateSelectionScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.player_date_selection_schedule_layout, this);
        previousButton = view.findViewById(R.id.playerDateSelectionSchedulePreviousButton);
        nextButton = view.findViewById(R.id.playerDateSelectionScheduleNextButton);
        currentDate = view.findViewById(R.id.playerDateSelectionScheduleCurrentDate);
        scheduleGridView = view.findViewById(R.id.playerDateSelectionScheduleGridView);
    }

    private void SetUpCalender() {
        // sync schedule data to calender from view model: PlayerDateSelectionViewModel
        String date = DateFormatter.playerScheduleSelectionDateFormat.format(calender.getTime());
        currentDate.setText(date);

        dates.clear();
        Calendar monthCalender = (Calendar) calender.clone();
        monthCalender.set(Calendar.MONTH, 1);
        int firstDayOfMonth = monthCalender.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalender.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (dates.size() < MAX_CALENDER_DAYS) {
            Date day = monthCalender.getTime();
            dates.add(day);
            // sync appointment data to calender dates
            // decide whether key (Date) is in String format
            // get appointment time slots for current day of month
            Map appointmentsForCurrentDay = (Map) allAvailableAppointmentDates.get(day);
            int appointmentCountForCurrentDay = 0;
            for (Object value : appointmentsForCurrentDay.values()) {
                Map appointmentData = (Map) value;
                if (appointmentData.get("booked") == "false") {
                    appointmentCountForCurrentDay += 1;
                }
            }
            appointmentDatesCountForCurrentMonth.add(appointmentCountForCurrentDay);
            monthCalender.add(Calendar.MONTH, 1);
        }

        playerDateSelectionScheduleViewAdapter = new PlayerDateSelectionScheduleViewAdapter(context, calender, dates, appointmentDatesCountForCurrentMonth);
        scheduleGridView.setAdapter(playerDateSelectionScheduleViewAdapter);
    }

    // (1) adapter needs to sync data to current month when data changes in real-time database
    // just pass data from view model
    // (2) dont need second case

    public void SyncAvailableAppointmentDates(Map<String, Object> availableAppointments) {
        allAvailableAppointmentDates = availableAppointments;
    }
}
