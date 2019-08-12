package com.beazle.pursuitvolley.Coach.CoachProfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseCloudStorageTags.CloudStorageTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

public class CoachBioFragment extends Fragment {

    private final static String TAG = "CoachBioFragment";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseStorage mCloudStorage;

    private View bioFragmentView;

    private TextView coachNameValueBox;
    private TextView coachAgeValueBox;
    private TextView coachLocationValueBox;
    private TextView coachBioValueBox;
    private ImageView coachProfilePic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        bioFragmentView = inflater.inflate(R.layout.fragment_bio, container, false);
        coachNameValueBox = bioFragmentView.findViewById(R.id.coachNameValue);
        coachAgeValueBox = bioFragmentView.findViewById(R.id.coachAgeValue);
        coachLocationValueBox = bioFragmentView.findViewById(R.id.coachLocationValue);
        coachBioValueBox = bioFragmentView.findViewById(R.id.coachBioValue);
        coachProfilePic = bioFragmentView.findViewById(R.id.bioFragmentCoachProfilePicImageView);
        RefreshCoachBioUI();

        return bioFragmentView;
    }

    private void RefreshCoachBioUI() {
        DocumentReference docRef = mFirestore.collection(FirestoreTags.coachCollection).document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // set UI data to be th refreshed data from firestore
                        coachNameValueBox.setText(document.getString(FirestoreTags.coachDocumentFullname));
                        coachAgeValueBox.setText(document.getString(FirestoreTags.coachDocumentAge));
                        coachLocationValueBox.setText(document.getString(FirestoreTags.coachDocumentLocation));
                        coachBioValueBox.setText(document.getString(FirestoreTags.coachDocumentBio));

                        // get thumbnail from cloud storage
                        mCloudStorage.getReference().child(CloudStorageTags.coachesFolder).child(document.getId()).child(CloudStorageTags.coachThumbnailTAG).
                                getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                // Use the bytes to display the image
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                coachProfilePic.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // set default picture as profile pic
                                Bitmap bitmap = BitmapFactory.decodeResource(bioFragmentView.getResources(), R.drawable.default_coach_profile_pic);
                                coachProfilePic.setImageBitmap(bitmap);
                                Log.d(DebugTags.DebugTAG, "failed to thumbnail from cloud storage, setting coach thumbnailas default pic");
                                // Handle any errors
                            }
                        });

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
