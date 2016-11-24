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
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public String rowID = "";

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

    private static int SIGN_IN_REQUEST = 1000;

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

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Log.v("OnCreate", "OnCreate is called");

    }



    @Override
    protected void onResume() {

        super.onResume();



        if(sharedPreferences.contains("isUserLoggedIn")){ // SharedPreferences contains the key for the user being logged in


            boolean userLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false); // get the value for if user is logged in(true or false)

            Log.v("userLoggedIn value", " " + userLoggedIn);

            if(userLoggedIn){ //if user logged in

                hideSignInButton = true;
                invalidateOptionsMenu(); // launch Menu Android methods so that you can disable Sign In menu button
                myCart.setEnabled(true); //enable My Cart button
                myAccount.setEnabled(true); //enable My Account button
            }

            else{

                disableNotLoggedInButtons = true;
                myCart.setEnabled(false); // disable My Cart button
                myAccount.setEnabled(false); //disable My Account button
            }
        }

        else{ //if key for user login state is not in sharedPreferences

            sharedPreferences.edit().putBoolean("isUserLoggedIn",false).commit(); //add key and boolean value to SharedPreferences
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
                startActivityForResult(goToSignInActivity,SIGN_IN_REQUEST);

            default:

                return super.onOptionsItemSelected(item);
            }

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.v("onActivityResult", "is this called?");

        if(requestCode == SIGN_IN_REQUEST){

            if(resultCode == RESULT_OK){

                sharedPreferences.edit().putBoolean("isUserLoggedIn", true).commit();

                Log.v("onActivityResult", "Was preference updated?");
            }
        }
    }

}

