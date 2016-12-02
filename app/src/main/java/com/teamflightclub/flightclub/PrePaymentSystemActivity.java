package com.teamflightclub.flightclub;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teamflightclub.flightclub.model.ListItem;

import java.util.ArrayList;

public class PrePaymentSystemActivity extends AppCompatActivity {

    Button payment;
    RecyclerView cartItems;
    ArrayList<Reservation> cartProducts;
    private String userId;

    public static PrePaymentSystemAdapter prePaymentSystemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment_system2);

        Log.v("PrePaymentSystem", " OnCreate is running");

        payment = (Button)findViewById(R.id.pay_button);

        cartProducts = new ArrayList<Reservation>();
        userId = PreferenceManager.getDefaultSharedPreferences(this).getString(("userRowID"), "");

        cartItems = (RecyclerView) findViewById(R.id.cart_items_recycler_view);
        //cartItems.setVisibility(View.INVISIBLE);
        cartItems.setLayoutManager(new LinearLayoutManager(this));
        cartItems.setHasFixedSize(true);
        prePaymentSystemAdapter = new PrePaymentSystemAdapter(cartProducts, this);
        cartItems.setAdapter(prePaymentSystemAdapter);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoPayment();
            }
        });
        new PrePaymentSystemAdapter.FetchtheCart().execute(userId);
    }

    public void gotoPayment(){
        Intent gotoPayment = new Intent(this, PaymentSystemActivity.class);
        startActivity(gotoPayment);
    }
}
