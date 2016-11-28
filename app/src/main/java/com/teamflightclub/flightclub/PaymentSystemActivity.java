package com.teamflightclub.flightclub;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.teamflightclub.flightclub.library.CardValidCallback;
import com.teamflightclub.flightclub.library.CreditCard;
import com.teamflightclub.flightclub.library.CreditCardForm;

public class PaymentSystemActivity extends Activity {
    private static final String TAG = "SimpleActivity";

    CardValidCallback cardValidCallback = new CardValidCallback() {
        @Override
        public void cardValid(CreditCard card) {
            Log.d(TAG, "valid card: " + card);
            Toast.makeText(PaymentSystemActivity.this, "Card valid and complete", Toast.LENGTH_SHORT).show();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_system);

        final CreditCardForm zipForm = (CreditCardForm) findViewById(R.id.form_with_zip);
        zipForm.setOnCardValidCallback(cardValidCallback);
    }

}
