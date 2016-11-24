package com.teamflightclub.flightclub;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordActivity extends AppCompatActivity{
    Button changePasswordbutton;
    TextView changePasswordText;
    EditText changePasswordCurrentPass;
    EditText changePasswordNewPassword;
    EditText changePasswordNewConfirmPass;
    String rowID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
         changePasswordbutton = (Button)findViewById(R.id.change_password_button);
         changePasswordText = (TextView)findViewById(R.id.change_password_text);
         changePasswordCurrentPass = (EditText)findViewById(R.id.currentPassword_changepassword);
         changePasswordNewPassword = (EditText)findViewById(R.id.newPassword_changepassword);
         changePasswordNewConfirmPass = (EditText)findViewById(R.id.newconfirmPassword_changepassword);
         changePasswordbutton.setEnabled(false);
        Intent intent = getIntent();
        rowID = intent.getStringExtra("rowID");
        //Log.v("myApp","Row ID = "+rowID);

        changePasswordCurrentPass.addTextChangedListener( mTextWatcher);
        changePasswordNewPassword.addTextChangedListener( mTextWatcher);
        changePasswordNewConfirmPass.addTextChangedListener( mTextWatcher);

         changePasswordbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
               // Log.d("ACCT_CLICK", "You have clicked on the create acct text");
                ChangePasswordClicked();

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

    public void ChangePasswordClicked(){
        String oldPassword = changePasswordCurrentPass.getText().toString();
        String newPassword = changePasswordNewPassword.getText().toString();
        String confirmPassword = changePasswordNewConfirmPass.getText().toString();
        //Log.v("myApp","Row ID = "+rowID);
        if (!newPassword.equals(confirmPassword)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage("Passwords do not match.");
            alertDialog.show();
        }
        else if (oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage("Please enter all fields.");
            alertDialog.show();
        }
        else {
            ChangePasswordAuthenticator changePasswordAuthenticator = new ChangePasswordAuthenticator(this);
            changePasswordAuthenticator.execute(rowID,oldPassword,newPassword);
        }
    }

    public void checkText()
    {


        String s1 = changePasswordNewPassword.getText().toString();
        String s2 = changePasswordNewConfirmPass.getText().toString();
        String s3 =  changePasswordCurrentPass.getText().toString();

        if(s1.equals("")|| s2.equals("") || s3.equals("")){
            changePasswordbutton.setEnabled(false);
        } else {
            changePasswordbutton.setEnabled(true);
        }
    }

//    @Override
//    public void done() {
//        finish();
//    }
}

