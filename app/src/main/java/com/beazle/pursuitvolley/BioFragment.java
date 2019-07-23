package com.beazle.pursuitvolley;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class BioFragment extends Fragment {

    private final static String TAG = "BioFragment";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private TextView coachNameValueBox;
    private TextView coachAgeValueBox;
    private TextView coachLocationValueBox;
    private TextView coachBioValueBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        View bioFragmentView = inflater.inflate(R.layout.fragment_bio, container, false);
        coachNameValueBox = bioFragmentView.findViewById(R.id.coachNameValue);
        coachAgeValueBox = bioFragmentView.findViewById(R.id.coachAgeValue);
        coachLocationValueBox = bioFragmentView.findViewById(R.id.coachLocationValue);
        coachBioValueBox = bioFragmentView.findViewById(R.id.coachBioValue);
        RefreshCoachBioUI();

        return bioFragmentView;
    }

    private void RefreshCoachBioUI() {
        DocumentReference docRef = mFirestore.collection("coaches").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // set UI data to be th refreshed data from firestore
                        coachNameValueBox.setText(document.getString("fullname"));
                        coachAgeValueBox.setText(document.getString("age"));
                        coachLocationValueBox.setText(document.getString("location"));
                        coachBioValueBox.setText(document.getString("bio"));
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
