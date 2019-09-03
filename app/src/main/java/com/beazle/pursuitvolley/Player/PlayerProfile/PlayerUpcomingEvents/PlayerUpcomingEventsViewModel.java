package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerUpcomingEvents;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerUpcomingEventsViewModel extends ViewModel {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    private MutableLiveData<List<PlayerUpcomingEvent>> playerUpcomingEventData;

    public LiveData<List<PlayerUpcomingEvent>> GetData() {
        if (playerUpcomingEventData == null) {
            playerUpcomingEventData = new MutableLiveData<List<PlayerUpcomingEvent>>();
            LoadPlayerUpcomingEventsData();
        }
        return playerUpcomingEventData;
    }

    private void LoadPlayerUpcomingEventsData() {
        // load data asynchronously for player's bio from firebase

        mFirestore.collection(FirestoreTags.upcomingEventsCollection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                List<PlayerUpcomingEvent> playerUpcomingEventList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // firestore requires at least one document to be initialized in a collection
                                    // so we initialize a dummy placeholder document
                                    // then show the upcoming event to the user
                                    // add it to the upcoming events manager
                                    Map data = document.getData();
                                    PlayerUpcomingEvent event = new PlayerUpcomingEvent(
                                            (String) data.get(FirestoreTags.upcomingEventTitle),
                                            (String) data.get(FirestoreTags.upcomingEventDate),
                                            (String) data.get(FirestoreTags.upcomingEventBeginTime),
                                            (String) data.get(FirestoreTags.upcomingEventEndTime),
                                            (String) data.get(FirestoreTags.upcomingEventLocation)
                                    );
                                    playerUpcomingEventList.add(event);
                                }

                                playerUpcomingEventData.setValue(playerUpcomingEventList);
                            } else {
                                // collection does not exist
                                // task.getResult() is null
                            }

                        } else {
                            // error getting documents
                            // collecion might not exist yet until we create the first document
                        }
                    }
                });
    }
}
