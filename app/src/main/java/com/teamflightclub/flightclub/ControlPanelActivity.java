package com.teamflightclub.flightclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.teamflightclub.flightclub.ui.ViewPursTicketsActivity;

public class ControlPanelActivity extends AppCompatActivity {

        Button viewTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        viewTicket = (Button)findViewById(R.id.viewTicket);
        viewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTickets();
            }
        });

    }
    public void openTickets(){
        Intent intent = new Intent(this, ViewPursTicketsActivity.class);
        startActivity(intent);
    }

}
