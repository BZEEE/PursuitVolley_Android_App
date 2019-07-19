package com.beazle.pursuitvolley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CoachProfileActivity extends AppCompatActivity {

    private Fragment currentFragment;
    private ScheduleFragment scheduleFragment;
    private BioFragment bioFragment;
    private PaymentSettingsFragment paymentSettingsFragment;
    private Dialog signOutDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);

        scheduleFragment = new ScheduleFragment();
        bioFragment = new BioFragment();
        paymentSettingsFragment = new PaymentSettingsFragment();

        signOutDialog = new Dialog(this);

        mAuth = FirebaseAuth.getInstance();

//        NavigationUI.setupWithNavController(bottomNavView,
//                Navigation.findNavController(this, R.id.nav_host_fragment))
        BottomNavigationView bottomNavigationView = findViewById(R.id.coachProfileBottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // set schedule fragment as the default fragment
        currentFragment = scheduleFragment;
        getSupportFragmentManager().beginTransaction().replace(
                R.id.coachProfileViewContainer, scheduleFragment).commit();
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
                        case R.id.nav_log_out:
                            selectedFragment = currentFragment;
                            ShowSignOutDialog();

                        default:
                            // a fragment was selected that is not a part of our app
                            Toast.makeText(CoachProfileActivity.this, "fragment selected is not a part of this app", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.coachProfileViewContainer, selectedFragment).commit();

                    return true;
                }
            };

    private void ShowSignOutDialog() {
        signOutDialog.setContentView(R.layout.coach_sign_out_pop_up);
        Button yesButton = findViewById(R.id.signOutYesButton);
        Button noButton = findViewById(R.id.signOutNoButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoNotSignOut();
            }
        });

        signOutDialog.show();

    }

    private void SignOut() {
        // perform sign out operations
        // sign out user from firebase
        signOutDialog.dismiss();
        // mAuth.signOut();
        startActivity(new Intent(this, CoachLoginActivity.class));
        // finish the activity
        finish();
    }

    private void DoNotSignOut() {
        signOutDialog.dismiss();
    }
}
