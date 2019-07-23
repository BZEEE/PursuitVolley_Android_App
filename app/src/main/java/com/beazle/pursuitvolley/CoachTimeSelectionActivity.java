package com.beazle.pursuitvolley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CoachTimeSelectionActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    public static final String TAG = "CoachTimeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_time_selection);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        long date = getIntent().getLongExtra(CoachCalenderActivity.dateTAG, 0);
        GetAvailabletimeSlotsForThatDate(Long.toString(date));

    }

    private void GetAvailabletimeSlotsForThatDate(String date) {
//        DocumentReference docRef = mFirestore.collection("coaches").document(mAuth.getCurrentUser().getUid()).collection(date);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        // get activation from document data
//                        String codeFromFirebase = (String) document.getData().get("code");
//
//                    } else {
//                        Log.d(TAG, "Admin codes document does not exist");
//                    }
//                } else {
//                    Log.d(TAG, "document get() failed with ", task.getException());
//                }
//            }
//        });
    }
}
