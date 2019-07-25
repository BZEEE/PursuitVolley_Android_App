package com.beazle.pursuitvolley.Coach;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beazle.pursuitvolley.Coach.CoachProfile.CoachProfileActivity;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CoachLoginActivity extends AppCompatActivity {

    private static final String TAG = "CoachLoginActivity";

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private String curent_user_id;

    private TextView signInSignUpTextSwitch;
    private TextView signInSignUpTitle;
    private EditText activationCodeEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInSignUpButton;
    private boolean signInState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_login);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        curent_user_id = mAuth.getUid();

        signInState = true;
        signInSignUpButton = findViewById(R.id.coachSignInSignUpButton);
        signInSignUpTextSwitch = findViewById(R.id.coachSignInSignUpClickableTextSwitch);
        signInSignUpTitle = findViewById(R.id.coachSignInSignUpTitle);
        activationCodeEditText = findViewById(R.id.coachSignUpActivationCode);
        activationCodeEditText.setVisibility(View.INVISIBLE);
        emailEditText = findViewById(R.id.coachEmailAddressEntryBox);
        passwordEditText = findViewById(R.id.coachPasswordEntryBox);

        signInSignUpTextSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signInState) {
                    signInState = false;
                    signInSignUpTextSwitch.setText(R.string.coach_sign_up_clickable_text);
                    signInSignUpButton.setText(R.string.coach_sign_up_button_text);
                    signInSignUpTitle.setText(R.string.coach_sign_up_button_text);
                    activationCodeEditText.setVisibility(View.VISIBLE);
                } else {
                    signInState = true;
                    signInSignUpTextSwitch.setText(R.string.coach_sign_in_clickable_text);
                    signInSignUpButton.setText(R.string.coach_sign_in_button_text);
                    signInSignUpTitle.setText(R.string.coach_sign_in_button_text);
                    activationCodeEditText.setVisibility(View.INVISIBLE);
                }
            }
        });

        signInSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activationCode = activationCodeEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (signInState) {
                    // user is signing in (Login)
                    SignInCoach(email, password);
                } else {
                    // user is signing up (Registering)
                    CheckActivationCode(activationCode, email, password);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // user is already signed in, so go straight to coach profile
        if(curent_user_id != null){
            startActivity(new Intent(this, CoachProfileActivity.class));
            finish();
        }

    }

    private void SignInCoach(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInUserWithEmail:success");
                            GoToCoachProfileActivity();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInUserWithEmail:failure", task.getException());
                            Toast.makeText(CoachLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });

    }

    private void SignUpCoach(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            // coach document is initialized using cloud functions
                            // add new coach to CoachManager class so that the recycler view knows which coaches to show to players
                            String coachId = mAuth.getCurrentUser().getUid();
                            CoachManager.AddCoachToList(new Coach(coachId));
                            AddDefaultCoachDataToFirestoreAfterSigningUp(coachId);
                            GoToCoachInfoEntryActivity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CoachLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });
    }

    private void CheckActivationCode(final String activationCode, final String email, final String password) {
        // get activation code from cloud firestore query
        DocumentReference docRef = mFirestore.collection("admin_codes").document("coach_activation_code");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // get activation from document data
                        String codeFromFirebase = (String) document.getData().get("code");
                        if (codeFromFirebase.equals(activationCode)) {
                            SignUpCoach(email, password);
                        } else {
                            Toast.makeText(CoachLoginActivity.this, "wrong activation code", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "Admin codes document does not exist");
                    }
                } else {
                    Log.d(TAG, "document get() failed with ", task.getException());
                }
            }
        });
    }

    private void GoToCoachInfoEntryActivity() {
        startActivity(new Intent(this, CoachInfoEntry.class));
    }

    private void GoToCoachProfileActivity() {
        startActivity(new Intent(this, CoachProfileActivity.class));
    }

    private void AddDefaultCoachDataToFirestoreAfterSigningUp(String coachId) {
        // add intial data fields to firestore for coach specific document
        Map<String, Object> data = new HashMap<>();
        data.put("fullname", "");
        data.put("age", "");
        data.put("location", "");
        data.put("bio", "");
        mFirestore.collection("coaches").document(coachId).set(data);
    }


}