package com.beazle.pursuitvolley.Player.PlayerPaymentFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beazle.pursuitvolley.DebugTags.DebugTags;
import com.beazle.pursuitvolley.FirebaseFirestoreTags.FirestoreTags;
import com.beazle.pursuitvolley.IntentTags.IntentTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.PlayerAppointmentReceiptParcelable;
import com.beazle.pursuitvolley.R;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class PlayerCheckoutActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 3000;
    private Map paymentParamsMap;

    private TextView coachNameTextView;
    private TextView selectedDateTextView;
    private TextView selectedTimeTextView;
    private Button continueToPaymentButton;

    private FirebaseFirestore mFirestore;
    private FirebaseFunctions mFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_checkout);

        mFirestore = FirebaseFirestore.getInstance();
        mFunctions = FirebaseFunctions.getInstance();

        coachNameTextView = findViewById(R.id.player_checkout_coach_name);
        selectedDateTextView = findViewById(R.id.player_checkout_date);
        selectedTimeTextView = findViewById(R.id.player_checkout_time);
        continueToPaymentButton = findViewById(R.id.continue_to_payment_button);

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));


        PlayerAppointmentReceiptParcelable receipt = getIntent().getParcelableExtra(IntentTags.currentReceiptTAG);
        // set details of player appointment
        SetCheckoutCoachNameDetails(receipt.getCurrentAppointmentCoachUid());
        selectedDateTextView.setText(df.format(receipt.getCurrentAppointmentDate()));
        selectedTimeTextView.setText(receipt.getCurrentAppointmentBeginTime());

        continueToPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakePayment();
            }
        });
    }

    public void SetCheckoutCoachNameDetails(String coachUniqueId) {
        DocumentReference docRef = mFirestore.collection(FirestoreTags.coachCollection).document(coachUniqueId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String coachFullname = (String) document.getData().get(FirestoreTags.coachDocumentFullname);
                        coachNameTextView.setText(coachFullname);
                    } else {
                        // no such document
                        Log.d(DebugTags.DebugTAG, "No such document");
                    }
                } else {
                    Log.d(DebugTags.DebugTAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void SubmitBrainTreePayment(String clientToken) {
        // initialize drop in UI for user to enter and select card information
        DropInRequest dropInRequest = new DropInRequest()
                // .clientToken(clientToken)
                // temporary string to test if integration worked
                // Ideally we'll be generating a token on our server and passing it to the client to encrypt credit card information
                // which will then be passed to the brain tree server to actually process the payment
                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJleUowZVhBaU9pSktWMVFpTENKaGJHY2lPaUpGVXpJMU5pSXNJbXRwWkNJNklqSXdNVGd3TkRJMk1UWXRjMkZ1WkdKdmVDSjkuZXlKbGVIQWlPakUxTmpVeE9UTTBNRGtzSW1wMGFTSTZJbU14TkRSbU1qQTFMVGd4TWpFdE5HTXpPUzA0WldaaExUa3haVFZqWWpkbVltWTVNaUlzSW5OMVlpSTZJak0wT0hCck9XTm5aak5pWjNsM01tSWlMQ0pwYzNNaU9pSkJkWFJvZVNJc0ltMWxjbU5vWVc1MElqcDdJbkIxWW14cFkxOXBaQ0k2SWpNME9IQnJPV05uWmpOaVozbDNNbUlpTENKMlpYSnBabmxmWTJGeVpGOWllVjlrWldaaGRXeDBJanBtWVd4elpYMHNJbkpwWjJoMGN5STZXeUp0WVc1aFoyVmZkbUYxYkhRaVhTd2liM0IwYVc5dWN5STZlMzE5LmFlT3VLRk02ZlZLRzI4eUxGOWl2LXFuSzRIMEttaENwemNsTkRjSlJkQ0FzdUdGb3MzZWhEVFRfUFo1OU5iSUZrODQ4YlFrZ1g0WE9BRUJfbzB4MF9RIiwiY29uZmlnVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaS92MS9jb25maWd1cmF0aW9uIiwiZ3JhcGhRTCI6eyJ1cmwiOiJodHRwczovL3BheW1lbnRzLnNhbmRib3guYnJhaW50cmVlLWFwaS5jb20vZ3JhcGhxbCIsImRhdGUiOiIyMDE4LTA1LTA4In0sImNoYWxsZW5nZXMiOltdLCJlbnZpcm9ubWVudCI6InNhbmRib3giLCJjbGllbnRBcGlVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhdXRoVXJsIjoiaHR0cHM6Ly9hdXRoLnZlbm1vLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhbmFseXRpY3MiOnsidXJsIjoiaHR0cHM6Ly9vcmlnaW4tYW5hbHl0aWNzLXNhbmQuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS8zNDhwazljZ2YzYmd5dzJiIn0sInRocmVlRFNlY3VyZUVuYWJsZWQiOnRydWUsInBheXBhbEVuYWJsZWQiOnRydWUsInBheXBhbCI6eyJkaXNwbGF5TmFtZSI6IkFjbWUgV2lkZ2V0cywgTHRkLiAoU2FuZGJveCkiLCJjbGllbnRJZCI6bnVsbCwicHJpdmFjeVVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS9wcCIsInVzZXJBZ3JlZW1lbnRVcmwiOiJodHRwOi8vZXhhbXBsZS5jb20vdG9zIiwiYmFzZVVybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9jaGVja291dC5wYXlwYWwuY29tIiwiZGlyZWN0QmFzZVVybCI6bnVsbCwiYWxsb3dIdHRwIjp0cnVlLCJlbnZpcm9ubWVudE5vTmV0d29yayI6dHJ1ZSwiZW52aXJvbm1lbnQiOiJvZmZsaW5lIiwidW52ZXR0ZWRNZXJjaGFudCI6ZmFsc2UsImJyYWludHJlZUNsaWVudElkIjoibWFzdGVyY2xpZW50MyIsImJpbGxpbmdBZ3JlZW1lbnRzRW5hYmxlZCI6dHJ1ZSwibWVyY2hhbnRBY2NvdW50SWQiOiJhY21ld2lkZ2V0c2x0ZHNhbmRib3giLCJjdXJyZW5jeUlzb0NvZGUiOiJVU0QifSwibWVyY2hhbnRJZCI6IjM0OHBrOWNnZjNiZ3l3MmIiLCJ2ZW5tbyI6Im9mZiJ9");
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();

                // drop in is successful now send nonce to brain tree server for payment process
                // pass amount to the nonce
                paymentParamsMap = new HashMap<>();
                // paymentParamsMap.put("amount", amount)
                paymentParamsMap.put("nonce", strNonce);
                // go to results activity to confirm payment success or failure

                // send confirmation email if payment and appointment booking is successful
                // PursuitVolleyEmailManager.ComposeEmail(this, addresses, subject, bodyText);
                // Or
                // send text message confirmation based on how we want to use the user's data
                // PursuitVolleySMSManager.SendSMSMessage(this, "7807211470", "whatsup home slice");
            } else if (resultCode == RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    private Task<String> GetClientToken() {
        // generate a client token on our server and have its value returned
        // we call the cloud function from the client side

        return mFunctions
                .getHttpsCallable("ReturnClientToken")
                .call()
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }

    private void MakePayment() {
        // generate a client token asynchronously as the activity is loaded
        // when a client token is returned, allow the user to continue to the drop-in payment screen
        // drop-in payment screen does not need its own activity, since it exposed as an API from brain tree
        GetClientToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            // client token successfully returned
                            // make progress bar invisible
                            String clientToken = task.getResult();
                            // check if a token is actually returned
                            Toast.makeText(PlayerCheckoutActivity.this, clientToken, Toast.LENGTH_SHORT).show();
                            SubmitBrainTreePayment(clientToken);

                        } else {
                            // error occurred when getting client token
                            Log.d(DebugTags.DebugTAG, "failed toget client token from server");
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                            }
                        }

                    }
                });

    }
}
