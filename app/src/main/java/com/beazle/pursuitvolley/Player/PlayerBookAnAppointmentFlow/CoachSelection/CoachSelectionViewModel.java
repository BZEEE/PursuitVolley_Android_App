package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.CoachSelection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseCloudStorageTags.CloudStorageTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoachSelectionViewModel extends ViewModel {
    private FirebaseFirestore mFirestore;
    private FirebaseStorage mCloudStorage;
    private Context context;

    private static MutableLiveData<List<Coach>> coaches;

    public LiveData<List<Coach>> GetCoaches(Context context) {
        if (coaches == null) {
            coaches = new MutableLiveData<List<Coach>>();
            this.context = context;
            LoadCoaches();
        }
        return coaches;
    }

    private void LoadCoaches() {
        mFirestore = FirebaseFirestore.getInstance();
        mCloudStorage = FirebaseStorage.getInstance();
        // Fetch Coaches from Firestore
        mFirestore.collection(FirestoreTags.coachCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                List<Coach> coachList = new ArrayList<>();
                                StorageReference coachesFolderReference = mCloudStorage.getReference().child(CloudStorageTags.coachesFolder);
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map coachData = document.getData();
                                    String coachUniqueId = document.getId();
                                    String coachName = (String) coachData.get(FirestoreTags.coachDocumentFullname);
                                    String coachAge = (String) coachData.get(FirestoreTags.coachDocumentAge);
                                    String coachBio = (String) coachData.get(FirestoreTags.coachDocumentBio);
                                    String coachLocation = (String) coachData.get(FirestoreTags.coachDocumentLocation);
                                    Coach coach = new Coach(coachUniqueId, coachName, coachAge, coachLocation, coachBio);

                                    // set coach thumbnail
                                    coachesFolderReference.child(document.getId()).child(CloudStorageTags.coachThumbnailTAG).
                                            getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            // Use the bytes to display the image
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            coach.SetThumbnail(bitmap);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // set default picture as profile pic
                                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_profile_pic);
                                            coach.SetThumbnail(bitmap);
                                            Log.d(DebugTags.DebugTAG, "failed to thumbnail from cloud storage, setting coach thumbnailas default pic");
                                            // Handle any errors
                                        }
                                    });
                                    coachList.add(coach);
                                }
                                coaches.setValue(coachList);

                            } else {
                                // no collection exists
                                // task.getResult() is null
                            }

                        } else {
                            Log.d(DebugTags.DebugTAG, "Error getting documents: ", task.getException());
                            Log.d(DebugTags.DebugTAG, "cannot show coaches to player, handles errors accordingly, show certain ones to user. Eg: no internet connection, so cant connect to firebase");
                        }
                    }
                });

    }
}
