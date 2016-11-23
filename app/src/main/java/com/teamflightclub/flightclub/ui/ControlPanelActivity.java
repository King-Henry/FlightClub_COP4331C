package com.teamflightclub.flightclub.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.teamflightclub.flightclub.R;
import android.widget.Toast;

public class ControlPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        Toast.makeText(getApplicationContext(), "Login Successful!",Toast.LENGTH_LONG).show();
    }
}
