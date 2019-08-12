package com.beazle.pursuitvolley.Coach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.Coach.CoachProfile.CoachProfileActivity;
import com.beazle.pursuitvolley.Coach.CoachSelection.CoachManager;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.R;
import com.beazle.pursuitvolley.RealtimeDatabaseTags.RealtimeDatabaseTags;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CoachInfoEntryActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private FirebaseDatabase mRealtimeDatabase;
    private FirebaseAuth mAuth;

    private EditText fullnameEditText;
    private EditText ageEditText;
    private EditText locationEditText;
    private EditText bioEditText;
    private Button selectProfilePictureButton;
    private Button enterCoachInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info_entry);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mRealtimeDatabase = FirebaseDatabase.getInstance();

        fullnameEditText = findViewById(R.id.coachInfoEntryNameEditBox);
        ageEditText = findViewById(R.id.coachInfoEntryAgeEditBox);
        locationEditText = findViewById(R.id.coachInfoEntryLocationEditBox);
        bioEditText = findViewById(R.id.coachInfoEntryBioEditBox);
        selectProfilePictureButton = findViewById(R.id.coachSelectProfilePictureButton);
        enterCoachInfoButton = findViewById(R.id.coachInfoEntryButton);

        selectProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start intent filter to do the following
                // (1) allow the user to take a picture from their camera as their profile pic
                // (2) allow the user to select an image from gallery as profile pic
            }
        });

        enterCoachInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCoachInfoInFirestore();
                UpdateCoachInfoInRealtimeDatabase();
                GoToCoachUserProfile();
                // end the activity once we've finished the info entry
                finish();
            }
        });
    }

    private void GoToCoachUserProfile() {
        startActivity(new Intent(this, CoachProfileActivity.class));
    }

    private void UpdateCoachInfoInFirestore() {
        String fullname = fullnameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String bio = bioEditText.getText().toString();

        String coachUniqueId = mAuth.getCurrentUser().getUid();

        DocumentReference coachesRef = mFirestore.collection(FirestoreTags.coachCollection).document(coachUniqueId);
        // handle exception and edge cases
        // write data to all fields of the coach's firestore document

        if (!TextUtils.isEmpty(fullname)) {
            coachesRef
                    .update(FirestoreTags.coachDocumentFullname, fullname)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (coach) collection with field name, " + FirestoreTags.coachDocumentFullname);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (coach) collection with field name, " + FirestoreTags.coachDocumentFullname);
                        }
                    });
        }

        if (!TextUtils.isEmpty(age)) {
            coachesRef
                    .update(FirestoreTags.coachDocumentAge, age)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (coach) collection with field name, " + FirestoreTags.coachDocumentAge);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (coach) collection with field name, " + FirestoreTags.coachDocumentAge);
                        }
                    });
        }

        if (!TextUtils.isEmpty(location)) {
            coachesRef
                    .update(FirestoreTags.coachDocumentLocation, location)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (coach) collection with field name, " + FirestoreTags.coachDocumentLocation);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (coach) collection with field name, " + FirestoreTags.coachDocumentLocation);
                        }
                    });
        }



        if (!TextUtils.isEmpty(bio)) {
            coachesRef
                    .update(FirestoreTags.coachDocumentBio, bio)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (coach) collection with field name, " + FirestoreTags.coachDocumentBio);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (coach) collection with field name, " + FirestoreTags.coachDocumentBio);
                        }
                    });
        }

    }

    private void UpdateCoachInfoInRealtimeDatabase() {
        String fullname = fullnameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String bio = bioEditText.getText().toString();

        String coachUniqueId = mAuth.getCurrentUser().getUid();

        DatabaseReference coacesRef = mRealtimeDatabase.getReference().child(RealtimeDatabaseTags.coachesCollecion).child(coachUniqueId);

        if (!TextUtils.isEmpty(fullname)) {
            coacesRef.child(RealtimeDatabaseTags.coachDataFullname).setValue(fullname)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                            Log.d(DebugTags.DebugTAG, "Successfully updated coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataFullname);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            Log.d(DebugTags.DebugTAG, "Failed to update coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataFullname);
                        }
                    });
        }

        if (!TextUtils.isEmpty(age)) {
            coacesRef.child(RealtimeDatabaseTags.coachDataAge).setValue(age)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                            Log.d(DebugTags.DebugTAG, "Successfully updated coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataAge);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            Log.d(DebugTags.DebugTAG, "Failed to update coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataAge);
                        }
                    });
        }

        if (!TextUtils.isEmpty(location)) {
            coacesRef.child(RealtimeDatabaseTags.coachDataLocation).setValue(location)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                            Log.d(DebugTags.DebugTAG, "Successfully updated coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataLocation);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            Log.d(DebugTags.DebugTAG, "Failed to update coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataLocation);
                        }
                    });
        }

        if (!TextUtils.isEmpty(bio)) {
            coacesRef.child(RealtimeDatabaseTags.coachDataBio).setValue(bio)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful
                            Log.d(DebugTags.DebugTAG, "Successfully updated coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataBio);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            Log.d(DebugTags.DebugTAG, "Failed to update coach data in realtime database with field name, " + RealtimeDatabaseTags.coachDataBio);
                        }
                    });
            ;
        }

    }

}
