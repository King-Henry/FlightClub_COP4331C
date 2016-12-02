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
import android.widget.TextView;

import com.teamflightclub.flightclub.model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Carrito on 12/1/2016.
 */

public class PrePaymentSystemAdapter extends RecyclerView.Adapter<PrePaymentSystemAdapter.SearchListViewHolder> {

    public static ArrayList<Reservation> cartProducts;
    static Context contxt;
    public int position;

    public PrePaymentSystemAdapter(ArrayList<Reservation> cartData,Context context) {
        //super();
        cartProducts = cartData;
        contxt = context;
    }

    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.v("OnCreateViewHolder", "ViewHolder being created");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_details_cardview_layout,parent,false);
        PrePaymentSystemAdapter.SearchListViewHolder searchListViewHolder = new PrePaymentSystemAdapter.SearchListViewHolder(view);
        return searchListViewHolder;

    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {

        Log.v("onBindViewHolder", " we are binding the viewholder");

//        holder.airlineCompany.setText(flights.get(position).airlineName);
//        holder.flightDepartureTime.setText(flights.get(position).departureTime);
//        holder.flightArrivalTime.setText(flights.get(position).arrivalTime);
//        String price = "$" + String.format("%.2f",flights.get(position).price);
//        holder.flightPrice.setText(price);
//        holder.fromToDestinationName.setText("");
        this.position = position;

        holder.bind(cartProducts.get(position),position);

        //bind views with information
    }

    @Override
    public int getItemCount() {

        return cartProducts == null ? 0: cartProducts.size();
    }

    public static class SearchListViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView topText;
        TextView bottomLeft;
        TextView bottomRight;
        TextView topRight;
        TextView middleText;

        public SearchListViewHolder(View viewHolderLayout){

            super(viewHolderLayout);
            cardView = (CardView)viewHolderLayout.findViewById(R.id.reservationDetailCard);
            topText = (TextView)viewHolderLayout.findViewById(R.id.topBigText);
            bottomLeft = (TextView) viewHolderLayout.findViewById(R.id.bottomLeftText);
            bottomRight = (TextView)viewHolderLayout.findViewById(R.id.bottomRightText);
            topRight = (TextView)viewHolderLayout.findViewById(R.id.price);
            middleText = (TextView)viewHolderLayout.findViewById(R.id.middleText);
        }

        public void bind(final Reservation product, int position){
            Log.v("Product DB",product.reservationDb);
            switch (product.reservationDb) {
                case "TempFlights2":
                    Flight flight = (Flight) product;
                    topText.setText(flight.airlineName + " " + flight.flightNumber);
                    bottomLeft.setText(flight.departureAirportLocation + "\n" + flight.departureTime + "\n" + flight.departureDate);
                    bottomRight.setText(flight.arrivalAirportLocation + "\n" + flight.arrivalTime + "\n" + flight.arrivalDate);
                    String price = "$" + String.format("%.2f",flight.price);
                    topRight.setText(price);
                    middleText.setText(flight.duration + " ," + flight.distance + "miles");
                    break;

                case "Cars":
                    Car car = (Car) product;
                    topText.setText(car.carMakeModel);
                    bottomLeft.setText(car.carClass + "\n" + car.adultCount + " Passengers\n" + car.largeLuggageCount + " Large Bags");
                    bottomRight.setText("TOTAL $"+String.format("%.2f",car.totalPrice)+"\n" + car.supplierName + "\n" + car.dropOffCode);
                    topRight.setText(car.ratePeriodCode + " $" + String.format("%.2f",car.unitPrice));
                    middleText.setText("");
                    break;

                case "Hotels":
                    Hotel hotel = (Hotel) product;
                    topText.setText(hotel.name);
                    bottomLeft.setText(hotel.rating + "/5\n" + returnAmenities(hotel.amenities));
                    topRight.setText("$" + String.format("%.2f",hotel.price));
                    bottomRight.setText(hotel.airportCode+"\n"+hotel.distance + " miles");
                    middleText.setText("");
                    break;

                case "Activities":
                    Activity activity = (Activity) product;
                    topText.setText(activity.title);
                    topRight.setText("$"+String.format("%.2f",activity.fromPrice)+"\n"+activity.fromPriceTicketType);
                    bottomLeft.setText("\n"+activity.duration);
                    bottomRight.setText(activity.airportCode);
                    break;
            }

        }
    }

    public static class FetchtheCart extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://teamflightclubproject.com/getCart.php?userId="+params[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();

                JSONArray data = new JSONArray(response.body().string());

                for (int i = 0; i < data.length(); i++) {

                    JSONObject dataObject = data.getJSONObject(i);

                    switch (dataObject.getString("Type")) {

                        case "Flight":
                            String airlineName = dataObject.getString("airlineName");
                            int ID = dataObject.getInt("ID");
                            String flightNumber = dataObject.getString("flightNumber");
                            String departureTime = dataObject.getString("departureTime");
                            String arrivalTime = dataObject.getString("arrivalTime");
                            double price = dataObject.getDouble("price");
                            int distance = dataObject.getInt("distance");
                            String arrivalAirportCode = dataObject.getString("arrivalAirportCode");
                            String departureAirportCode = dataObject.getString("departureAirportCode");
                            String departureAirportLocation = dataObject.getString("departureAirportLocation");
                            String arrivalAirportLocation = dataObject.getString("arrivalAirportLocation");
                            String departureDate = dataObject.getString("departureDate");
                            String arrivalDate = dataObject.getString("arrivalDate");
                            String duration = dataObject.getString("duration");
                            int seatsRemaining = dataObject.getInt("seatsRemaining");
                            String legId = dataObject.getString("legId");
                            Flight flight = new Flight(airlineName, flightNumber, departureTime, arrivalTime, price, ID, distance,
                                    arrivalAirportCode, departureAirportCode, departureAirportLocation, arrivalAirportLocation,
                                    departureDate, arrivalDate, duration, seatsRemaining, legId);
                            //flight.setListInfo(icons[1]);
                            cartProducts.add(flight);
                            break;

                        case "Activity":
                            String fromPriceTicketType = dataObject.getString("FromPriceTicketType");
                            String fromPrice = dataObject.getString("FromPrice");
                            String title = dataObject.getString("Title");
                            String imageUrl = dataObject.getString("ImageUrl");
                            String airportCode = dataObject.getString("airportCode");
                            duration = dataObject.getString("Duration");
                            String activityID = dataObject.getString("ID");
                            Activity activity = new Activity(activityID, title, duration, fromPrice, fromPriceTicketType, imageUrl, airportCode);
                            //activity.setListInfo(icons[0]);
                            cartProducts.add(activity);
                            break;

                        case "Car":
                            String PIID = dataObject.getString("PIID");
                            String supplierName = dataObject.getString("SupplierName");
                            int adultCount = dataObject.getInt("AdultCount");
                            String carMakeModel = dataObject.getString("CarMakeModel");
                            String ratePeriodCode = dataObject.getString("RatePeriodCode");
                            double unitPrice = dataObject.getDouble("UnitPrice");
                            double totalPrice = dataObject.getDouble("TotalPrice");
                            String carClass = dataObject.getString("CarClass");
                            int largeLuggageCount = dataObject.getInt("LargeLuggageCount");
                            int carDoorCount = dataObject.getInt("CarDoorCount");
                            String thumbnailUrl = dataObject.getString("ThumbnailUrl");
                            String dropOffCode = dataObject.getString("DropOffCode");
                            String pickupCode = dataObject.getString("PickupCode");
                            Car car = new Car(PIID,supplierName,adultCount,carMakeModel,ratePeriodCode,unitPrice,totalPrice,carClass,largeLuggageCount,carDoorCount,thumbnailUrl,dropOffCode,pickupCode);
                            //car.setListInfo(icons[0]);
                            cartProducts.add(car);
                            break;

                        case "Hotel":
                            String hotelName = "Hotel Reservation";
                            int hotelId = dataObject.getInt("hotelId");
                            double rating = dataObject.getDouble("Rating");
                            String hotelAirportCode = dataObject.getString("airportCode");
                            double hotelDistance = dataObject.getDouble("Distance");
                            double hotelPrice = dataObject.getDouble("Price");
                            String amenities = dataObject.getString("Amenities");
                            String hotelThumbnailUrl = dataObject.getString("ThumbnailUrl");
                            String largeThumbnailUrl = dataObject.getString("LargeThumbnailUrl");
                            String description = dataObject.getString("Description");
                            Hotel hotel = new Hotel(hotelName,hotelId, rating, hotelAirportCode, hotelDistance, hotelPrice,
                                    hotelThumbnailUrl, amenities, largeThumbnailUrl, description);
                            //hotel.setListInfo(icons[0]);
                            cartProducts.add(hotel);
                            break;


                    }

                }



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("End of content");
            }
            return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {

        Log.v("OnPostExecute", " notifying data set changed");
        PrePaymentSystemActivity.prePaymentSystemAdapter.notifyDataSetChanged();
    }
    }

    public static String returnAmenities(String[] amenities) {
        String formattedAmenities = "";
        for (String amenity: amenities) {
            formattedAmenities += amenity + "\n";
        }

        return formattedAmenities.substring(0,formattedAmenities.length()-3);
    }
}
