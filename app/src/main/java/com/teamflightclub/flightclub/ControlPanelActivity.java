package com.teamflightclub.flightclub;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teamflightclub.flightclub.ui.ViewPursTicketsActivity;

public class ControlPanelActivity extends AppCompatActivity {

        Button viewTicket;
        Button viewChangePassword;
        Button viewChangeEmail;
        String rowID;
        Button viewQRCode;
        static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";


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
        viewQRCode = (Button)findViewById(R.id.viewQR);

        viewChangePassword = (Button)findViewById(R.id.changePassword);

        viewChangeEmail = (Button)findViewById(R.id.changeEmail);
        viewQRCode.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
               // openQRCode();
                scanQR(v);

            }
        });
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

    //product barcode mode
    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(ControlPanelActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(ControlPanelActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}
