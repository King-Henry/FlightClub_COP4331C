package com.teamflightclub.flightclub;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import static android.view.View.GONE;
import static com.teamflightclub.flightclub.CarSearchResultsAdapter.cars;
import static com.teamflightclub.flightclub.HotelSearchResultsAdapter.hotels;
import static com.teamflightclub.flightclub.SearchResultsAdapter.flights;

public class HotelDetailScreenActivity extends AppCompatActivity implements AsyncCallback{

    Hotel hotel;

    ImageView hotelPicture;

    TextView name;
    TextView rating;
    TextView distance;
    TextView airportCode;
    TextView hotelPrice;
    TextView amenities;
    TextView hotelSignInText;

    Button hotelAddToCart;


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
        setContentView(R.layout.activity_car_detail_screen);

        Bundle bundle = getIntent().getExtras();
        hotel = hotels.get(bundle.getInt("hotelItemPostion"));

        hotelPicture = (ImageView)findViewById(R.id.detail_car_picture);
        name = (TextView)findViewById(R.id.vehicle_name_detail);
        rating = (TextView)findViewById(R.id.num_of_passengers_detail);
        distance = (TextView)findViewById(R.id.num_of_doors_detail);
        airportCode = (TextView)findViewById(R.id.num_of_bags_detail);
        hotelPrice = (TextView)findViewById(R.id.car_price_detail);
        amenities = (TextView)findViewById(R.id.car_class_name_detail);
        hotelSignInText = (TextView)findViewById(R.id.car_sign_in_text);
        hotelAddToCart = (Button)findViewById(R.id.car_add_to_cart_button);

        Glide.with(this).load(hotel.thumbnailUrl).into(hotelPicture);
        name.setText(hotel.name);
        rating.setText(String.format("%.2f",hotel.rating) + "/5 Stars");
        distance.setText(String.format("%.2f",hotel.distance)+" miles");
        airportCode.setText(hotel.airportCode);
        hotelPrice.setText("$" + String.format("%.2f", hotel.price));
        amenities.setText(returnAmenities(hotel.amenities));

    }

    @Override
    protected void onResume() {
        super.onResume();

        String userRowID = PreferenceManager.getDefaultSharedPreferences(this).getString("userRowID","");

        if(userRowID.isEmpty()){

            hotelAddToCart.setEnabled(false);
            hotelSignInText.setVisibility(View.VISIBLE);
        }

        else{

            hideSignInButton = true;
            invalidateOptionsMenu();
            hotelAddToCart.setEnabled(true);
            hotelSignInText.setVisibility(GONE);

        }
    }

    @Override
    public void done() {

        Toast.makeText(this,"Ticket successfully added to cart", Toast.LENGTH_LONG).show();
    }

    public static String returnAmenities(String[] amenities) {
        String formattedAmenities = "";
        for (String amenity: amenities) {
            formattedAmenities += amenity + "\n";
        }

        return formattedAmenities.substring(0,formattedAmenities.length()-3);
    }
}
