package com.beazle.pursuitvolley.Player.PlayerPaymentFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beazle.pursuitvolley.R;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

public class PlayerCreditCardPaymentAcivity extends AppCompatActivity {
    private Stripe mStripe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_credit_card_payment_acivity);

        final CardInputWidget mCardInputWidget = findViewById(R.id.cardInputWidget);
        Button checkoutButton = findViewById(R.id.goToCheckoutButton);

        // To use the PaymentIntent, you need to make its client secret accessible to your
        // mobile application. Typically, the best approach is to serve it from an HTTP
        // endpoint on your server and then retrieve it on the client side.
        // https://stripe.com/docs/payments/payment-intents/android
        PaymentConfiguration.init("pk_test_TYooMQauvdEDq54NiTphI7jx");
        mStripe = new Stripe(this,
                PaymentConfiguration.getInstance().getPublishableKey());
        // now retrieve the PaymentIntent that was created on your backend

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckCardValidity(mCardInputWidget);
            }
        });


    }

    private void CheckCardValidity(CardInputWidget mCardInputWidget) {
        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
            Toast.makeText(this, "Invalid Card Data", Toast.LENGTH_SHORT).show();
        } else {
            // card is valid
            // add additional information to player's card'
            AddAdditionalInformationToPlayersCard(cardToSave);
            TokenizeCardInformation(cardToSave);
            startActivity(new Intent(this, PlayerPaymentResultActivity.class));
        }
    }

    private void AddAdditionalInformationToPlayersCard(Card cardToSave) {
        // add the customers name to the card, if we want, otherwise no need to add additionl info
        cardToSave = cardToSave.toBuilder().name("Customer Name").build();
    }

    private void TokenizeCardInformation(Card card) {
        Stripe stripe = new Stripe(this, "pk_test_TYooMQauvdEDq54NiTphI7jx");
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(@NonNull Token token) {
                        // send token id to your server for charge creation
                        CreateCharge(token.getId());
                    }
                    public void onError(@NonNull Exception error) {
                        // Show localized error message
                        Toast.makeText(PlayerCreditCardPaymentAcivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void CreateCharge(String id) {
        // send the returned token id to our server

//        final String tokenId = id;
//
//        final Map<String, Object> params = new HashMap<>();
//        params.put("amount", 999);
//        params.put("currency", "usd");
//        params.put("description", "Example charge");
//        params.put("source", tokenId);
//        Charge charge = Charge.create(params);
    }


}
