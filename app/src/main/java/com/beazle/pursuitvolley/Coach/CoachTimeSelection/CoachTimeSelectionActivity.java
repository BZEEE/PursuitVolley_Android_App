package com.beazle.pursuitvolley.Coach.CoachTimeSelection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.beazle.pursuitvolley.Coach.CoachDateSelection.CoachDateSelectionActivity;
import com.beazle.pursuitvolley.Coach.CoachSelection.CoachSelectionRecyclerViewAdapter;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CoachTimeSelectionActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private RecyclerView timeSlotList;

    public static final String TAG = "CoachTimeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_time_selection);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String coachId = getIntent().getStringExtra(CoachSelectionRecyclerViewAdapter.CoachCardViewHolder.coachSelectionViewHolderTag);
        long selectedDate = getIntent().getLongExtra(CoachDateSelectionActivity.dateTAG, 0);

        List<String> timeSlots = GetAvailabletimeSlotsForThatDate(coachId, Long.toString(selectedDate));


        timeSlotList = findViewById(R.id.timeSelectionRecyclerView);
        timeSlotList.setLayoutManager(new LinearLayoutManager(this));
        timeSlotList.setAdapter(new CoachTimeSelectionRecyclerViewAdapter(this, timeSlots, coachId, selectedDate));
    }

    private List<String> GetAvailabletimeSlotsForThatDate(String coachId, String date) {
        // get all relaven
        final List<String> timeSlots = new ArrayList<>();
        CollectionReference collectionRef = mFirestore.collection("coaches").document(coachId).collection(date);
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (int i = 0; i < documents.size(); i++) {
                        DocumentSnapshot doc = documents.get(i);
                        boolean timeSlotIsBooked = (boolean) doc.getData().get("booked");
                        if (timeSlotIsBooked) {
                            timeSlots.add(doc.getId());
                        }
                    }
                }
            }
        });

        return timeSlots;
    }
}
