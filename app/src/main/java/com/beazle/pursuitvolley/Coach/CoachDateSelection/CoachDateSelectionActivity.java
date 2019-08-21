package com.beazle.pursuitvolley.Coach.CoachDateSelection;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.beazle.pursuitvolley.Coach.CoachTimeSelection.CoachTimeSelectionActivity;
import com.beazle.pursuitvolley.IntentTags.IntentTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.PlayerAppointmentReceiptParcelable;
import com.beazle.pursuitvolley.R;

import java.util.Calendar;

public class CoachDateSelectionActivity extends AppCompatActivity {

    private CalendarView dateCalender;
    private Button continueButton;

    public static final String dateTAG = "CoachCalenderActivityDateTAg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_calender_selection);

        Intent receivedIntent = getIntent();
        final PlayerAppointmentReceiptParcelable receipt = (PlayerAppointmentReceiptParcelable) receivedIntent.getParcelableExtra(IntentTags.currentReceiptTAG);

        dateCalender = findViewById(R.id.dateCalender);
        continueButton = findViewById(R.id.continueToTimeSelectionButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToTimeSelectionActivity(receipt, GetSelectedDate());
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

    public void GoToTimeSelectionActivity(PlayerAppointmentReceiptParcelable receipt, long selectedDate) {
        Intent intent = new Intent(this, CoachTimeSelectionActivity.class);
        receipt.setCurrentAppointmentDate(selectedDate);
        intent.putExtra(IntentTags.currentReceiptTAG, receipt);
        startActivity(intent);
    }
}
