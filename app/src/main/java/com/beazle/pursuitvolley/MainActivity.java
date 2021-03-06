package com.beazle.pursuitvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beazle.pursuitvolley.Coach.CoachAuthentication.CoachLoginActivity;
import com.beazle.pursuitvolley.Player.PlayerAuthentication.PlayerLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // startActivity(new Intent(this, PlayerProfileActivity.class));

        TextView coachPortalText = findViewById(R.id.coachPortalClickableText);
        Button bookAnAppointmentButton = findViewById(R.id.bookAnAppointmentButton);

        coachPortalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToCoachPortal();
            }
        });

        bookAnAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToPlayerSignInPage();
            }
        });
    }

    private void GoToCoachPortal() {
        startActivity(new Intent(this, CoachLoginActivity.class));
    }

    private void GoToPlayerSignInPage() {

        // ***** actually need to go to PlayerLoginActivity.class
        // startActivity(new Intent(this, CoachSelectionActivity.class));
        startActivity(new Intent(this, PlayerLoginActivity.class));
    }
}
