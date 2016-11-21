package com.teamflightclub.flightclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    Button flightsCategory;
    Button hotelsCategory;
    Button carsCategory;
    Button entertainmentCategory;
    Button myCart;
    Button myAccount;
    Button settings;

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



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);

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

