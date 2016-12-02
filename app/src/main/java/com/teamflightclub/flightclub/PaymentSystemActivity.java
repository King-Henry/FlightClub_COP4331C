package com.teamflightclub.flightclub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamflightclub.flightclub.library.CardValidCallback;
import com.teamflightclub.flightclub.library.CreditCard;
import com.teamflightclub.flightclub.library.CreditCardForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static com.teamflightclub.flightclub.R.id.spinner;
import static com.teamflightclub.flightclub.R.id.spinner2;

public class PaymentSystemActivity extends Activity implements OnItemSelectedListener {
    private static final String TAG = "SimpleActivity";
    EditText firstName;
    EditText lastName;
    EditText address;
    ArrayList<String> countries;
    ArrayList<String> statesName;
    Spinner citizenship;
    Spinner states;
    TextView statesText;
    Button payment;
    boolean q=false;
    boolean textPaymentPermission = false;
    boolean buttonPaymentPermission = false;
    boolean countryPaymentPermission = false;
    CardValidCallback cardValidCallback = new CardValidCallback() {
        @Override
        public void cardValid(CreditCard card) {


            Log.d(TAG, "valid card: " + card);
            Toast.makeText(PaymentSystemActivity.this, "Card valid", Toast.LENGTH_SHORT).show();
            buttonPaymentPermission = true;

            if(!firstName.getText().toString().equals("")
                    && !lastName.getText().toString().equals("") && textPaymentPermission && countryPaymentPermission){

                payment.setEnabled(true);
            }
            else{
                payment.setEnabled(false);
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {

        Locale[] locale = Locale.getAvailableLocales();
        countries = new ArrayList<>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }

        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_system);
        statesName = new ArrayList<>();
        statesName = getStates(statesName);
        citizenship = (Spinner)findViewById(spinner);

        statesText = (TextView)findViewById(R.id.textView4);
        citizenship.setOnItemSelectedListener(this);

        states = (Spinner)findViewById(spinner2);
        states.setOnItemSelectedListener(this);

        firstName = (EditText)findViewById(R.id.editText2);
        lastName = (EditText)findViewById(R.id.editText3);
        address = (EditText)findViewById(R.id.editText5);
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!firstName.getText().toString().equals("") && !lastName.getText().toString().equals("")){
                    textPaymentPermission = true;
                    if (buttonPaymentPermission && countryPaymentPermission) {
                        payment.setEnabled(true);
                    }
                }
                else{
                    payment.setEnabled(false);
                }

                return;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, countries);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citizenship.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, statesName);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        states.setAdapter(dataAdapter2);

        final CreditCardForm zipForm = (CreditCardForm) findViewById(R.id.form_with_zip);
        zipForm.setOnCardValidCallback(cardValidCallback);
        payment = (Button)findViewById(R.id.pay);
        payment.setEnabled(false);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment(v);
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        countryPaymentPermission = true;

        // Showing selected spinner item
     //   if (q==true)
      //      Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        if (countries.contains(item)) {
     //       Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            if (item.equals("United States")) {
                states.setVisibility(View.VISIBLE);
                statesText.setVisibility(View.VISIBLE);
                q=true;
            } else {
                states.setVisibility(View.INVISIBLE);
                statesText.setVisibility(View.INVISIBLE);
                q=false;
            }

        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        states.setVisibility(View.INVISIBLE);
        statesText.setVisibility(View.INVISIBLE);

    }
    public ArrayList<String> getStates(ArrayList<String> list){
        String[] array = {"Alabama",
                "Alaska",
                "Arizona",
                "Arkansas",
                "California",
                "Colorado",
                "Connecticut",
                "Delaware",
                "Florida",
                "Georgia",
                "Hawaii",
                "Idaho",
                "Illinois Indiana",
                "Iowa",
                "Kansas",
                "Kentucky",
                "Louisiana",
                "Maine",
                "Maryland",
                "Massachusetts",
                "Michigan",
                "Minnesota",
                "Mississippi",
                "Missouri",
                "Montana Nebraska",
                "Nevada",
                "New Hampshire",
                "New Jersey",
                "New Mexico",
                "New York",
                "North Carolina",
                "North Dakota",
                "Ohio",
                "Oklahoma",
                "Oregon",
                "Pennsylvania",
                "Rhode Island",
                "South Carolina",
                "South Dakota",
                "Tennessee",
                "Texas",
                "Utah",
                "Vermont",
                "Virginia",
                "Washington",
                "West Virginia",
                "Wisconsin",
                "Wyoming"};
        for (int i=0; i<array.length; i++)
        list.add(array[i]);
        return list;
    }
    public void goTopayment(){
        Intent i = new Intent(this, FinalTicketActivity.class);
        i.putExtra("firstName", firstName.getText().toString());
        i.putExtra("lastName", lastName.getText().toString());
        i.putExtra("address", address.getText().toString());
        startActivity(i);
    }

    public void makePayment(View view) {
        PaymentAuthenticator paymentAuthenticator = new PaymentAuthenticator(this);
        paymentAuthenticator.execute(PreferenceManager.getDefaultSharedPreferences(this).getString(("userRowID"), ""));
        goTopayment();
    }

}
