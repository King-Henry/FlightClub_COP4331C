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

public class HotelSearchResultsAdapter extends RecyclerView.Adapter<HotelSearchResultsAdapter.HotelSearchListViewHolder> {

    static String locationAirportCode = "";


    public int position;

    public static ArrayList<Hotel> hotels;

    private final HotelSearchOnClickListener hotelSearchOnClickListener;

    static Context contxt;

    public HotelSearchResultsAdapter(ArrayList<Hotel> hotelData, Context context,
                                   HotelSearchOnClickListener hotelSearchOnClickListener) {

        hotels = hotelData;
        contxt = context;
        this.hotelSearchOnClickListener = hotelSearchOnClickListener;

        Log.v("flights data empty?", "" + hotels.isEmpty());
    }


    @Override
    public HotelSearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.v("OnCreateViewHolder", "ViewHolder being created");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_search_results_layout, parent, false);
        HotelSearchListViewHolder hotelSearchListViewHolder = new HotelSearchListViewHolder(view);
        return hotelSearchListViewHolder;

    }

    @Override
    public void onBindViewHolder(HotelSearchListViewHolder holder, int position) {

        Log.v("onBindViewHolder", " we are binding the viewholder");

//        holder.airlineCompany.setText(flights.get(position).airlineName);
//        holder.flightDepartureTime.setText(flights.get(position).departureTime);
//        holder.flightArrivalTime.setText(flights.get(position).arrivalTime);
//        String price = "$" + String.format("%.2f",flights.get(position).price);
//        holder.flightPrice.setText(price);
//        holder.fromToDestinationName.setText("");
        this.position = position;

        holder.binder(hotels.get(position), hotelSearchOnClickListener, position);

        //bind views with information
    }

    @Override
    public int getItemCount() {

        return hotels == null ? 0 : hotels.size();
    }

    public class HotelSearchListViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView hotelPicture;
        TextView rating;
        TextView distance;
        TextView airportCode;
        TextView hotelPrice;
        TextView name;

        public HotelSearchListViewHolder(View viewHolderLayout) {

            super(viewHolderLayout);
            cardView = (CardView) viewHolderLayout.findViewById(R.id.flight_card_view);
            hotelPicture = (ImageView) viewHolderLayout.findViewById(R.id.car_picture);
            rating = (TextView) viewHolderLayout.findViewById(R.id.num_of_passengers);
            distance = (TextView) viewHolderLayout.findViewById(R.id.num_of_doors);
            airportCode = (TextView) viewHolderLayout.findViewById(R.id.num_of_bags);
            hotelPrice = (TextView) viewHolderLayout.findViewById(R.id.car_price);
            name = (TextView)viewHolderLayout.findViewById(R.id.vehicle_name);
        }

        public void binder(final Hotel hotel, final HotelSearchOnClickListener onClickListener, int position) {

            rating.setText(String.format("%.2f",hotel.rating) + "/5 Stars");
            distance.setText(String.format("%.2f",hotel.distance) + " Miles" );
            airportCode.setText(hotel.airportCode);
            String unitPrice = "$" + String.format("%.2f", hotel.price);
            hotelPrice.setText("$" + String.format("%.2f", hotel.price));
            name.setText(hotel.name);
            Glide.with(contxt).load(hotel.thumbnailUrl).into(hotelPicture);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent hotelDetailScreen = new Intent(contxt, HotelDetailScreenActivity.class);
                    hotelDetailScreen.putExtra("carItemPosition", getAdapterPosition());
                    Log.v("Item Position", "" + getAdapterPosition());
                    contxt.startActivity(hotelDetailScreen);
                }
            });
        }
    }


    public static class FetchtheHotels extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            String url = "";


            try {
                url = "http://teamflightclubproject.com/hotelSearch.php?" + URLEncoder.encode("airportCode", "UTF-8")
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


                JSONArray hotelData = new JSONArray(response.body().string());



                for (int i = 0; i < hotelData.length(); i++) {
                    Log.v("Are you getting here?", "yes");
                    JSONObject hotelDataObject = hotelData.getJSONObject(i);

                    int hotelId = hotelDataObject.getInt("hotelId");
                    Log.v("Is this even called?", "yes1");
                    String name = hotelDataObject.getString("Name");
                    Log.v("Is this even called?", "yes2");
                    double rating = hotelDataObject.getDouble("Rating");
                    Log.v("Is this even called?", "yes3");
                    String airportCode = hotelDataObject.getString("airportCode");
                    Log.v("Is this even called?", "yes4");
                    double distance = hotelDataObject.getInt("Distance");
                    Log.v("Is this even called?", "yes5");
                    double price = hotelDataObject.getDouble("Price");
                    String amenities = hotelDataObject.getString("Amenities");
                    String thumbnailUrl = hotelDataObject.getString("ThumbnailUrl");
                    String largeThumbnailUrl = hotelDataObject.getString("LargeThumbnailUrl");
                    String description = hotelDataObject.getString("Description");
                    String type = hotelDataObject.getString("Type");

                    Hotel hotel = new Hotel(name, hotelId, rating, airportCode, distance, price, thumbnailUrl, amenities, largeThumbnailUrl, description);

                    Log.v("Car", Integer.toString(hotel.hotelId));

                    hotels.add(hotel);


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
            HotelSearchActivity.hotelSearchResultsAdapter.notifyDataSetChanged();
        }

    }

}






