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
import static com.teamflightclub.flightclub.SearchResultsAdapter.flights;

public class CarDetailScreenActivity extends AppCompatActivity implements AsyncCallback{

    Car car;

    ImageView carPicture;

    TextView vehicleName;
    TextView numOfPassengers;
    TextView numOfDoors;
    TextView numOfBags;
    TextView carPrice;
    TextView carClassName;
    TextView carSignInText;

    Button carAddToCart;


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
        car = cars.get(bundle.getInt("carItemPostion"));

        carPicture = (ImageView)findViewById(R.id.detail_car_picture);
        vehicleName = (TextView)findViewById(R.id.vehicle_name_detail);
        numOfPassengers = (TextView)findViewById(R.id.num_of_passengers_detail);
        numOfDoors = (TextView)findViewById(R.id.num_of_doors_detail);
        numOfBags = (TextView)findViewById(R.id.num_of_bags_detail);
        carPrice = (TextView)findViewById(R.id.car_price_detail);
        carClassName = (TextView)findViewById(R.id.car_class_name_detail);
        carSignInText = (TextView)findViewById(R.id.car_sign_in_text);
        carAddToCart = (Button)findViewById(R.id.car_add_to_cart_button);

        Glide.with(this).load(car.thumbnailUrl).into(carPicture);
        vehicleName.setText(car.carMakeModel);
        numOfPassengers.setText(car.adultCount + " Passengers");
        numOfDoors.setText(car.carDoorCount + " Doors");
        numOfBags.setText(car.largeLuggageCount + " Bags");
        carPrice.setText("$" + String.format("%.2f", car.unitPrice) + "/day");
        carClassName.setText(car.carClass);

    }

    @Override
    protected void onResume() {
        super.onResume();

        String userRowID = PreferenceManager.getDefaultSharedPreferences(this).getString("userRowID","");

        if(userRowID.isEmpty()){

            carAddToCart.setEnabled(false);
            carSignInText.setVisibility(View.VISIBLE);
        }

        else{

            hideSignInButton = true;
            invalidateOptionsMenu();
            carAddToCart.setEnabled(true);
            carSignInText.setVisibility(GONE);

        }
    }

    @Override
    public void done() {

        Toast.makeText(this,"Ticket successfully added to cart", Toast.LENGTH_LONG).show();
    }
}
