package com.teamflightclub.flightclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PrePaymentSystemActivity extends AppCompatActivity {

     Button payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment_system);

        payment = (Button)findViewById(R.id.pay_btn);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoPayment();
            }
        });
    }

    public void gotoPayment(){
        Intent gotoPayment = new Intent(this, PaymentSystemActivity.class);
        startActivity(gotoPayment);
    }
}
