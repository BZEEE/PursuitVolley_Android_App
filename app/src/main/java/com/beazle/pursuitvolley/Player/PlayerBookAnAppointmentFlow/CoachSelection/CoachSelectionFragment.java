package com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.CoachSelection;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.DateSelection.PlayerDateSelectionFragment;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.PlayerBookAnAppointmentActivity;
import com.beazle.pursuitvolley.Player.PlayerBookAnAppointmentFlow.PlayerBookAnAppointmentViewModel;
import com.beazle.pursuitvolley.R;

public class CoachSelectionFragment extends Fragment {

    private static FragmentManager fragmentManager;
    private CoachSelectionViewModel coachSelectionViewModel;
    private static PlayerBookAnAppointmentViewModel playerBookAnAppointmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coach_selection, container, false);

        fragmentManager = getFragmentManager();
        coachSelectionViewModel = ViewModelProviders.of(this).get(CoachSelectionViewModel.class);
        playerBookAnAppointmentViewModel = PlayerBookAnAppointmentActivity.GetPlayerBookAnAppointmentViewModel();

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


        // get the coaches list from the mutable view model
        // view model takes care of retrieving data from firebase

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCoachSelectionFragment);
        CoachSelectionRecyclerViewAdapter adapter = new CoachSelectionRecyclerViewAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);

        coachSelectionViewModel.GetCoaches(getActivity()).observe(this, coaches -> {
            adapter.SetCoachesData(coaches);
        });

        // hide progress bar
        progressBar.setVisibility(View.GONE);
        // enable user interaction again
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        return view;
    }

    public static void GoToPlayerDateSelectionFragment(Coach coach) {
        // set data from selected coach to view model, so we have a receipt of information about the appointment
        playerBookAnAppointmentViewModel.SetAppointmentCoachInformation(coach);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerDateSelectionFragment fragment = new PlayerDateSelectionFragment();
        fragmentTransaction.add(R.id.bookAnAppointmentflowFragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
