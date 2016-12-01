package com.teamflightclub.flightclub;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.teamflightclub.flightclub.SearchResultsAdapter.flights;

public class TicketDetailActivity extends AppCompatActivity {

    CardView leg_one_view;
    CardView leg_two_view;
    CardView leg_three_view;

    TextView flight_name_and_number_leg_one;
    TextView departure_time_leg_one;
    TextView from_to_city_leg_one;
    TextView price_leg_one;
    TextView arrival_time_leg_one;

    TextView flight_name_and_number_leg_two;
    TextView departure_time_leg_two;
    TextView from_to_city_leg_two;
    TextView price_leg_two;
    TextView arrival_time_leg_two;

    TextView flight_name_and_number_leg_three;
    TextView departure_time_leg_three;
    TextView from_to_city_leg_three;
    TextView price_leg_three;
    TextView arrival_time_leg_three;

    Button add_to_cart_button;
    TextView sign_in_text;

    Boolean hideSignInButton = false;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        Bundle bundle = getIntent().getExtras();
        Flight flight = flights.get(bundle.getInt("FlightItemPosition"));
        //Toast.makeText(this, flight.arrivalTime + flight.departureAirportLocation + flight.arrivalAirportLocation, Toast.LENGTH_LONG).show();

        add_to_cart_button = (Button)findViewById(R.id.add_to_cart_button);
        sign_in_text = (TextView)findViewById(R.id.sign_in_text);

        add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        leg_one_view = (CardView)findViewById(R.id.leg_one_detail_cardview);
        flight_name_and_number_leg_one = (TextView)findViewById(R.id.leg_one_airline_name_and_flight_number);
        departure_time_leg_one = (TextView)findViewById(R.id.leg_one_departure_city_and_time);
        from_to_city_leg_one = (TextView)findViewById(R.id.leg_one_flight_duration_and_distance);
        price_leg_one = (TextView)findViewById(R.id.leg_one_trip_price_red);
        arrival_time_leg_one = (TextView)findViewById(R.id.leg_one_arrival_city_and_time);

        flight_name_and_number_leg_one.setText(flight.airlineName + " " + flight.flightNumber);
        departure_time_leg_one.setText(flight.departureAirportLocation + "\n" + flight.departureTime + "\n" + flight.departureDate);
        from_to_city_leg_one.setText(flight.duration + " ," + flight.distance + "miles");
        price_leg_one.setText("$" + flight.price);
        arrival_time_leg_one.setText(flight.arrivalAirportLocation + "\n" + flight.arrivalTime + "\n" + flight.arrivalDate);

        if(flight.secondLeg != null){

            Flight flightTwo = flight.secondLeg;
            leg_two_view = (CardView)findViewById(R.id.leg_two_detail_cardview);
            leg_two_view.setVisibility(View.VISIBLE);
            flight_name_and_number_leg_two = (TextView)findViewById(R.id.leg_two_airline_name_and_flight_number);
            departure_time_leg_two = (TextView)findViewById(R.id.leg_two_departure_city_and_time);
            from_to_city_leg_two = (TextView)findViewById(R.id.leg_two_flight_duration_and_distance);
            price_leg_two = (TextView)findViewById(R.id.leg_two_trip_price_red);
            arrival_time_leg_two = (TextView)findViewById(R.id.leg_two_arrival_city_and_time);

            leg_two_view = (CardView)findViewById(R.id.leg_two_detail_cardview);
            flight_name_and_number_leg_two = (TextView)findViewById(R.id.leg_two_airline_name_and_flight_number);
            departure_time_leg_two = (TextView)findViewById(R.id.leg_two_departure_city_and_time);
            from_to_city_leg_two = (TextView)findViewById(R.id.leg_two_flight_duration_and_distance);
            price_leg_two = (TextView)findViewById(R.id.leg_two_trip_price_red);
            arrival_time_leg_two = (TextView)findViewById(R.id.leg_two_arrival_city_and_time);

            flight_name_and_number_leg_two.setText(flightTwo.airlineName + " " + flightTwo.flightNumber);
            departure_time_leg_two.setText(flightTwo.departureAirportLocation + "\n" + flightTwo.departureTime + "\n" + flightTwo.departureDate);
            from_to_city_leg_two.setText(flightTwo.duration + " ," + flightTwo.distance + "miles");
            price_leg_two.setText("$" + flightTwo.price);
            arrival_time_leg_two.setText(flightTwo.arrivalAirportLocation + "\n" + flightTwo.arrivalTime + "\n" + flightTwo.arrivalDate);

        }

        if(flight.thirdLeg != null){

            Flight flightThree = flight.thirdLeg;

            leg_three_view = (CardView)findViewById(R.id.leg_three_detail_cardview);
            leg_three_view.setVisibility(View.VISIBLE);
            flight_name_and_number_leg_three = (TextView)findViewById(R.id.leg_three_airline_name_and_flight_number);
            departure_time_leg_three = (TextView)findViewById(R.id.leg_three_departure_city_and_time);
            from_to_city_leg_three = (TextView)findViewById(R.id.leg_three_flight_duration_and_distance);
            price_leg_three = (TextView)findViewById(R.id.leg_three_trip_price_red);
            arrival_time_leg_three = (TextView)findViewById(R.id.leg_three_arrival_city_and_time);

            leg_three_view = (CardView)findViewById(R.id.leg_three_detail_cardview);
            flight_name_and_number_leg_three = (TextView)findViewById(R.id.leg_three_airline_name_and_flight_number);
            departure_time_leg_three = (TextView)findViewById(R.id.leg_three_departure_city_and_time);
            from_to_city_leg_three = (TextView)findViewById(R.id.leg_three_flight_duration_and_distance);
            price_leg_three = (TextView)findViewById(R.id.leg_three_trip_price_red);
            arrival_time_leg_three = (TextView)findViewById(R.id.leg_three_arrival_city_and_time);

            flight_name_and_number_leg_three.setText(flightThree.airlineName + " " + flightThree.flightNumber);
            departure_time_leg_three.setText(flightThree.departureAirportLocation + "\n" + flightThree.departureTime + "\n" + flightThree.departureDate);
            from_to_city_leg_three.setText(flightThree.duration + " ," + flightThree.distance + "miles");
            price_leg_three.setText("$" + flightThree.price);
            arrival_time_leg_three.setText(flightThree.arrivalAirportLocation + "\n" + flightThree.arrivalTime + "\n" + flightThree.arrivalDate);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        String userRowID = PreferenceManager.getDefaultSharedPreferences(this).getString("userRowID","");

        if(userRowID.isEmpty()){

            add_to_cart_button.setEnabled(false);
            sign_in_text.setVisibility(View.VISIBLE);
        }

        else{

            hideSignInButton = true;
            invalidateOptionsMenu();
            add_to_cart_button.setEnabled(true);
            sign_in_text.setVisibility(View.GONE);

        }
    }
}