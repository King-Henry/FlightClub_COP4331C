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

public class CarSearchActivity extends AppCompatActivity {

    static FrameLayout progressBarRoot;

    static EditText carPickUpDate;
    static EditText carDropOffDate;
    EditText carLocation;

    Button carSearchButton;

    private static int numberOfTimesCalled = 0;
    private static int editTextIdentifier = 0;

    ArrayList<Car> cars;

    RecyclerView carSearchResults;

    public static CarSearchResultsAdapter carSearchResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);

        progressBarRoot = (FrameLayout)findViewById(R.id.car_progress_bar_root);
        progressBarRoot.setVisibility(View.GONE);

        carPickUpDate = (EditText)findViewById(R.id.car_pickup_input);
        carDropOffDate = (EditText)findViewById(R.id.car_dropoff_input);
        carLocation = (EditText)findViewById(R.id.car_location);
        carSearchButton = (Button)findViewById(R.id.car_search_button);

        Log.v("SearchActivity OnCreate", " OnCreate is running");

        carPickUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfTimesCalled = 0;
                android.support.v4.app.DialogFragment dialogFragment = new CarSearchActivity.DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        carDropOffDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfTimesCalled = 0;
                editTextIdentifier = 1;
                android.support.v4.app.DialogFragment dialogFragment = new CarSearchActivity.DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        cars = new ArrayList<Car>();

        carSearchResults = (RecyclerView) findViewById(R.id.car_search_results_recycler_view);
        carSearchResults.setVisibility(View.INVISIBLE);
        carSearchResults.setLayoutManager(new LinearLayoutManager(this));
        carSearchResults.setHasFixedSize(true);

       carSearchResultsAdapter = new CarSearchResultsAdapter(cars, this, new CarSearchOnClickListener() {

            @Override
            public void onItemClick(Car car) {

                Toast.makeText(CarSearchActivity.this, "WE'RE IN BIDNESSS", Toast.LENGTH_LONG).show();
            }
        });

        carSearchResults.setAdapter(carSearchResultsAdapter);


        carSearchButton.setOnClickListener(new View.OnClickListener() {
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
                cars.clear();
                populateURLData();
                loadingToResults();
            }
        });
    }


    public void populateURLData(){

        CarSearchResultsAdapter.locationAirportCode = carLocation.getText().toString().substring(0,3);
    }


    public void loadingToResults(){

        Log.v("loadingToResults", "This is called");
        //progressBarRoot = (FrameLayout)findViewById(R.id.progress_bar_root);
        progressBarRoot.setVisibility(View.VISIBLE);


        new CarSearchResultsAdapter.FetchtheCars().execute();

        Log.v("Car Array List empty?", "" + cars.isEmpty());
        // searchResultsAdapter.notifyDataSetChanged();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                progressBarRoot.setVisibility(View.GONE);

                if(carSearchResults.getVisibility() == View.INVISIBLE) {

                    carSearchResults.setVisibility(View.VISIBLE);
                }
            }
        }, 4000);
    }


    public static void setPickUpDate(int year, int month, int dayOfMonth){


        carPickUpDate.setText(month + "/" + dayOfMonth + "/" + year);
    }

    public static void setDropOffDate(int year, int month, int dayOfMonth){


        carDropOffDate.setText(month + "/" + dayOfMonth + "/" + year);
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