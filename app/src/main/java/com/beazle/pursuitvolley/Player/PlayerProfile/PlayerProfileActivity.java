package com.beazle.pursuitvolley.Player.PlayerProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerBio.PlayerBioViewModel;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerCurrentAppointments.PlayerAppointmentsFragment;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerBio.PlayerBioFragment;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerCurrentAppointments.PlayerAppointmentsViewModel;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerUpcomingEvents.PlayerUpcomingEventsFragment;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerUpcomingEvents.PlayerUpcomingEventsViewModel;
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

        // get reference to viewmodels for all tab fragments
        PlayerBioViewModel playerBioViewModel = ViewModelProviders.of(this).get(PlayerBioViewModel.class);
        PlayerAppointmentsViewModel playerAppointmentViewModel = ViewModelProviders.of(this).get(PlayerAppointmentsViewModel.class);
        PlayerUpcomingEventsViewModel playerUpcomingEventsViewModel = ViewModelProviders.of(this).get(PlayerUpcomingEventsViewModel.class);
        // load data into viewmodels from firebase for all tabs
        // viewmodels exist as long as this activity exists
        playerBioViewModel.GetData();
        playerAppointmentViewModel.GetData();
        playerUpcomingEventsViewModel.GetData();

    }

    private void SetupViewPager(ViewPager viewPager) {
        PlayerProfileTabsAdapter adapter = new PlayerProfileTabsAdapter(getSupportFragmentManager());
        adapter.AddFragment(new PlayerAppointmentsFragment(), getResources().getString(R.string.player_current_appointments_tab_text));
        adapter.AddFragment(new PlayerUpcomingEventsFragment(), getResources().getString(R.string.player_upcoming_events_tab_text));
        adapter.AddFragment(new PlayerBioFragment(), getResources().getString(R.string.player_info_tab_text));
        viewPager.setAdapter(adapter);
    }
}
