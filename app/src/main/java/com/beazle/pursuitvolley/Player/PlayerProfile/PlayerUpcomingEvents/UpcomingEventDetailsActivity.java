package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerUpcomingEvents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.beazle.pursuitvolley.R;

public class UpcomingEventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_event_details);

        Intent intent = getIntent();

        TextView upcomingEventTitle = findViewById(R.id.upcomingEventTitleValue);
        TextView upcomingEventDate = findViewById(R.id.upcomingEventDateValue);
        TextView upcomingEventLocation = findViewById(R.id.upcomingEventLocationValue);

        upcomingEventTitle.setText(intent.getStringExtra("upcomingEventTitle"));
        upcomingEventDate.setText(intent.getStringExtra("upcomingEventDate"));
        upcomingEventLocation.setText(intent.getStringExtra("upcomingEventLocation"));

    }
}
