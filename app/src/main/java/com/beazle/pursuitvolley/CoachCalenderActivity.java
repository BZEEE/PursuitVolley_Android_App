package com.beazle.pursuitvolley;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
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

        Intent receivedIntent = getIntent();
        final String coachId = receivedIntent.getStringExtra(CoachSelectionRecyclerViewAdapter.CoachCardViewHolder.coachSelectionViewHolderTag);

        dateCalender = findViewById(R.id.dateCalender);
        continueButton = findViewById(R.id.continueToTimeSelectionButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToTimeSelectionActivity(coachId, GetSelectedDate());
            }
        });

        SetDateBoundaries();
    }

    private void SetDateBoundaries() {
        long min = GetFirstDateOfMonth();
        long max = GetEndDate();

        dateCalender.setMinDate(min);
        dateCalender.setMaxDate(max);
    }

    public long GetFirstDateOfMonth(){
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    public long GetEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 14);
        return cal.getTimeInMillis();
    }

    public long GetSelectedDate() {
        return dateCalender.getDate();
    }

    public void GoToTimeSelectionActivity(String coachId, long selectedDate) {
        Intent intent = new Intent(this, CoachTimeSelectionActivity.class);
        intent.putExtra(CoachSelectionRecyclerViewAdapter.CoachCardViewHolder.coachSelectionViewHolderTag, coachId);
        intent.putExtra(dateTAG, selectedDate);
        startActivity(intent);
    }
}
