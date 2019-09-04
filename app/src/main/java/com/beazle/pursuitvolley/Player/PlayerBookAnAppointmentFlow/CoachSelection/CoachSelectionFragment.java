package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.CoachSelection;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.beazle.pursuitvolley.R;

public class CoachSelectionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coach_selection, container, false);

        RelativeLayout coachSelectionActivityRelativeLayout = view.findViewById(R.id.coachSelectionFragmentRelativeLayout);

        // initialize progress bar fo
        ProgressBar progressBar = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        coachSelectionActivityRelativeLayout.addView(progressBar,params);
        progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
        // disable user interaction with the window
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        CoachSelectionViewModel model = ViewModelProviders.of(this).get(CoachSelectionViewModel.class);
        // get the coaches list from the mutable view model
        // view model takes care of retrieving data from firebase

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCoachSelectionFragment);
        CoachSelectionRecyclerViewAdapter adapter = new CoachSelectionRecyclerViewAdapter(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        model.GetCoaches(getContext()).observe(this, coaches -> {
            adapter.SetCoachesData(coaches);
        });

        // hide progress bar
        progressBar.setVisibility(View.GONE);
        // enable user interaction again
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        return view;
    }
}
