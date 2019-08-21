package com.beazle.pursuitvolley.Player.PlayerProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.PlayerAppointmentsFragment;
import com.beazle.pursuitvolley.Player.PlayerProfile.UpcomingEvents.PlayerUpcomingEventsFragment;
import com.beazle.pursuitvolley.R;
import com.google.android.material.tabs.TabLayout;

public class PlayerProfileActivity extends AppCompatActivity {

    private PagerAdapter playerProfilePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        playerProfilePageAdapter = new PlayerProfileTabsAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.playerProfileViewPager);
        SetupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.playerProfileTabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void SetupViewPager(ViewPager viewPager) {
        PlayerProfileTabsAdapter adapter = new PlayerProfileTabsAdapter(getSupportFragmentManager());
        adapter.AddFragment(new PlayerAppointmentsFragment(), getResources().getString(R.string.player_current_appointments_tab_text));
        adapter.AddFragment(new PlayerUpcomingEventsFragment(), getResources().getString(R.string.player_upcoming_events_tab_text));
        viewPager.setAdapter(adapter);
    }
}
