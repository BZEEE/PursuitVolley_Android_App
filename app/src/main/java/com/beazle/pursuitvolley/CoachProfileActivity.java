package com.beazle.pursuitvolley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CoachProfileActivity extends AppCompatActivity {

    private ScheduleFragment scheduleFragment;
    private BioFragment bioFragment;
    private PaymentSettingsFragment paymentSettingsFragment;
    private Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);

        scheduleFragment = new ScheduleFragment();
        bioFragment = new BioFragment();
        paymentSettingsFragment = new PaymentSettingsFragment();

//        NavigationUI.setupWithNavController(bottomNavView,
//                Navigation.findNavController(this, R.id.nav_host_fragment))
        BottomNavigationView bottomNavigationView = findViewById(R.id.coachProfileBottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // set schedule fragment as the defualt fragment
        getSupportFragmentManager().beginTransaction().replace(
                R.id.coachProfileViewContainer, scheduleFragment).commit();

//        signout = findViewById(R.id.signout);
//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                startActivity(new Intent(this, CoachLoginActivity.class));
//                finish();
//            }
//        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_schedule:
                            selectedFragment = scheduleFragment;
                            break;
                        case R.id.nav_bio:
                            selectedFragment = bioFragment;
                            break;
                        case R.id.nav_payment_settings:
                            selectedFragment = paymentSettingsFragment;
                            break;
                        default:
                            // a fragment was selected that is not a part of our app
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.coachProfileViewContainer, selectedFragment).commit();

                    return true;
                }
            };
}
