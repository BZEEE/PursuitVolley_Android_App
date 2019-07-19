package com.beazle.pursuitvolley;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CoachLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String curent_user_id;

    private TextView signInSignUpTextSwitch;
    private TextView signInSignUpTitle;
    private EditText activationCodeEditText;
    private Button signInSignUpButton;
    private boolean signInState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_login);

        mAuth = FirebaseAuth.getInstance();
        curent_user_id = mAuth.getUid();

        signInState = true;
        signInSignUpButton = findViewById(R.id.coachSignInSignUpButton);
        signInSignUpTextSwitch = findViewById(R.id.coachSignInSignUpClickableTextSwitch);
        signInSignUpTitle = findViewById(R.id.coachSignInSignUpTitle);
        activationCodeEditText = findViewById(R.id.coachSignUpActivationCode);
        activationCodeEditText.setVisibility(View.INVISIBLE);

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
                if (signInState) {
                    // user is signing in (Login)
                    SignInCoach();
                } else {
                    // user is signing up (Registering)
                    SignUpCoach();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // user is already signed in, so go straight to coach profile
        if(curent_user_id == null){
            startActivity(new Intent(this, CoachProfileActivity.class));
            finish();
        }

    }

    private void SignInCoach() {

    }

    private void SignUpCoach() {

    }


}
