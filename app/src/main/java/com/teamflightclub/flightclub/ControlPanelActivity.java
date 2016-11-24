package com.teamflightclub.flightclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teamflightclub.flightclub.ui.ViewPursTicketsActivity;

public class ControlPanelActivity extends AppCompatActivity {

        Button viewTicket;
        Button viewChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        Toast.makeText(getApplicationContext(), "Login Successful!",Toast.LENGTH_LONG).show();
        viewTicket = (Button)findViewById(R.id.viewTicket);

        viewChangePassword = (Button)findViewById(R.id.changePassword);
        viewTicket.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                openTickets();
            }
        });
        viewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePassword();
            }
        });

    }
    public void openTickets(){
        Intent intent = new Intent(this, ViewPursTicketsActivity.class);
        startActivity(intent);
    }
    public void openChangePassword() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);

    }
}
