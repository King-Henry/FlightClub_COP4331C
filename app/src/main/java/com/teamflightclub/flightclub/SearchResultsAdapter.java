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

    public static class Data{

        String text;
        int icon;
    }

    ArrayList<Data> datas;

    public SearchResultsAdapter(){

        datas = new ArrayList<Data>();

        String[] strings = {"Tim", "Cary","Tommy","Hung","Brandon", "Tim", "Cary","Tommy","Hung","Brandon","Tim", "Cary","Tommy","Hung","Brandon","Tim", "Cary","Tommy","Hung","Brandon"};
        int[] icons = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher
                ,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher};

        for(int i =0; i< strings.length;i++){

            Data data = new Data();
            data.text = strings[i];
            data.icon = icons[i];
            datas.add(data);
        }

    }

    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_cardview_layout,parent,false);
        SearchListViewHolder searchListViewHolder = new SearchListViewHolder(view);
        return searchListViewHolder;

    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {

        Data data = datas.get(position);
        holder.imageView.setImageResource(data.icon);
        holder.textView.setText(data.text);
    }

    @Override
    public int getItemCount() {

        return datas.size();
    }

    public static class SearchListViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textView;
        ImageView imageView;

        public SearchListViewHolder(View viewHolderLayout){

            super(viewHolderLayout);
            cardView = (CardView)viewHolderLayout.findViewById(R.id.card_view);
            textView = (TextView)viewHolderLayout.findViewById(R.id.text_view);
            imageView = (ImageView)viewHolderLayout.findViewById(R.id.image_view);
        }
    }
}
