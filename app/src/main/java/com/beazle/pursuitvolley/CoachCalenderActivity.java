package com.beazle.pursuitvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

public class CoachCalenderActivity extends AppCompatActivity {

    private CalendarView dateCalender;
    private Button continueButton;

    public static final String dateTAG = "CoachCalenderActivityDateTAg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_calender);

        dateCalender = findViewById(R.id.dateCalender);
        continueButton = findViewById(R.id.continueToTimeSelectionButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToTimeSelectionActivity(GetSelectedDate());
            }
        });

        SetDateBoundaries();
    }

    private void SetDateBoundaries() {
        long min = GetFirstDateOfMonth(new Date());
        long max = GetEndDate(new Date());

        dateCalender.setMinDate(min);
        dateCalender.setMaxDate(max);
    }

    public long GetFirstDateOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

    public long GetEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH+1, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

    public long GetSelectedDate() {
        return dateCalender.getDate();
    }

    public void GoToTimeSelectionActivity(long selectedDate) {
        Intent intent = new Intent(this, CoachTimeSelectionActivity.class);
        intent.putExtra(dateTAG, selectedDate);
        startActivity(intent);
    }
}
