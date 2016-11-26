package com.teamflightclub.flightclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teamflightclub.flightclub.ui.ViewPursTicketsActivity;

import java.util.ResourceBundle;

public class ControlPanelActivity extends AppCompatActivity {

        Button viewTicket;
        Button viewChangePassword;
        Button viewChangeEmail;
        String rowID;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_account_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.ctrl_panel_sign_out_button) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Sign Out");
            builder.setMessage("Are you Sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    signOutConfirm();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();

        }

        return false;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        viewTicket = (Button)findViewById(R.id.viewTicket);


        viewChangePassword = (Button)findViewById(R.id.changePassword);

        viewChangeEmail = (Button)findViewById(R.id.changeEmail);
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
        viewChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeEmail();
            }
        });

    }
    public void openTickets(){
        Intent intent = new Intent(this, ViewPursTicketsActivity.class);
        startActivity(intent);
    }
    public void openChangePassword() {
        //Log.v("myApp","Row ID = "+rowID);
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra("rowID",rowID);
        startActivity(intent);

    }
    public void openChangeEmail(){
        Intent intent = new Intent(this, ChangeEmailActivity.class);
        intent.putExtra("rowID",rowID);
        startActivity(intent);
    }

    public void signOutConfirm(){

        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("userRowID","").commit();
        Toast.makeText(this,"You have signed out",Toast.LENGTH_LONG).show();
        finish();
    }
}
