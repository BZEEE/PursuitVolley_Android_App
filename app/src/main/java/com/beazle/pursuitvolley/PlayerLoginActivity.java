package com.beazle.pursuitvolley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerLoginActivity extends AppCompatActivity {

    public static final String TAG = "PlayerLoginActivityTAG";
    public static final String googleSignInAccountId = "GoogleSignInAccount";
    private static final int PURSUIT_VOLLEY_REQUEST_CODE = 9001;
    private FirebaseUser currentUser;
    // Firebase Auth Object.
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private CallbackManager mCallbackManager;
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
        currentUser = mAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        AuthenticateUser();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            startActivity(new Intent(this, CoachSelectionActivity.class));
            finish();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PURSUIT_VOLLEY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Authentication is completed
                //ccurrent user is no longer null
                FirebaseUser user = mAuth.getCurrentUser();
                AddDefaultCoachDataToFirestoreAfterSigningUp(user);
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

    private void AddDefaultCoachDataToFirestoreAfterSigningUp(final FirebaseUser user) {
        DocumentReference docRef = mFirestore.collection("players").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists()) {
                        // if player document does not exist, then add the default field data
                        Map<String, Object> data = new HashMap<>();
                        data.put("tokens", 0);
                        mFirestore.collection("players").document(user.getUid()).set(data);
                    }
                } else {
                    // task is unsuccessful
                }
            }
        });
    }
}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_player_login);
//
//        // LoginButton loginButton = findViewById(R.id.facebookSignInButton);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        currentUser = FirebaseAuth.getInstance().getCurrentUser();


//        mCallbackManager = CallbackManager.Factory.create();
//        loginButton.setReadPermissions("email");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                Log.i(TAG,"Hello"+loginResult.getAccessToken().getToken());
//                //  Toast.makeText(MainActivity.this, "Token:"+loginResult.getAccessToken(), Toast.LENGTH_SHORT).show();
//
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });
//
//
//        mAuthListener = new FirebaseAuth.AuthStateListener(){
//
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                if (user!=null){
//                    String name = user.getDisplayName();
//                    Toast.makeText(PlayerLoginActivity.this,""+user.getDisplayName(),Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(PlayerLoginActivity.this,"something went wrong",Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//        };

//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (currentUser != null) {
//            startActivity(new Intent(this, CoachSelectionActivity.class));
//            finish();
//            return;
//        }
//    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result back to the Facebook SDK
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if (task.isSuccessful()) {
//                            Log.w(TAG, "signInWithCredential", task.getException());
//                            Toast.makeText(PlayerLoginActivity.this, "Success",
//                                    Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(PlayerLoginActivity.this, "Authentication error",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                    }
//                });
//    }
