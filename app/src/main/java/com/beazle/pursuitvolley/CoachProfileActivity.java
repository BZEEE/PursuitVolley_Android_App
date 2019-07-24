package com.beazle.pursuitvolley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class CoachProfileActivity extends AppCompatActivity {

    private static final String TAG = "CoachProfileActivityTAG";

    private Fragment currentFragment;
    private CoachScheduleFragment coachScheduleFragment;
    private CoachBioFragment bioFragment;
    private CoachPaymentSettingsFragment coachPaymentSettingsFragment;
    private Dialog signOutDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);

        coachScheduleFragment = new CoachScheduleFragment();
        bioFragment = new CoachBioFragment();
        coachPaymentSettingsFragment = new CoachPaymentSettingsFragment();

        signOutDialog = new Dialog(this);

        Toolbar myToolbar = findViewById(R.id.coachProfileToolbar);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();

//        NavigationUI.setupWithNavController(bottomNavView,
//                Navigation.findNavController(this, R.id.nav_host_fragment))
        BottomNavigationView bottomNavigationView = findViewById(R.id.coachProfileBottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // set schedule fragment as the default fragment
        currentFragment = coachScheduleFragment;
        getSupportFragmentManager().beginTransaction().replace(
                R.id.coachProfileViewContainer, coachScheduleFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.coachProfileDeleteAccount:
                // user clicked the delete account button
                // delete account from FirebaseAuth
                // remove coaches document from firestore
                FirebaseUser user = mAuth.getCurrentUser();
                DeleteUserAccount(user);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_schedule:
                            selectedFragment = coachScheduleFragment;
                            break;
                        case R.id.nav_bio:
                            selectedFragment = bioFragment;
                            break;
                        case R.id.nav_payment_settings:
                            selectedFragment = coachPaymentSettingsFragment;
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
        mAuth.signOut();
        startActivity(new Intent(this, CoachLoginActivity.class));
        // finish the activity
        finish();
    }

    private void DoNotSignOut() {
        signOutDialog.dismiss();
    }

    private void DeleteUserAccount(FirebaseUser user) {
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                            // successful delete from FirebaseAuth
                            // now delete coach document from Firestore
                            // try deleting firestore data with cloud function trigger instead
                            // if that doesnt work then do it here
                            // DeleteUserDocumentFromFirestore(user);
                            startActivity(new Intent(CoachProfileActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Log.d(TAG, "Failed to delete User Account: (CoachProfileActivity)");
                        }
                    }
                });
    }

//    private void DeleteUserDocumentFromFirestore(FirebaseUser user) {
//        mFirestore.collection("coaches").document(user.getUid())
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error deleting document", e);
//                    }
//                });
//    }

}
