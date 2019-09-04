package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerBio;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PlayerBioViewModel extends ViewModel {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    private MutableLiveData<Map<String, Object>> playerBioData;



    public LiveData<Map<String, Object>> GetData() {
        if (playerBioData == null) {
            playerBioData = new MutableLiveData<Map<String, Object>>();
            LoadPlayerBioData();
        }
        return playerBioData;
    }

    private void LoadPlayerBioData() {
        // load data asynchronously for player's bio from firebase
        DocumentReference docRef = mFirestore.collection(FirestoreTags.playerCollection).document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // set mutable live data to be the refreshed data from firestore

                        String playerNameValueBox = (String) document.get(FirestoreTags.playerDocumentFullname);
                        String playerAgeValueBox = (String) document.get(FirestoreTags.playerDocumentAge);
                        String playerLocationValueBox = (String) document.get(FirestoreTags.playerDocumentLocation);
                        String playerPhoneNumberValueBox = (String) document.get(FirestoreTags.playerDocumentPhoneNumber);
                        String playerEmailValueBox = (String) document.get(FirestoreTags.playerDocumentEmail);

                        Map<String, Object> data = new HashMap<>();
                        data.put(FirestoreTags.playerDocumentFullname, playerNameValueBox == null ? "" : playerNameValueBox);
                        data.put(FirestoreTags.playerDocumentAge,playerAgeValueBox == null ? "" : playerAgeValueBox);
                        data.put(FirestoreTags.playerDocumentLocation,playerLocationValueBox == null ? "" : playerLocationValueBox);
                        data.put(FirestoreTags.playerDocumentPhoneNumber,playerPhoneNumberValueBox == null ? "" : playerPhoneNumberValueBox);
                        data.put(FirestoreTags.playerDocumentEmail,playerEmailValueBox == null ? "" : playerEmailValueBox);

                        playerBioData.setValue(data);

                    } else {
                        Log.d(DebugTags.DebugTAG, "No such document found, (RefreshPlayerBioFromFirestore)");
                    }
                } else {
                    Log.d(DebugTags.DebugTAG, "get failed with (RefreshPlayerBioFromFirestore) ", task.getException());
                }
            }
        });
    }
}
