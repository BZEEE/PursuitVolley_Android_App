package com.beazle.pursuitvolley.Player.PlayerPaymentFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beazle.pursuitvolley.IntentTags.IntentTags;
import com.beazle.pursuitvolley.Player.PlayerProfile.CurrentAppointments.CurrentAppointmentReceiptParcelable;
import com.beazle.pursuitvolley.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.TimeZone;

public class PlayerCheckoutActivity extends AppCompatActivity {

    private static final String TAG = "PlayerCheckoutActivity";

    private TextView coachNameTextView;
    private TextView selectedDateTextView;
    private TextView selectedTimeTextView;
    private Button continueToPaymentButton;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_checkout);

        mFirestore = FirebaseFirestore.getInstance();

        coachNameTextView = findViewById(R.id.player_checkout_coach_name);
        selectedDateTextView = findViewById(R.id.player_checkout_date);
        selectedTimeTextView = findViewById(R.id.player_checkout_time);
        continueToPaymentButton = findViewById(R.id.continue_to_payment_button);

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));


        CurrentAppointmentReceiptParcelable receipt = getIntent().getParcelableExtra(IntentTags.currentReceiptTAG);
        // set details of player appointment
        SetCheckoutCoachNameDetails(receipt.getCurrentAppointmentCoachUid());
        selectedDateTextView.setText(df.format(receipt.getCurrentAppointmentDate()));
        selectedTimeTextView.setText(receipt.getCurrentAppointmentBeginTime());

        continueToPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToPaymentActivity();
            }
        });
    }

    public void SetCheckoutCoachNameDetails(String coachUniqueId) {
        DocumentReference docRef = mFirestore.collection("coaches").document(coachUniqueId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String coachFullname = (String) document.getData().get("fullname");
                        coachNameTextView.setText(coachFullname);
                    } else {
                        // no such document
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void GoToPaymentActivity() {
        Intent intent = new Intent(this, PlayerPaymentAdyenAcivity.class);
        startActivity(intent);
    }
}
