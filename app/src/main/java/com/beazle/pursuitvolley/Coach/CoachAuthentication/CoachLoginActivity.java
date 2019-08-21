package com.beazle.pursuitvolley.Coach.CoachAuthentication;

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

import com.beazle.pursuitvolley.Coach.CoachInfoEntryActivity;
import com.beazle.pursuitvolley.Coach.CoachProfile.CoachProfileActivity;
import com.beazle.pursuitvolley.Coach.CoachSelection.Coach;
import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.R;
import com.beazle.pursuitvolley.RealtimeDatabaseTags.RealtimeDatabaseTags;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CoachLoginActivity extends AppCompatActivity {

    private DatabaseReference mRealtimeDatabaseRootReference;
    private FirebaseDatabase mRealtimeDatabase;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private FirebaseUser currentCoach;

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

        mRealtimeDatabase = FirebaseDatabase.getInstance();
        mRealtimeDatabaseRootReference = mRealtimeDatabase.getReference();
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentCoach = mAuth.getCurrentUser();
        mAuth.signOut();

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
        if(currentCoach != null){
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
                            Log.d(DebugTags.DebugTAG, "signInUserWithEmail:success");
                            GoToCoachProfileActivity();
                            finish();

                        } else {
                            // handle authentication errors
                            Exception error = task.getException();
                            if (error != null) {

                                String errorCode = error.getMessage();
                                switch (errorCode) {
                                    case "ERROR_INVALID_CUSTOM_TOKEN":
                                        Toast.makeText(CoachLoginActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                        Toast.makeText(CoachLoginActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_CREDENTIAL":
                                        Toast.makeText(CoachLoginActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_EMAIL":
                                        Toast.makeText(CoachLoginActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_WRONG_PASSWORD":
                                        Toast.makeText(CoachLoginActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_MISMATCH":
                                        Toast.makeText(CoachLoginActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_REQUIRES_RECENT_LOGIN":
                                        Toast.makeText(CoachLoginActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                        Toast.makeText(CoachLoginActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        Toast.makeText(CoachLoginActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                        Toast.makeText(CoachLoginActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_DISABLED":
                                        Toast.makeText(CoachLoginActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_TOKEN_EXPIRED":
                                        Toast.makeText(CoachLoginActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_NOT_FOUND":
                                        Toast.makeText(CoachLoginActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_USER_TOKEN":
                                        Toast.makeText(CoachLoginActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_OPERATION_NOT_ALLOWED":
                                        Toast.makeText(CoachLoginActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_WEAK_PASSWORD":
                                        Toast.makeText(CoachLoginActivity.this, "The given password is invalid, it must be 6 characters at least.", Toast.LENGTH_LONG).show();
                                        break;

                                    default:
                                        Log.d(DebugTags.DebugTAG, errorCode);
                                }
                            } else {
                                Log.d(DebugTags.DebugTAG, "Caught exception is null: Coach Login Activity");
                            }

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
                            Log.d(DebugTags.DebugTAG, "createUserWithEmail:success");
                            // coach document is initialized using cloud functions
                            // add new coach to CoachManager class so that the recycler view knows which coaches to show to players
                            AddCoachToFirestoreAfterSigningUp();
                            AddCoachToRealtimeDatabaseAfterSigningUp();
                            // AddDefaultCoachDataToRealtimeDatabaseAfterSigningUp(coach);
                            GoToCoachInfoEntryActivity();
                            finish();

                        } else {
                            // handle authentication errors
                            Exception error = task.getException();
                            if (error != null) {

                                String errorCode = error.getMessage();
                                switch (errorCode) {
                                    case "ERROR_INVALID_CUSTOM_TOKEN":
                                        Toast.makeText(CoachLoginActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                        Toast.makeText(CoachLoginActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_CREDENTIAL":
                                        Toast.makeText(CoachLoginActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_EMAIL":
                                        Toast.makeText(CoachLoginActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_WRONG_PASSWORD":
                                        Toast.makeText(CoachLoginActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_MISMATCH":
                                        Toast.makeText(CoachLoginActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_REQUIRES_RECENT_LOGIN":
                                        Toast.makeText(CoachLoginActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                        Toast.makeText(CoachLoginActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        Toast.makeText(CoachLoginActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                        Toast.makeText(CoachLoginActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_DISABLED":
                                        Toast.makeText(CoachLoginActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_TOKEN_EXPIRED":
                                        Toast.makeText(CoachLoginActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_NOT_FOUND":
                                        Toast.makeText(CoachLoginActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_USER_TOKEN":
                                        Toast.makeText(CoachLoginActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_OPERATION_NOT_ALLOWED":
                                        Toast.makeText(CoachLoginActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_WEAK_PASSWORD":
                                        Toast.makeText(CoachLoginActivity.this, "The given password is invalid, it must be 6 characters at least.", Toast.LENGTH_LONG).show();
                                        break;

                                    default:
                                        Log.d(DebugTags.DebugTAG, errorCode);
                                }
                            } else {
                                Log.d(DebugTags.DebugTAG, "Caught exception is null: Coach Login Activity");
                            }

                        }
                    }
                });
    }

    private void CheckActivationCode(final String accountActivationCode, final String email, final String password) {
        // get activation code from cloud firestore query
        DocumentReference docRef = mFirestore.collection(FirestoreTags.adminCodesCollection).document(FirestoreTags.coachAccountActivationCode);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // get activation from document data
                        String codeFromFirebase = (String) document.getData().get(FirestoreTags.codeKey);
                        if (codeFromFirebase.equals(accountActivationCode)) {
                            SignUpCoach(email, password);
                        } else {
                            Toast.makeText(CoachLoginActivity.this, "wrong activation code", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(DebugTags.DebugTAG, "Admin codes document does not exist");
                    }
                } else {
                    Log.d(DebugTags.DebugTAG, "document get() failed with ", task.getException());
                }
            }
        });
    }

    private void GoToCoachInfoEntryActivity() {
        startActivity(new Intent(this, CoachInfoEntryActivity.class));
    }

    private void GoToCoachProfileActivity() {
        startActivity(new Intent(this, CoachProfileActivity.class));
    }

    private void AddCoachToFirestoreAfterSigningUp() {
        // add intial data fields to firestore for coach specific document
        Map<String, Object> data = new HashMap<>();
        data.put(FirestoreTags.coachDocumentFullname, "");
        data.put(FirestoreTags.coachDocumentAge, "");
        data.put(FirestoreTags.coachDocumentLocation, "");
        data.put(FirestoreTags.coachDocumentBio, "");
        mFirestore.collection(FirestoreTags.coachCollection).document(currentCoach.getUid()).set(data);
    }

    private void AddCoachToRealtimeDatabaseAfterSigningUp() {
        // add intial data fields to firestore for coach specific document
        Coach coach = new Coach(currentCoach.getUid(), "", "", "", "");
        mRealtimeDatabaseRootReference.child(RealtimeDatabaseTags.coachesCollecion).child(currentCoach.getUid()).setValue(coach);
    }

}
