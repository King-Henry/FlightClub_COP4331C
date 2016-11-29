package com.teamflightclub.flightclub;


import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

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

    public static ArrayList<Flight> flights;

    public SearchResultsAdapter(ArrayList<Flight> flightData){

        flights = flightData;
    }



    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_cardview_layout,parent,false);
        SearchListViewHolder searchListViewHolder = new SearchListViewHolder(view);
        return searchListViewHolder;

    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {

        holder.airlineCompany.setText(flights.get(position).airlineName);
        holder.flightDepartureTime.setText(flights.get(position).departureTime);
        holder.flightArrivalTime.setText(flights.get(position).arrivalTime);
        holder.flightPrice.setText("$" + Double.toString(flights.get(position).price));
        holder.fromToDestinationName.setText("");



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
    }


    public static class FetchtheFlights extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
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

            try{
                Response response = client.newCall(request).execute();

                JSONArray flightData = new JSONArray(response.body().string());

                for(int i = 0; i<flightData.length();i++) {

                    JSONObject flightDataObject = flightData.getJSONObject(i);

                    Flight flight = new Flight();

                    flight.airlineName = flightDataObject.getString("airlineName");
                    flight.ID = flightDataObject.getInt("ID");
                    flight.departureTime = flightDataObject.getString("departureTime");
                    flight.arrivalTime = flightDataObject.getString("arrivalTime");
                    flight.price = flightDataObject.getDouble("price");

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

            SearchActivity.searchResultsAdapter.notifyDataSetChanged();
        }
    }
}
