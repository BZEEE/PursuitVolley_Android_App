package com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.beazle.pursuitvolley.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CoachScheduleView extends LinearLayout {
    ImageButton previousButton, nextButton;
    TextView currentDate;
    GridView scheduleGridView;
    private static final int MAX_CALENDER_DAYS = 42;
    Calendar calender = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    List<Date> dates = new ArrayList<>();
    List<Events> events = new ArrayList<>();

    public CoachScheduleView(Context context, Context context1) {
        super(context);
        this.context = context1;
    }

    public CoachScheduleView(Context context, @Nullable AttributeSet attrs) {
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
    }
}
