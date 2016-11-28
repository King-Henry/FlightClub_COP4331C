package com.teamflightclub.flightclub;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

public class SearchActivity extends AppCompatActivity {

    AutoCompleteTextView airportDeparture;
    AutoCompleteTextView airportArrival;
    static EditText departureDate;
    static EditText returnDate;
    EditText numOfTickets;
    Button searchButton;
    RecyclerView searchResults;

    FrameLayout spinningLoaderRoot;

    public static int editTextIdentifier = 0;
    public static int numberOfTimesCalled = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        airportDeparture = (AutoCompleteTextView)findViewById(R.id.departure_airport_input);
        airportArrival = (AutoCompleteTextView)findViewById(R.id.arrival_airport_input);
        returnDate = (EditText)findViewById(R.id.flight_return_date);
        departureDate = (EditText)findViewById(R.id.flight_departure_date);
        searchButton = (Button)findViewById(R.id.search_button);
        numOfTickets = (EditText)findViewById(R.id.number_of_tickets);

        numOfTicketsFormatter();

        String[] airports = getResources().getStringArray(R.array.airports_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,airports);
        airportDeparture.setAdapter(adapter);
        airportArrival.setAdapter(adapter);


        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfTimesCalled = 0;
                android.support.v4.app.DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),"datePicker");

            }
        });


        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfTimesCalled = 0;
                editTextIdentifier = 1;
                android.support.v4.app.DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        searchResults = (RecyclerView)findViewById(R.id.search_results_recycler_view);
        searchResults.setVisibility(View.INVISIBLE);
        searchResults.setLayoutManager(new LinearLayoutManager(this));
        searchResults.setHasFixedSize(true);
        SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter();
        searchResults.setAdapter(searchResultsAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("Search Button", "has been clicked");
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                    Log.v("close keyboard", "closing keyboard??");
                }catch(Exception e){

                    //do nothing
                }
                loadingToResults();
            }
        });

    }

    public void loadingToResults(){

        Log.v("loadingToResults", "This is called");
        spinningLoaderRoot = (FrameLayout)findViewById(R.id.progress_bar_root);
        spinningLoaderRoot.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spinningLoaderRoot.setVisibility(View.GONE);

                if(searchResults.getVisibility() == View.INVISIBLE) {

                    searchResults.setVisibility(View.VISIBLE);
                }
            }
        }, 4000);
    }

    public void numOfTicketsFormatter(){

        numOfTickets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                numOfTickets.setGravity(Gravity.RIGHT);
            }
        });
    }


    public static void setDepartureDate(int year, int month, int dayOfMonth){

        departureDate.setText(month + "/" + dayOfMonth + "/" + year);
    }

    public static void setReturnDate(int year, int month, int dayOfMonth){

        returnDate.setText(month + "/" + dayOfMonth + "/" + year);
    }



    public static class DatePickerFragment extends android.support.v4.app.DialogFragment
            implements DatePickerDialog.OnDateSetListener {


            public Dialog onCreateDialog(Bundle savedInstanceState){

                final java.util.Calendar calendar = java.util.Calendar.getInstance();
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH);
                int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(getActivity(),this,year,month,day);

            }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            if (numberOfTimesCalled == 0) {

                if (editTextIdentifier == 0) {

                    setDepartureDate(year, month + 1, dayOfMonth);
                    Log.v("Setting Departure Date", "we're here");
                } else if (editTextIdentifier == 1) {
                    Log.v("Setting return date", "We're here");
                    setReturnDate(year, month + 1, dayOfMonth);
                }

                editTextIdentifier = 0;
            }
            numberOfTimesCalled++;
        }
    }
}
