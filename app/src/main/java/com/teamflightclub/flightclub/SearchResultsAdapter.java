package com.teamflightclub.flightclub;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tim on 11/27/2016.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchListViewHolder> {

    static String departureAirportCode = "";
    static String arrivalAirportCode = "";
    static String departureDate = "";
    static String passengers = "";

    public int position;

    public static ArrayList<Flight> flights;
    public static HashSet<String> flightIdSet = new HashSet<String>();

    private final FlightSearchOnClickListener flightSearchOnClickListener;

    static Context contxt;

    public SearchResultsAdapter(ArrayList<Flight> flightData, Context context,
                                FlightSearchOnClickListener flightSearchOnClickListener){

        flights = flightData;
        contxt = context;
        this.flightSearchOnClickListener = flightSearchOnClickListener;

        Log.v("flights data empty?", "" + flights.isEmpty());
    }


    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.v("OnCreateViewHolder", "ViewHolder being created");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_cardview_layout,parent,false);
        SearchListViewHolder searchListViewHolder = new SearchListViewHolder(view);
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

        holder.bind(flights.get(position), flightSearchOnClickListener,position);

        //bind views with information
    }


    @Override
    public int getItemCount() {

        return flights == null ? 0: flights.size();
    }


    public static class SearchListViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView airlineCompany;
        TextView flightDepartureTime;
        TextView flightArrivalTime;
        TextView flightPrice;
        TextView fromToDestinationName;

        public SearchListViewHolder(View viewHolderLayout){

            super(viewHolderLayout);
            cardView = (CardView)viewHolderLayout.findViewById(R.id.flight_card_view);
            airlineCompany = (TextView)viewHolderLayout.findViewById(R.id.airline_name);
            flightDepartureTime = (TextView) viewHolderLayout.findViewById(R.id.departure_time);
            flightArrivalTime = (TextView)viewHolderLayout.findViewById(R.id.arrival_time);
            flightPrice = (TextView)viewHolderLayout.findViewById(R.id.trip_price);
            fromToDestinationName = (TextView)viewHolderLayout.findViewById(R.id.departure_arrival_names);
        }

        public void bind(final Flight flight,final FlightSearchOnClickListener onClickListener, int position){

            airlineCompany.setText(flight.airlineName);
            flightDepartureTime.setText(flight.departureTime);
            flightArrivalTime.setText(flight.arrivalTime);
            String price = "$" + String.format("%.2f",flight.price);
            flightPrice.setText(price);

            if(flight.secondLeg != null) {

                fromToDestinationName.setText("This is a Multi-City Flight");
            }

            else{

                fromToDestinationName.setText("");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent detailScreen = new Intent(contxt, TicketDetailActivity.class);
                    detailScreen.putExtra("FlightItemPosition",getAdapterPosition());
                    Log.v("Item Position", "" + getAdapterPosition());
                    contxt.startActivity(detailScreen);
                }
            });
        }
    }


    public static class FetchtheFlights extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {

            String url = "";


            try {
                url = "http://teamflightclubproject.com/TempFlights2.php?" + URLEncoder.encode("departureAirportCode", "UTF-8")
                        + "=" + URLEncoder.encode(departureAirportCode, "UTF-8")+"&"+URLEncoder.encode("arrivalAirportCode", "UTF-8")
                        + "=" + URLEncoder.encode(arrivalAirportCode, "UTF-8")+"&"+URLEncoder.encode("departureDate", "UTF-8")
                        + "=" + URLEncoder.encode(departureDate, "UTF-8")+"&"+URLEncoder.encode("passengers", "UTF-8")
                        + "=" + URLEncoder.encode(passengers, "UTF-8");

                Log.v("Print URL", url.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

//            try {
//                Response response = client.newCall(request).execute();
//
//                JSONArray flightData = new JSONArray(response.body().string());
//
//                for (int i = 0; i < flightData.length(); i++) {
//
//                    JSONObject flightDataObject = flightData.getJSONObject(i);
//                    Log.v("Print Leg Id: ",flightDataObject.getString("legId"));
//                }
//            }catch(IOException e) {
//
//                e.printStackTrace();
//            }catch(JSONException e){
//
//                System.out.println("no more content");
//            }

            flightIdSet.clear();

            try{
                Response response = client.newCall(request).execute();

                JSONArray flightData = new JSONArray(response.body().string());

                for(int i = 0; i<flightData.length();i++) {

                    JSONObject flightDataObject = flightData.getJSONObject(i);

                    if (flightIdSet.contains(flightDataObject.getString("legId")))
                        continue;

                    String airlineName = flightDataObject.getString("airlineName");
                    int ID = flightDataObject.getInt("ID");
                    String departureTime = flightDataObject.getString("departureTime");
                    String arrivalTime = flightDataObject.getString("arrivalTime");
                    double price = flightDataObject.getDouble("price");
                    int distance = flightDataObject.getInt("distance");
                    String arrivalAirportCode = flightDataObject.getString("arrivalAirportCode");
                    String departureAirportCode = flightDataObject.getString("departureAirportCode");
                    String departureAirportLocation = flightDataObject.getString("departureAirportLocation");
                    String departureDate = flightDataObject.getString("departureDate");
                    String arrivalDate = flightDataObject.getString("arrivalDate");
                    String duration = flightDataObject.getString("duration");
                    int seatsRemaining = flightDataObject.getInt("seatsRemaining");
                    String legId = flightDataObject.getString("legId");
                    String arrivalAirportLocation = flightDataObject.getString("arrivalAirportLocation");
                    String flightNumber = flightDataObject.getString("ID");

                    Flight flight = new Flight(airlineName,flightNumber,departureTime,arrivalTime,price,ID,distance,
                            arrivalAirportCode,departureAirportCode,departureAirportLocation,arrivalAirportLocation,
                            departureDate,arrivalDate,duration,seatsRemaining,legId);

                    if (!flightDataObject.getString("secondLeg").equals("") || !flightDataObject.getString("secondLeg").equals("null")) {
                        if (flightDataObject.getString("secondLeg").equals(flightData.getJSONObject(i+1).getString("legId"))) {
                            flight.secondLeg = createFlight(flightData.getJSONObject(i + 1));
                            Log.v("Print Leg",flight.legId + " is equal to "+flight.secondLeg.legId);
                            if(!flightData.getJSONObject(i+1).getString("secondLeg").equals("") || !flightData.getJSONObject(i+1).getString("secondLeg").equals("null")) {
                                if(flightData.getJSONObject(i+1).getString("secondLeg").equals(flightData.getJSONObject(i+2).getString("legId"))) {
                                    flight.thirdLeg = createFlight(flightData.getJSONObject(i + 2));
                                    Log.v("Print Leg ",flight.legId + " is equal to "+flight.thirdLeg.legId);
                                }
                            }
                        }
                    }
                    Log.v("Print Leg",flight.legId);
                    flightIdSet.add(flight.legId);
                    flights.add(flight);


                    }
                }catch(IOException e) {

                e.printStackTrace();
            }catch(JSONException e){

                System.out.println("no more content");
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Log.v("OnPostExecute", " notifying data set changed");
            SearchActivity.searchResultsAdapter.notifyDataSetChanged();
        }

        public Flight createFlight(JSONObject flightDataObject) throws JSONException {
            String airlineName = flightDataObject.getString("airlineName");
            int ID = flightDataObject.getInt("ID");
            String flightNumber = flightDataObject.getString("flightNumber");
            String departureTime = flightDataObject.getString("departureTime");
            String arrivalTime = flightDataObject.getString("arrivalTime");
            double price = flightDataObject.getDouble("price");
            int distance = flightDataObject.getInt("distance");
            arrivalAirportCode = flightDataObject.getString("arrivalAirportCode");
            departureAirportCode = flightDataObject.getString("departureAirportCode");
            String departureAirportLocation = flightDataObject.getString("departureAirportLocation");
            String arrivalAirportLocation = flightDataObject.getString("arrivalAirportLocation");
            departureDate = flightDataObject.getString("departureDate");
            String arrivalDate = flightDataObject.getString("arrivalDate");
            String duration = flightDataObject.getString("duration");
            int seatsRemaining = flightDataObject.getInt("seatsRemaining");
            String legId = flightDataObject.getString("legId");

            return new Flight(airlineName, flightNumber, departureTime, arrivalTime, price, ID, distance,
            arrivalAirportCode, departureAirportCode, departureAirportLocation, arrivalAirportLocation,
                    departureDate, arrivalDate, duration, seatsRemaining, legId);
        }
    }

}
