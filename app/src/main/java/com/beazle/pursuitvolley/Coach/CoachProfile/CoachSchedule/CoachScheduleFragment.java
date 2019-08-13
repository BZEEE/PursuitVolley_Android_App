package com.beazle.pursuitvolley.Coach.CoachProfile.CoachSchedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.R;

public class CoachScheduleFragment extends Fragment {

    private CoachScheduleView coachSchedule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coach_schedule, container, false);

        coachSchedule = view.findViewById(R.id.coachScheduleView);

        // try not to refres the UI every time we switch back to the Schedule Fragment

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.coachScheduleFragmentSwipeLayout);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d(DebugTags.DebugTAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        // RefreshScheduleUI()
                    }
                }
        );

        return view;
    }
}
