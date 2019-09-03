package com.beazle.pursuitvolley.Player.PlayerProfile.PlayerBio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.beazle.pursuitvolley.Coach.CoachInfoEntryActivity;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.Player.PlayerInfoEntryActivity;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class PlayerBioFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private View bioFragmentView;

    private PlayerBioViewModel playerBioViewModel;

    private TextView playerNameValueBox;
    private TextView playerAgeValueBox;
    private TextView playerLocationValueBox;
    private TextView playerPhoneNumberValueBox;
    private TextView playerEmailValueBox;
    private Button playerEditInfoButtton;

    public static PlayerBioFragment newInstance() {
        return new PlayerBioFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bioFragmentView = inflater.inflate(R.layout.fragment_coach_bio, container, false);

        playerNameValueBox = bioFragmentView.findViewById(R.id.playerNameValue);
        playerAgeValueBox = bioFragmentView.findViewById(R.id.playerAgeValue);
        playerLocationValueBox = bioFragmentView.findViewById(R.id.playerLocationValue);
        playerPhoneNumberValueBox = bioFragmentView.findViewById(R.id.playerPhoneNumberValue);
        playerEmailValueBox = bioFragmentView.findViewById(R.id.playerEmailValue);
        playerEditInfoButtton = bioFragmentView.findViewById(R.id.playerBioFragmentEditInfoButton);

        playerEditInfoButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToPlayerInfoEntryActivity();
            }
        });

        //apply loaded view model data to fragment UI
        playerBioViewModel = ViewModelProviders.of(this).get(PlayerBioViewModel.class);
        // let this fragment be an observer of the mutable data in the viewmodel
        playerBioViewModel.GetData().observe(this, playerBioData -> {
            // update player bio UI
            playerNameValueBox.setText( (String) playerBioData.get(FirestoreTags.playerDocumentFullname));
            playerAgeValueBox.setText( (String) playerBioData.get(FirestoreTags.playerDocumentAge));
            playerLocationValueBox.setText( (String) playerBioData.get(FirestoreTags.playerDocumentLocation));
            playerPhoneNumberValueBox.setText( (String) playerBioData.get(FirestoreTags.playerDocumentPhoneNumber));
            playerEmailValueBox.setText( (String) playerBioData.get(FirestoreTags.playerDocumentEmail));
        });

        return bioFragmentView;
    }

    private void GoToPlayerInfoEntryActivity() {
        startActivity(new Intent(getActivity(), PlayerInfoEntryActivity.class));
    }
}
