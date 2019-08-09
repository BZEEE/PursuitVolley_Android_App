package com.beazle.pursuitvolley.Coach.CoachSelection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.R;

import java.util.List;
import java.util.Observer;

public class CoachSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_selection);

        RelativeLayout coachSelectionActivityRelativeLayout = findViewById(R.id.coachSelectionActivityRelativeLayout);

        // initialize progress bar for loading coaches
        ProgressBar progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        coachSelectionActivityRelativeLayout.addView(progressBar,params);
        progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
        // disable user interaction with the window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        CoachManager model = ViewModelProviders.of(this).get(CoachManager.class);
        // get the coaches list from the mutable view model
        // view model takes care of retrieving data from firebase
        List coachesList = model.GetCoaches().getValue();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCoachSelection);
        CoachSelectionRecyclerViewAdapter adapter = new CoachSelectionRecyclerViewAdapter(this, coachesList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        // hide progress bar
        progressBar.setVisibility(View.GONE);
        // enable user interaction again
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
