package com.beazle.pursuitvolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CoachSelectionActivity extends AppCompatActivity {

    List<Coach> coachList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_selection);

        coachList = new ArrayList<>();
        // retreive all coaches and their info from firestore
        // pull down their relavent thumbnail images from cloud storage as well
        // create new Coach objects and add them to the list
        // coach = new Coach();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCoachSelection);
        CoachSelectionRecyclerViewAdapter adapter = new CoachSelectionRecyclerViewAdapter(this, coachList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
