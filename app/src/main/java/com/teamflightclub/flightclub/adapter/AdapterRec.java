package com.teamflightclub.flightclub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamflightclub.flightclub.R;
import com.teamflightclub.flightclub.model.ListItem;

import java.util.List;
/**
 * Created by Gio on 11/23/2016.
 */

public class AdapterRec extends RecyclerView.Adapter<AdapterRec.Holder> {

    private List<ListItem> listData;
    private LayoutInflater inflater;

    public AdapterRec (List<ListItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView icon;
        private View container;


        public Holder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView)itemView.findViewById(R.id.im_item_icon);
            container = itemView.findViewById(R.id.cont_item_root);
        }

    }
}

