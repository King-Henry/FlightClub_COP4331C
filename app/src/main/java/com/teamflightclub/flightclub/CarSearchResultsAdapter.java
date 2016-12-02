package com.teamflightclub.flightclub;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tim on 12/1/2016.
 */

public class CarSearchResultsAdapter extends RecyclerView.Adapter<CarSearchResultsAdapter.CarSearchListViewHolder> {

    static String locationAirportCode = "";


    public int position;

    public static ArrayList<Car> cars;

    private final CarSearchOnClickListener carSearchOnClickListener;

    static Context contxt;

    public CarSearchResultsAdapter(ArrayList<Car> carData, Context context,
                                   CarSearchOnClickListener carSearchOnClickListener) {

        cars = carData;
        contxt = context;
        this.carSearchOnClickListener = carSearchOnClickListener;

        Log.v("flights data empty?", "" + cars.isEmpty());
    }


    @Override
    public CarSearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.v("OnCreateViewHolder", "ViewHolder being created");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_search_results_layout, parent, false);
        CarSearchListViewHolder carSearchListViewHolder = new CarSearchListViewHolder(view);
        return carSearchListViewHolder;

    }

    @Override
    public void onBindViewHolder(CarSearchListViewHolder holder, int position) {

        Log.v("onBindViewHolder", " we are binding the viewholder");

//        holder.airlineCompany.setText(flights.get(position).airlineName);
//        holder.flightDepartureTime.setText(flights.get(position).departureTime);
//        holder.flightArrivalTime.setText(flights.get(position).arrivalTime);
//        String price = "$" + String.format("%.2f",flights.get(position).price);
//        holder.flightPrice.setText(price);
//        holder.fromToDestinationName.setText("");
        this.position = position;

        holder.binder(cars.get(position), carSearchOnClickListener, position);

        //bind views with information
    }

    @Override
    public int getItemCount() {

        return cars == null ? 0 : cars.size();
    }

    public class CarSearchListViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView carPicture;
        TextView numOfPassengers;
        TextView numOfDoors;
        TextView numOfBags;
        TextView carPrice;
        TextView vehicleName;

        public CarSearchListViewHolder(View viewHolderLayout) {

            super(viewHolderLayout);
            cardView = (CardView) viewHolderLayout.findViewById(R.id.flight_card_view);
            carPicture = (ImageView) viewHolderLayout.findViewById(R.id.car_picture);
            numOfPassengers = (TextView) viewHolderLayout.findViewById(R.id.num_of_passengers);
            numOfDoors = (TextView) viewHolderLayout.findViewById(R.id.num_of_doors);
            numOfBags = (TextView) viewHolderLayout.findViewById(R.id.num_of_bags);
            carPrice = (TextView) viewHolderLayout.findViewById(R.id.car_price);
            vehicleName = (TextView)viewHolderLayout.findViewById(R.id.vehicle_name);
        }

        public void binder(final Car car, final CarSearchOnClickListener onClickListener, int position) {

            numOfPassengers.setText(car.adultCount + " passengers");
            numOfDoors.setText(car.carDoorCount + " Doors" );
            numOfBags.setText(car.largeLuggageCount + " bags");
            String unitPrice = "$" + String.format("%.2f", car.unitPrice);
            carPrice.setText(unitPrice + "/day");
            vehicleName.setText(car.carMakeModel);
            Glide.with(contxt).load(car.thumbnailUrl).into(carPicture);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent carDetailScreen = new Intent(contxt, CarDetailScreenActivity.class);
                    carDetailScreen.putExtra("carItemPosition", getAdapterPosition());
                    Log.v("Item Position", "" + getAdapterPosition());
                    contxt.startActivity(carDetailScreen);
                }
            });
        }
    }


    public static class FetchtheCars extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            String url = "";


            try {
                url = "http://teamflightclubproject.com/carSearch.php?" + URLEncoder.encode("PickupCode", "UTF-8")
                        + "=" + URLEncoder.encode(locationAirportCode, "UTF-8");

                Log.v("Print URL", url.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();


            try {

                Log.v("Is this even called?", "yes");
                Response response = client.newCall(request).execute();


                JSONArray carData = new JSONArray(response.body().string());



                for (int i = 0; i < carData.length(); i++) {

                    JSONObject carDataObject = carData.getJSONObject(i);

                    String PIID = carDataObject.getString("PIID");
                    String supplierName = carDataObject.getString("SupplierName");
                    String carMakeModel = carDataObject.getString("CarMakeModel");
                    int adultCount = carDataObject.getInt("AdultCount");
                    String ratePeriodCode = carDataObject.getString("RatePeriodCode");
                    double unitPrice = carDataObject.getDouble("UnitPrice");
                    double totalPrice = carDataObject.getDouble("TotalPrice");
                    String carClass = carDataObject.getString("CarClass");
                    int carDoorCount = carDataObject.getInt("CarDoorCount");
                    int largeLuggageCount = carDataObject.getInt("LargeLuggageCount");
                    String thumbnailUrl = carDataObject.getString("ThumbnailUrl");
                    String dropOffCode = carDataObject.getString("DropOffCode");
                    String pickupCode = carDataObject.getString("PickupCode");
                    String type = carDataObject.getString("Type");

                    Car car = new Car(PIID, supplierName, adultCount, carMakeModel,
                            ratePeriodCode, unitPrice, totalPrice, carClass, largeLuggageCount,
                            carDoorCount, thumbnailUrl, dropOffCode, pickupCode);

                    Log.v("Car", car.PIID);

                    cars.add(car);


                }

            } catch (IOException e) {

                e.printStackTrace();

            } catch (JSONException e) {

                System.out.println("no more content");
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Log.v("OnPostExecute", " notifying data set changed");
            CarSearchActivity.carSearchResultsAdapter.notifyDataSetChanged();
        }

    }

}






