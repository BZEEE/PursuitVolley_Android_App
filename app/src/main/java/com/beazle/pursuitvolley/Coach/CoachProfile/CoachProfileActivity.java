package com.beazle.pursuitvolley.Coach.CoachProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.beazle.pursuitvolley.Coach.CoachAuthentication.CoachLoginActivity;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.MainActivity;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CoachProfileActivity extends AppCompatActivity {

    private Fragment currentFragment;
    private CoachScheduleFragment coachScheduleFragment;
    private CoachBioFragment bioFragment;
    private CoachPaymentSettingsFragment coachPaymentSettingsFragment;
    private PopupWindow signOutWindow;
    private PopupWindow deleteAccountWindow;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseUser currentCoach;
    private RelativeLayout coachProfileActivityRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);

        coachScheduleFragment = new CoachScheduleFragment();
        bioFragment = new CoachBioFragment();
        coachPaymentSettingsFragment = new CoachPaymentSettingsFragment();

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        currentCoach = mAuth.getCurrentUser();

//        NavigationUI.setupWithNavController(bottomNavView,
//                Navigation.findNavController(this, R.id.nav_host_fragment))
        BottomNavigationView bottomNavigationView = findViewById(R.id.coachProfileBottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // get layout reference for layout pop-up window
        coachProfileActivityRelativeLayout = findViewById(R.id.coachProfileActivityRelativeLayout);

        // set schedule fragment as the default fragment
        currentFragment = coachScheduleFragment;
        getSupportFragmentManager().beginTransaction().replace(
                R.id.coachProfileViewContainer, coachScheduleFragment).commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.coach_profile_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.coachProfileDeleteAccount:
                // user clicked the delete account button
                // remove coaches document from firestore
                // mAuth.signOut();
                ShowAccountDeleteDialog();
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
                            currentFragment = coachScheduleFragment;
                            break;
                        case R.id.nav_bio:
                            selectedFragment = bioFragment;
                            currentFragment = bioFragment;
                            break;
                        case R.id.nav_payment_settings:
                            selectedFragment = coachPaymentSettingsFragment;
                            currentFragment = coachPaymentSettingsFragment;
                            break;
                        case R.id.nav_log_out:
                            selectedFragment = currentFragment;
                            ShowSignOutDialog();
                            break;

                        default:
                            // a fragment was selected that is not a part of our app
                            Toast.makeText(CoachProfileActivity.this, "fragment selected is not a part of this app", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    Log.d(DebugTags.DebugTAG, "sign out bruh");
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.coachProfileViewContainer, selectedFragment).commit();

                    return true;
                }
            };

    private void ShowSignOutDialog() {

        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) CoachProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.coach_sign_out_pop_up,null);

        //instantiate popup window
        signOutWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        signOutWindow.showAtLocation(coachProfileActivityRelativeLayout, Gravity.CENTER, 0, 0);
        Button yesButton = findViewById(R.id.signOutYesButton);
        Button noButton = findViewById(R.id.signOutNoButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                signOutWindow.dismiss();
                Toast.makeText(CoachProfileActivity.this, "successfully signed out", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(this, CoachLoginActivity.class));
                // finish the activity
                finish();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutWindow.dismiss();
            }
        });

    }

    private void ShowAccountDeleteDialog() {

        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) CoachProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.coach_account_delete_pop_up,null);

        //instantiate popup window
        deleteAccountWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        deleteAccountWindow.showAtLocation(coachProfileActivityRelativeLayout, Gravity.CENTER, 0, 0);
        Button deleteAccountButton = findViewById(R.id.deleteAccountYesButton);
        Button cancelButton = findViewById(R.id.deleteAccountCancelButton);

        EditText deletionCodeEditText = findViewById(R.id.deleteCodeEditText);

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deletionCode = deletionCodeEditText.getText().toString();
                CheckDeletionCode(deletionCode);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountWindow.dismiss();
            }
        });

    }

    private void CheckDeletionCode(String accountDeletionCode) {
        DocumentReference docRef = mFirestore.collection(FirestoreTags.adminCodesCollection).document(FirestoreTags.coachAccountDeletionCode);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // get activation from document data
                        String codeFromFirebase = (String) document.getData().get(FirestoreTags.codeKey);
                        if (codeFromFirebase.equals(accountDeletionCode)) {
                            DeleteCoachAccount();
                        } else {
                            Toast.makeText(CoachProfileActivity.this, "wrong deletion code", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(DebugTags.DebugTAG, "Admin codes document does not exist");
                    }
                } else {
                    Log.d(DebugTags.DebugTAG, "document get() failed with ", task.getException());
                }
            }
        });
    }

    private void DeleteCoachAccount() {
        currentCoach.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(DebugTags.DebugTAG, "User account deleted.");
                            // successful delete from FirebaseAuth
                            // now delete coach document from Firestore
                            // NOTE: Deleting a document does not delete its subcollection, have to do this manually or recursively
                            deleteAccountWindow.dismiss();
                            mAuth.signOut();
                            Toast.makeText(CoachProfileActivity.this, "successfully deleted account", Toast.LENGTH_SHORT).show();
                            // DeleteUserDocumentFromFirestore(user);
                            // startActivity(new Intent(CoachProfileActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Log.d(DebugTags.DebugTAG, "Failed to delete User Account: (CoachProfileActivity)");
                        }
                    }
                });
    }
}
