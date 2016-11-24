package com.teamflightclub.flightclub.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;

import com.teamflightclub.flightclub.R;
import com.teamflightclub.flightclub.adapter.AdapterRec;
import com.teamflightclub.flightclub.model.getSqldata;

public class ViewPursTicketsActivity extends AppCompatActivity {
    private RecyclerView recView;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_purs_ticket);
        recView = (RecyclerView)findViewById(R.id.rec_list);
        //LayoutManager: GridLayoutManager or StaggeredGridLayoutManager
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterRec(getSqldata.getListData(),this);
        recView.setAdapter(adapter);
    }
}
