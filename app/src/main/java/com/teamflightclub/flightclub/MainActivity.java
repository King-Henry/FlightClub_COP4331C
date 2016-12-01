package com.teamflightclub.flightclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button flightsCategory;
    Button hotelsCategory;
    Button carsCategory;
    Button entertainmentCategory;
    Button myCart;
    Button myAccount;
    Button settings;

    SharedPreferences sharedPreferences;

    boolean hideSignInButton;
    boolean disableNotLoggedInButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flightsCategory = (Button)findViewById(R.id.flight_category_button);
        hotelsCategory = (Button)findViewById(R.id.hotels_category_button);
        carsCategory = (Button)findViewById(R.id.cars_category_button);
        entertainmentCategory = (Button)findViewById(R.id.entertainment_category_button);
        myCart = (Button)findViewById(R.id.my_cart_bar_button);
        myAccount = (Button)findViewById(R.id.my_account_bar_button);
        settings = (Button)findViewById(R.id.settings_bar_button);

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toMyAccount = new Intent(MainActivity.this, ControlPanelActivity.class);
                startActivity(toMyAccount);
            }
        });
        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toMyCart = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(toMyCart);
            }
        });


        flightsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToFlightSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(goToFlightSearch);
            }
        });


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Log.v("OnCreate", "OnCreate is called");

    }



    @Override
    protected void onResume() {

        super.onResume();

        if(sharedPreferences.contains("userRowID")){ // SharedPreferences contains the row for the user being logged in


            String userID = sharedPreferences.getString("userRowID",""); // get the value for if user is logged in(true or false)

            Log.v("userRowID", " " + userID);

            if(!sharedPreferences.getString("userRowID","").isEmpty()){ //if user logged in

                hideSignInButton = true;
                invalidateOptionsMenu(); // launch Menu Android methods so that you can disable Sign In menu button
                myCart.setEnabled(true); //enable My Cart button
                myAccount.setEnabled(true); //enable My Account button
            }

            else{

                disableNotLoggedInButtons = true;
                hideSignInButton = false;
                invalidateOptionsMenu();
                myCart.setEnabled(false); // disable My Cart button
                myAccount.setEnabled(false); //disable My Account button
            }
        }

        else{ //if key for user login state is not in sharedPreferences

            sharedPreferences.edit().putString("userRowID","").commit(); //add key and boolean value to SharedPreferences
            myCart.setEnabled(false); // disable My Cart button
            myAccount.setEnabled(false); //disable My Account button
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Log.v("onPrepareOptionMenus", "has been called");
        if(hideSignInButton){ //

            menu.getItem(0).setEnabled(false); // hide sign in button

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()){

            case R.id.sign_in_edit_button:

                Intent goToSignInActivity = new Intent(this,LoginActivity.class);
                startActivity(goToSignInActivity);

            default:

                return super.onOptionsItemSelected(item);
            }

        }

}

