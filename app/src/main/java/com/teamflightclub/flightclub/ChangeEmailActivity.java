package com.teamflightclub.flightclub;

import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeEmailActivity extends AppCompatActivity implements AsyncCallback{

    Button changeEmailbutton;
    TextView changeEmailText;
    EditText changeEmailCurrentEmail;
    EditText changeEmailNewEmail;
    EditText changeEmailNewConfirmEmail;
    String rowID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        changeEmailbutton = (Button)findViewById(R.id.change_Email_button);
        changeEmailText = (TextView)findViewById(R.id.change_Email_text);
        changeEmailCurrentEmail = (EditText)findViewById(R.id.currentEmail_changeEmail);
        changeEmailNewEmail = (EditText)findViewById(R.id.newEmail_changeEmail);
        changeEmailNewConfirmEmail = (EditText)findViewById(R.id.newconfirmEmail_changeEmail);
        changeEmailbutton.setEnabled(false);
        rowID = PreferenceManager.getDefaultSharedPreferences(this).getString("userRowID","");
        Log.v("ChangeEmailActivityID", rowID);

        changeEmailCurrentEmail.addTextChangedListener( mTextWatcher);
        changeEmailNewEmail.addTextChangedListener( mTextWatcher);
        changeEmailNewConfirmEmail.addTextChangedListener( mTextWatcher);

        changeEmailbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // Log.d("ACCT_CLICK", "You have clicked on the create acct text");

                ChangeEmailClicked();


            }
        });

    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkText();
        }
    };

    public void ChangeEmailClicked(){
        String oldEmail = changeEmailCurrentEmail.getText().toString();
        String newEmail = changeEmailNewEmail.getText().toString();
        String confirmEmail = changeEmailNewConfirmEmail.getText().toString();
        //Log.v("myApp","Row ID = "+rowID);
        if (!newEmail.equals(confirmEmail)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage("Emails do not match.");
            alertDialog.show();
        }
        else if (oldEmail.equals("") || newEmail.equals("") || confirmEmail.equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage("Please enter all fields.");
            alertDialog.show();
        }
        else {
            ChangeEmailAuthenticator changeEmailAuthenticator = new ChangeEmailAuthenticator(this,this);
            changeEmailAuthenticator.execute(rowID,oldEmail,newEmail);
        }
    }

    public void checkText()
    {


        String s1 = changeEmailNewEmail.getText().toString();
        String s2 = changeEmailNewConfirmEmail.getText().toString();
        String s3 =  changeEmailCurrentEmail.getText().toString();

        if(s1.equals("")|| s2.equals("") || s3.equals("")){
            changeEmailbutton.setEnabled(false);
        } else {
            changeEmailbutton.setEnabled(true);
        }
    }
    public void done(){

    }

    @Override
    public void done() {


        finish();
    }
}

