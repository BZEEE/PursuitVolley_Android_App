package com.beazle.pursuitvolley.Player.PlayerPaymentFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.adyen.threeds2.ProgressDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beazle.pursuitvolley.R;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class PlayerPaymentBrainTreeActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 3000;

    private Map paymentParamsMap;
    private FirebaseFunctions mFunctions;
    private String clientToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_payment_brain_tree);

        // new RequestClientToken().execute();
        // call a cloud function to return a client token used for securely processing the user's payment
        // this.clientToken = GetClientToken()
    }

    @Override
    protected void onStart() {
        super.onStart();

        GetClientToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            // client token successfully returned
                            // make progress bar invisible
                            String clientToken = task.getResult();
                            // check if a token is actually returned
                            Toast.makeText(PlayerPaymentBrainTreeActivity.this, clientToken, Toast.LENGTH_SHORT).show();
                            SubmitBrainTreePayment(clientToken);

                        } else {
                            // error occurred when getting client token
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
                // send payments
                // SendPayments();
                // use the result to update your UI and send the payment method nonce to your server
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

//    private class RequestClientToken extends AsyncTask {
//
//        private ProgressDialog mDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mDialog = new ProgressDialog() {
//                @Override
//                public void show() {
//
//                }
//
//                @Override
//                public void hide() {
//
//                }
//            };
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//        }
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            // call an HTTP request function to the server to
//            // generate a client token
//            // receive the token from the firebase server in a callback
//            // generate a client token once per checkout session
//
//            // get using callable cloud function
//            HttpClient client = new HttpClient();
//            client.get(clientAPIRequestToken, new HttpResponseCallback() {
//                @Override
//                public void success(final String responseBody) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            // hide the waiting UI response
//                            // show the credit entry widgets (payment edit boxes)
//                            token = responseBody;
//                        }
//                    });
//                }
//
//                @Override
//                public void failure(Exception exception) {
//                    // show failure info
//                }
//            });
//            return null;
//        }
//    }
}
