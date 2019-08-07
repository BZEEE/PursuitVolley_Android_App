package com.beazle.pursuitvolley.Coach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.Coach.CoachProfile.CoachProfileActivity;
import com.beazle.pursuitvolley.Coach.CoachSelection.CoachManager;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CoachInfoEntry extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    private EditText fullnameEditText;
    private EditText ageEditText;
    private EditText locationEditText;
    private EditText bioEditText;
    private Button enterCoachInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info_entry);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        fullnameEditText = findViewById(R.id.coachInfoEntryNameEditBox);
        ageEditText = findViewById(R.id.coachInfoEntryAgeEditBox);
        locationEditText = findViewById(R.id.coachInfoEntryLocationEditBox);
        bioEditText = findViewById(R.id.coachInfoEntryBioEditBox);
        enterCoachInfoButton = findViewById(R.id.coachInfoEntryButton);
        enterCoachInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteCoachInfoToFirestore();
                GoToCoachUserProfile();
            }
        });
    }

    private void GoToCoachUserProfile() {
        startActivity(new Intent(this, CoachProfileActivity.class));
    }

    private void WriteCoachInfoToFirestore() {
        final String fullname = fullnameEditText.getText().toString();
        final String age = ageEditText.getText().toString();
        final String location = locationEditText.getText().toString();
        final String bio = bioEditText.getText().toString();

        final String coachUniqueId = mAuth.getCurrentUser().getUid();
        final Coach coach = CoachManager.GetSpecificCoach(coachUniqueId);

        Log.d(DebugTags.DebugTAG, "activate bro");

        if (coach != null) {
            Log.d(DebugTags.DebugTAG, "we good");
            DocumentReference coachesRef = mFirestore.collection("coaches").document(coachUniqueId);
            Log.d(DebugTags.DebugTAG, "got document reference");
            Log.d(DebugTags.DebugTAG, coachesRef.toString());
            // handle exception and edge cases
            // write data to all fields of the coach's firestore document
            coachesRef
                    .update("fullname", fullname)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (coach) collection with field name 'fullname'");
                            coach.SetName(fullname);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (coach) collection with field name 'fullname'");
                        }
                    });

            coachesRef
                    .update("age", age)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (coach) collection with field name 'age'");
                            coach.SetAge(age);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (coach) collection with field name 'age'");
                        }
                    });

            coachesRef
                    .update("location", location)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (coach) collection with field name 'location'");
                            coach.SetLocaton(location);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (coach) collection with field name 'location'");
                        }
                    });

            coachesRef
                    .update("bio", bio)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (coach) collection with field name 'bio'");
                            coach.SetBio(bio);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (coach) collection with field name 'bio'");
                        }
                    });
        } else {
            Log.d(DebugTags.DebugTAG, "coach is not in the list of CoachManager");
        }



    }


}
