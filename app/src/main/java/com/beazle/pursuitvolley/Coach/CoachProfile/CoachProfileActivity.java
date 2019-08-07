package com.beazle.pursuitvolley.Coach.CoachProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.beazle.pursuitvolley.Coach.CoachLoginActivity;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.MainActivity;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
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
    private PopupWindow signOutWindow;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
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
                // delete account from FirebaseAuth
                // remove coaches document from firestore
                FirebaseUser user = mAuth.getCurrentUser();
                mAuth.signOut();
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
                SignOut();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoNotSignOut();
            }
        });

    }

    private void SignOut() {
        // perform sign out operations
        // sign out user from firebase
        signOutWindow.dismiss();
        mAuth.signOut();
        Toast.makeText(this, "successfully signed out", Toast.LENGTH_SHORT).show();
        // startActivity(new Intent(this, CoachLoginActivity.class));
        // finish the activity
        finish();
    }

    private void DoNotSignOut() {
        signOutWindow.dismiss();
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
                            // NOTE: Deleting a document does not delete its subcollection, have to do this manually or recursively

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
//        // loop through and delete all documents in the subcollection "booking", which will then delete the subcollection
//        // once all the documents are deleted within that subcollection
//        mFirestore.collection("coaches").document(user.getUid()).collection("bookings")
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        // delete document
//                        document.
//                    }
//                } else {
//                    Log.d(TAG, "Error Getting Documents", task.getException());
//                }
//            }
//        });
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

    // ********************************************
    // https://stackoverflow.com/questions/50220287/how-do-you-iterate-through-documents-in-a-sub-collection-within-a-transaction-fo

//    private void deleteCollection(final CollectionReference collection, Executor executor) {
//        Tasks.call(executor, new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                int batchSize = 10;
//                Query query = db.collection("job_skills").whereEqualTo("job_id", job_id);
//                List<DocumentSnapshot> deleted = deleteQueryBatch(query);
//
//                while (deleted.size() >= batchSize) {
//                    DocumentSnapshot last = deleted.get(deleted.size() - 1);
//                    query = collection.orderBy(FieldPath.documentId()).startAfter(last.getId()).limit(batchSize);
//
//                    deleted = deleteQueryBatch(query);
//                }
//
//                return null;
//            }
//        });
//    }

//    @WorkerThread
//    private List<DocumentSnapshot> deleteQueryBatch(final Query query) throws Exception {
//        QuerySnapshot querySnapshot = Tasks.await(query.get());
//
//        WriteBatch batch = query.getFirestore().batch();
//        for (DocumentSnapshot snapshot : querySnapshot) {
//            batch.delete(snapshot.getReference());
//        }
//        Tasks.await(batch.commit());
//
//        return querySnapshot.getDocuments();
//    }

}
