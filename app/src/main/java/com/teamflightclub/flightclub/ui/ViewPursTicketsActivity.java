package com.teamflightclub.flightclub.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;

import com.teamflightclub.flightclub.ControlPanelActivity;
import com.teamflightclub.flightclub.DisplayPursTicketInformationActivity;
import com.teamflightclub.flightclub.R;
import com.teamflightclub.flightclub.adapter.AdapterRecPurchaseHistory;
import com.teamflightclub.flightclub.model.ListItem;
import com.teamflightclub.flightclub.model.getSqldata;

import java.util.ArrayList;

public class ViewPursTicketsActivity extends AppCompatActivity implements AdapterRecPurchaseHistory.ItemClickCallback {

    private RecyclerView recView;
    private AdapterRecPurchaseHistory adapter;
    private ArrayList listData;
    Button returnBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_purs_ticket);
        listData = (ArrayList) getSqldata.getListData();
        recView = (RecyclerView) findViewById(R.id.rec_list);
        //LayoutManager: GridLayoutManager or StaggeredGridLayoutManager
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRecPurchaseHistory(listData, this);
        adapter.setItemClickCallback(this);
        recView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recView);
        /*
       returnBTN = (Button) findViewById(R.id.btn_return_item);
       returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveControlActivity();
            }
        });
        */
    }


    @Override
    public void onItemClick(int p) {
        ListItem item = (ListItem) listData.get(p);

        Intent intent = new Intent(this, DisplayPursTicketInformationActivity.class);
        startActivity(intent);

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        //      moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;

    }

    private void deleteItem(final int position) {
        listData.remove(position);
        adapter.notifyItemRemoved(position);
    }
    public void moveControlActivity(){
        Intent main = new Intent(this, ControlPanelActivity.class);
        startActivity(main);
    }
}