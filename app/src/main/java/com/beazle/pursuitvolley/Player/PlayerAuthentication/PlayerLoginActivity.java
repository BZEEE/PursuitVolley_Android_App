package com.beazle.pursuitvolley.Player.PlayerAuthentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.Player.Player;
import com.beazle.pursuitvolley.Player.PlayerInfoEntryActivity;
import com.beazle.pursuitvolley.Player.PlayerProfile.PlayerProfileActivity;
import com.beazle.pursuitvolley.R;
import com.beazle.pursuitvolley.RealtimeDatabaseTags.RealtimeDatabaseTags;
import com.facebook.CallbackManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerLoginActivity extends AppCompatActivity {

    public static final String TAG = "PlayerLoginActivityTAG";
    public static final String googleSignInAccountId = "GoogleSignInAccount";
    private static final int PURSUIT_VOLLEY_REQUEST_CODE = 9001;
    private FirebaseUser currentPlayer;
    // Firebase Auth Object.
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseDatabase mRealtimeDatabase;
    private DatabaseReference mRealtimeDatabaseRootReference;
    // Google Sign In button .
    com.google.android.gms.common.SignInButton signInButton;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private GoogleSignInClient mGoogleSignInClient;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_login);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mRealtimeDatabase = FirebaseDatabase.getInstance();
        mRealtimeDatabaseRootReference = mRealtimeDatabase.getReference();

        currentPlayer = mAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentPlayer != null) {
            startActivity(new Intent(this, PlayerProfileActivity.class));
            finish();
        } else {
            // show player sign in options
            AuthenticateUser();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PURSUIT_VOLLEY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Authentication is completed
                //current user is no longer null
                // AddPlayerToFirestoreAfterSigningUp();
                // go to player profile activity
                startActivity(new Intent(this, PlayerInfoEntryActivity.class));
                finish();
            } else {
                // error with Authentication
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // request code did not equal PURSUIT_VOLLEY_REQUEST_CODE
        }
    }

    private List<AuthUI.IdpConfig> GetProviderList() {
        List<AuthUI.IdpConfig> providers = new ArrayList<>();
        providers.add(new
                AuthUI.IdpConfig.EmailBuilder().build());
        providers.add(new
                AuthUI.IdpConfig.GoogleBuilder().build());
        providers.add(new
                AuthUI.IdpConfig.FacebookBuilder().build());
        return providers;
    }

    private void AuthenticateUser() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(R.style.PlayerLoginTheme)
//                        .setLogo(R.drawable.firelogo)
                        .setAvailableProviders(GetProviderList())
                        .setIsSmartLockEnabled(true)
                        .build(),
                PURSUIT_VOLLEY_REQUEST_CODE);
    }

    private void AddPlayerToFirestoreAfterSigningUp() {
        // add intial data fields to firestore for coach specific document
        Map<String, Object> data = new HashMap<>();
        data.put(FirestoreTags.playerDocumentFullname, "");
        data.put(FirestoreTags.playerDocumentAge, "");
        data.put(FirestoreTags.playerDocumentLocation, "");
        data.put(FirestoreTags.playerDocumentEmail, "");
        data.put(FirestoreTags.playerDocumentPhoneNumber, "");
        data.put(FirestoreTags.playerDocumentTokens, 0);
        mFirestore.collection(FirestoreTags.playerCollection).document(currentPlayer.getUid()).set(data);
    }

    private void AddCoachToRealtimeDatabaseAfterSigningUp() {
        // add intial data fields to firestore for coach specific document
        Map<String, Object> data = new HashMap<String, Object>();
        Player player = new Player( "", "", "", "", "");
        data.put(currentPlayer.getUid(), player);
        mRealtimeDatabaseRootReference.child(RealtimeDatabaseTags.coachesCollecion).child(currentPlayer.getUid()).setValue(data);
    }
}
