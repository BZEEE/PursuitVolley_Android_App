package com.beazle.pursuitvolley.Player.PlayerAuthentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beazle.pursuitvolley.DebugTags.DebugTags;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseDatabase mRealtimeDatabase;
    private FirebaseFunctions mFunctions;
    private DatabaseReference mRealtimeDatabaseRootReference;
    private FirebaseUser currentPlayer;

    private TextView signInSignUpTextSwitch;
    private TextView signInSignUpTitle;
    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private Button signInSignUpButton;
    private boolean signInState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_login);

        mRealtimeDatabase = FirebaseDatabase.getInstance();
        mRealtimeDatabaseRootReference = mRealtimeDatabase.getReference();
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentPlayer = mAuth.getCurrentUser();
        mAuth.signOut();

        signInState = true;
        signInSignUpButton = findViewById(R.id.playerSignInSignUpButton);
        signInSignUpTextSwitch = findViewById(R.id.playerSignInSignUpClickableTextSwitch);
        signInSignUpTitle = findViewById(R.id.playerSignInSignUpTitle);
        phoneNumberEditText = findViewById(R.id.playerPhoneNumberEntryBox);
        passwordEditText = findViewById(R.id.coachPasswordEntryBox);

        signInSignUpTextSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signInState) {
                    signInState = false;
                    signInSignUpTextSwitch.setText(R.string.coach_sign_up_clickable_text);
                    signInSignUpButton.setText(R.string.coach_sign_up_button_text);
                    signInSignUpTitle.setText(R.string.coach_sign_up_button_text);
                } else {
                    signInState = true;
                    signInSignUpTextSwitch.setText(R.string.coach_sign_in_clickable_text);
                    signInSignUpButton.setText(R.string.coach_sign_in_button_text);
                    signInSignUpTitle.setText(R.string.coach_sign_in_button_text);
                }
            }
        });

        signInSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (signInState) {
                    // user is signing in (Login)
                    SignInPlayer(phoneNumber, password);
                } else {
                    // user is signing up (Registering)
                    SignUpPlayer(phoneNumber, password);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentPlayer != null) {
            startActivity(new Intent(this, PlayerProfileActivity.class));
            finish();
        }
    }

    private void GetCustomPlayerAuthToken(String phoneNumber, String password) {
        // how can we create JWT token off of a unique id, when the player cat have a unique id until we sign them up
        mFunctions
                .getHttpsCallable("GenerateCustomPlayerAuthToken")
                .call()
                .addOnCompleteListener(new OnCompleteListener<HttpsCallableResult>() {
                    @Override
                    public void onComplete(@NonNull Task<HttpsCallableResult> task) {
                        if (task.isSuccessful()) {
                            // get custom token and sign-up player with it
                            Log.d(DebugTags.DebugTAG, "GetCustomPlayerAuthToken:success");
                            Map response = (Map) task.getResult().getData();
                            String customToken = (String) response.get("token");
                            SignUpPlayerWithCustomAuthToken(customToken, phoneNumber, password);
                        } else {
                            Log.d(DebugTags.DebugTAG, "GetCustomPlayerAuthToken:failure");
                        }
                    }
                });
    }

    private void SignUpPlayer(String phoneNumber, String password) {
        GetCustomPlayerAuthToken(phoneNumber, password);
    }

    private void SignUpPlayerWithCustomAuthToken(String customToken, String phoneNumber, String password) {
        mAuth.signInWithCustomToken(customToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(DebugTags.DebugTAG, "SignUpPLayerWithCustomAuthToken:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(DebugTags.DebugTAG, "SignUpPLayerWithCustomAuthToken:failure", task.getException());
                        }
                    }
                });
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
        data.put(FirestoreTags.playerDocumentAppointments, new HashMap<>());
        data.put(FirestoreTags.playerDocumentUpcomingEvents, new HashMap<>());
        mFirestore.collection(FirestoreTags.playerCollection).document(currentPlayer.getUid()).set(data);
    }

//    private void AddCoachToRealtimeDatabaseAfterSigningUp() {
//        // add intial data fields to firestore for coach specific document
//        Map<String, Object> data = new HashMap<String, Object>();
//        Player player = new Player( "", "", "", "", "");
//        data.put(currentPlayer.getUid(), player);
//        mRealtimeDatabaseRootReference.child(RealtimeDatabaseTags.coachesCollecion).child(currentPlayer.getUid()).setValue(data);
//    }
}
