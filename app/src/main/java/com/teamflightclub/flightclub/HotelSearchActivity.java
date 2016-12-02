package com.teamflightclub.flightclub;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static com.teamflightclub.flightclub.SearchActivity.searchResultsAdapter;

public class HotelSearchActivity extends AppCompatActivity {

    static FrameLayout progressBarRoot;

    static EditText hotelCheckInDate;
    static EditText hotelCheckOutDate;
    EditText hotelLocation;

    Button hotelSearchButton;

    private static int numberOfTimesCalled = 0;
    private static int editTextIdentifier = 0;

    ArrayList<Hotel> hotels;

    RecyclerView hotelSearchResults;

    public static HotelSearchResultsAdapter hotelSearchResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);

        progressBarRoot = (FrameLayout)findViewById(R.id.car_progress_bar_root);
        progressBarRoot.setVisibility(View.GONE);

        hotelCheckInDate = (EditText)findViewById(R.id.car_pickup_input);
        hotelCheckOutDate = (EditText)findViewById(R.id.car_dropoff_input);
        hotelLocation = (EditText)findViewById(R.id.car_location);
        hotelSearchButton = (Button)findViewById(R.id.car_search_button);

        Log.v("SearchActivity OnCreate", " OnCreate is running");

        hotelCheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfTimesCalled = 0;
                android.support.v4.app.DialogFragment dialogFragment = new HotelSearchActivity.DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        hotelCheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfTimesCalled = 0;
                editTextIdentifier = 1;
                android.support.v4.app.DialogFragment dialogFragment = new HotelSearchActivity.DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        hotels = new ArrayList<Hotel>();

        hotelSearchResults = (RecyclerView) findViewById(R.id.car_search_results_recycler_view);
        hotelSearchResults.setVisibility(View.INVISIBLE);
        hotelSearchResults.setLayoutManager(new LinearLayoutManager(this));
        hotelSearchResults.setHasFixedSize(true);

        hotelSearchResultsAdapter = new HotelSearchResultsAdapter(hotels, this, new HotelSearchOnClickListener() {

            @Override
            public void onItemClick(Hotel hotel) {

                Toast.makeText(HotelSearchActivity.this, "WE'RE IN BIDNESSS", Toast.LENGTH_LONG).show();
            }
        });

        hotelSearchResults.setAdapter(hotelSearchResultsAdapter);


        hotelSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("Search Button", "has been clicked");
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    Log.v("close keyboard", "closing keyboard??");
                } catch (Exception e) {

                    //do nothing
                }
                hotels.clear();
                populateURLData();
                loadingToResults();
            }
        });
    }


    public void populateURLData(){

        HotelSearchResultsAdapter.locationAirportCode = hotelLocation.getText().toString().substring(0,3);
    }


    public void loadingToResults(){

        Log.v("loadingToResults", "This is called");
        //progressBarRoot = (FrameLayout)findViewById(R.id.progress_bar_root);
        progressBarRoot.setVisibility(View.VISIBLE);


        new HotelSearchResultsAdapter.FetchtheHotels().execute();
        Log.v("Car Array List empty?", "" + hotels.isEmpty());
        // searchResultsAdapter.notifyDataSetChanged();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                progressBarRoot.setVisibility(View.GONE);

                if(hotelSearchResults.getVisibility() == View.INVISIBLE) {

                    hotelSearchResults.setVisibility(View.VISIBLE);
                }
            }
        }, 4000);
    }


    public static void setPickUpDate(int year, int month, int dayOfMonth){


        hotelCheckInDate.setText(month + "/" + dayOfMonth + "/" + year);
    }

    public static void setDropOffDate(int year, int month, int dayOfMonth){


        hotelCheckOutDate.setText(month + "/" + dayOfMonth + "/" + year);
    }


    public static class DatePickerFragment extends android.support.v4.app.DialogFragment
            implements  DatePickerDialog.OnDateSetListener {


        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            int year = calendar.get(java.util.Calendar.YEAR);
            int month = calendar.get(java.util.Calendar.MONTH);
            int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);

        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            if (numberOfTimesCalled == 0) {

                if (editTextIdentifier == 0) {

                    setPickUpDate(year, month + 1, dayOfMonth);
                    Log.v("Setting Departure Date", "we're here");
                } else if (editTextIdentifier == 1) {
                    Log.v("Setting return date", "We're here");
                    setDropOffDate(year, month + 1, dayOfMonth);
                }

                editTextIdentifier = 0;
            }
            numberOfTimesCalled++;
        }

    }
}