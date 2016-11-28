package com.teamflightclub.flightclub;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tim on 11/27/2016.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchListViewHolder> {


    ArrayList<Flight> flights;

    public SearchResultsAdapter(){

        flights = new ArrayList<Flight>();

        //populate ArrayList
    }



    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_cardview_layout,parent,false);
        SearchListViewHolder searchListViewHolder = new SearchListViewHolder(view);
        return searchListViewHolder;

    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {

        Flight flight = flights.get(position);

        //bind views with information
    }

    @Override
    public int getItemCount() {

        return flights.size();
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
}
