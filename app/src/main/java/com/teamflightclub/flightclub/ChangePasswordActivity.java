package com.teamflightclub.flightclub;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordActivity extends AppCompatActivity {
    Button changePasswordbutton;
    TextView changePasswordText;
    EditText changePasswordCurrentPass;
    EditText changePasswordNewPassword;
    EditText changePasswordNewConfirmPass;
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

        changePasswordCurrentPass.addTextChangedListener( mTextWatcher);
        changePasswordNewPassword.addTextChangedListener( mTextWatcher);
        changePasswordNewConfirmPass.addTextChangedListener( mTextWatcher);

         changePasswordbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                changePassword();
               // Log.d("ACCT_CLICK", "You have clicked on the create acct text");

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
    public void changePassword() {
        String passNew = changePasswordNewPassword.getText().toString();
        String passNewConfirm = changePasswordNewConfirmPass.getText().toString();
        String passwordCurrent = changePasswordCurrentPass.getText().toString();
        if (!passNew.equals(passNewConfirm)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage("Passwords do not match.");
            alertDialog.show();
        }
    }
    }

