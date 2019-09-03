package com.beazle.pursuitvolley.Player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beazle.pursuitvolley.Coach.CoachProfile.CoachProfileActivity;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerProfileActivity;
import com.beazle.pursuitvolley.R;
import com.beazle.pursuitvolley.RealtimeDatabaseTags.RealtimeDatabaseTags;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class PlayerInfoEntryActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;

    private EditText fullnameEditText;
    private EditText ageEditText;
    private EditText locationEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;
    private Button enterPlayerInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info_entry);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        currentUser = mAuth.getCurrentUser();

        fullnameEditText = findViewById(R.id.playerInfoEntryNameEditBox);
        ageEditText = findViewById(R.id.playerInfoEntryAgeEditBox);
        locationEditText = findViewById(R.id.playerInfoEntryLocationEditBox);
        phoneNumberEditText = findViewById(R.id.playerInfoEntryPhoneNumberEditBox);
        emailEditText = findViewById(R.id.playerInfoEntryEmailEditBox);
        enterPlayerInfoButton = findViewById(R.id.playerInfoEntryButton);

        enterPlayerInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePlayerInfoInFirestore();
                // UpdatePlayerInfoInRealtimeDatabase();
                GoToPlayerUserProfile();
                // end the activity once we've finished the info entry
                finish();
            }
        });
    }

    private void GoToPlayerUserProfile() {
        startActivity(new Intent(this, PlayerProfileActivity.class));
    }

    private void UpdatePlayerInfoInFirestore() {
        String fullname = fullnameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();

        String playerUniqueId = mAuth.getCurrentUser().getUid();

        DocumentReference playersRef = mFirestore.collection(FirestoreTags.coachCollection).document(playerUniqueId);
        // handle exception and edge cases
        // write data to all fields of the coach's firestore document

        if (!TextUtils.isEmpty(fullname)) {
            playersRef
                    .update(FirestoreTags.playerDocumentFullname, fullname)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (player) collection with field name, " + FirestoreTags.playerDocumentFullname);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (player) collection with field name, " + FirestoreTags.playerDocumentFullname);
                        }
                    });
        }

        if (!TextUtils.isEmpty(age)) {
            playersRef
                    .update(FirestoreTags.playerDocumentAge, age)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (player) collection with field name, " + FirestoreTags.playerDocumentAge);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (player) collection with field name, " + FirestoreTags.playerDocumentAge);
                        }
                    });
        }

        if (!TextUtils.isEmpty(location)) {
            playersRef
                    .update(FirestoreTags.playerDocumentLocation, location)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (player) collection with field name, " + FirestoreTags.playerDocumentLocation);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (player) collection with field name, " + FirestoreTags.playerDocumentLocation);
                        }
                    });
        }



        if (!TextUtils.isEmpty(phoneNumber)) {
            playersRef
                    .update(FirestoreTags.playerDocumentPhoneNumber, phoneNumber)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (player) collection with field name, " + FirestoreTags.playerDocumentPhoneNumber);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (player) collection with field name, " + FirestoreTags.playerDocumentPhoneNumber);
                        }
                    });
        }

        if (!TextUtils.isEmpty(email)) {
            playersRef
                    .update(FirestoreTags.playerDocumentEmail, email)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // document successfully update
                            // so also update Coach object for the Recycler view to reference as well
                            Log.d(DebugTags.DebugTAG, "successfully updated document in (player) collection with field name, " + FirestoreTags.playerDocumentEmail);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // document not successfully updated
                            Log.d(DebugTags.DebugTAG, "Failed to update document in (player) collection with field name, " + FirestoreTags.playerDocumentEmail);
                        }
                    });
        }

    }

//    private void UpdatePlayerInfoInRealtimeDatabase() {
//        String fullname = fullnameEditText.getText().toString();
//        String age = ageEditText.getText().toString();
//        String location = locationEditText.getText().toString();
//        String phoneNumber = phoneNumberEditText.getText().toString();
//        String email = emailEditText.getText().toString();
//
//        String coachUniqueId = mAuth.getCurrentUser().getUid();
//
//        DatabaseReference playerRef = mRealtimeDatabase.getReference().child(RealtimeDatabaseTags.playersCollecion).child(coachUniqueId);
//
//        if (!TextUtils.isEmpty(fullname)) {
//            playerRef.child(RealtimeDatabaseTags.coachDataFullname).setValue(fullname)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            // Write was successful!
//                            Log.d(DebugTags.DebugTAG, "Successfully updated player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataFullname);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Write failed
//                            Log.d(DebugTags.DebugTAG, "Failed to update player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataFullname);
//                        }
//                    });
//        }
//
//        if (!TextUtils.isEmpty(age)) {
//            playerRef.child(RealtimeDatabaseTags.playerDataAge).setValue(age)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            // Write was successful!
//                            Log.d(DebugTags.DebugTAG, "Successfully updated player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataAge);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Write failed
//                            Log.d(DebugTags.DebugTAG, "Failed to update player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataAge);
//                        }
//                    });
//        }
//
//        if (!TextUtils.isEmpty(location)) {
//            playerRef.child(RealtimeDatabaseTags.playerDataLocation).setValue(location)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            // Write was successful!
//                            Log.d(DebugTags.DebugTAG, "Successfully updated player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataLocation);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Write failed
//                            Log.d(DebugTags.DebugTAG, "Failed to update player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataLocation);
//                        }
//                    });
//        }
//
//        if (!TextUtils.isEmpty(phoneNumber)) {
//            playerRef.child(RealtimeDatabaseTags.playerDataPhoneNumber).setValue(phoneNumber)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            // Write was successful
//                            Log.d(DebugTags.DebugTAG, "Successfully updated player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataPhoneNumber);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Write failed
//                            Log.d(DebugTags.DebugTAG, "Failed to update player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataPhoneNumber);
//                        }
//                    });
//        }
//
//        if (!TextUtils.isEmpty(email)) {
//            playerRef.child(RealtimeDatabaseTags.playerDataEmail).setValue(email)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            // Write was successful
//                            Log.d(DebugTags.DebugTAG, "Successfully updated player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataEmail);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Write failed
//                            Log.d(DebugTags.DebugTAG, "Failed to update player data in realtime database with field name, " + RealtimeDatabaseTags.playerDataEmail);
//                        }
//                    });
//        }
//
//    }


}
