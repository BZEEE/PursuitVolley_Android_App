package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerUpcomingEvents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerBio.PlayerBioViewModel;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class PlayerUpcomingEventsFragment extends Fragment {
    private PlayerUpcomingEventsViewModel playerUpcomingEventsViewModel;

    public static PlayerUpcomingEventsFragment newInstance() {
        return new PlayerUpcomingEventsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_upcoming_events, container, false);

        //apply loaded view model data to fragment UI
        playerUpcomingEventsViewModel = ViewModelProviders.of(this).get(PlayerUpcomingEventsViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.playerProfileFragmentUpcomingEventsRecyclerView);
        PlayerUpcomingEventsRecyclerViewAdapter adapter = new PlayerUpcomingEventsRecyclerViewAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        playerUpcomingEventsViewModel.GetData().observe(this, upcomingEventsData -> {
            // let the player appointment Ui be an observer of the view model
            // so when the view model data changes, the UI is updated
            adapter.SetPlayerUpcomingEventsData(upcomingEventsData);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }
}
