package com.beazle.pursuitvolley.Coach.CoachSelection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.R;

import java.util.List;
import java.util.Observer;

public class CoachSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_selection);

        CoachManager model = ViewModelProviders.of(this).get(CoachManager.class);
        model.GetCoaches();
        // retreive all coaches and their info from firestore
        // pull down their relavent thumbnail images from cloud storage as well
        // create new Coach objects and add them to the list
        // coach = new Coach();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCoachSelection);
        CoachSelectionRecyclerViewAdapter adapter = new CoachSelectionRecyclerViewAdapter(this, coachList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}
