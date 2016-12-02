package com.teamflightclub.flightclub;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Carrito on 12/2/2016.
 */

public class ViewPurchasedTickets extends AppCompatActivity {

    Button payment;
    RecyclerView purchasedItems;
    ArrayList<Reservation> purchases;
    private String userId;

    public static ViewPurchasedTicketsAdapter viewPurchasedTicketsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment_system2);

        Log.v("PrePaymentSystem", " OnCreate is running");

        payment = (Button)findViewById(R.id.pay_button);

        purchases = new ArrayList<Reservation>();
        userId = PreferenceManager.getDefaultSharedPreferences(this).getString(("userRowID"), "");

        purchasedItems = (RecyclerView) findViewById(R.id.cart_items_recycler_view);
        //purchasedItems.setVisibility(View.INVISIBLE);
        purchasedItems.setLayoutManager(new LinearLayoutManager(this));
        purchasedItems.setHasFixedSize(true);
        viewPurchasedTicketsAdapter = new ViewPurchasedTicketsAdapter(purchases, this);
        purchasedItems.setAdapter(viewPurchasedTicketsAdapter);
        payment.setVisibility(View.INVISIBLE);
//        payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                gotoPayment();
//            }
//        });
        new ViewPurchasedTicketsAdapter.FetchthePurchases().execute(userId);
    }

}
