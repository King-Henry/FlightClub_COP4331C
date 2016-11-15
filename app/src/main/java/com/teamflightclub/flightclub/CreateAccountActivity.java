package com.teamflightclub.flightclub;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    EditText first_input, last_input, email_input, password_input, confirmPassword_input;
    Button creatAcct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        first_input = (EditText)findViewById(R.id.firstName_createAccount);
        last_input = (EditText)findViewById(R.id.lastName_createAccount);
        email_input = (EditText)findViewById(R.id.emailAddress_createAccount);
        password_input = (EditText)findViewById(R.id.password_createAccount);
        confirmPassword_input = (EditText)findViewById(R.id.confirmPassword_createAccount);
        creatAcct = (Button)findViewById((R.id.createAccountButton));
        creatAcct.setEnabled(false);

        confirmPassword_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!first_input.getText().toString().equals("") && !last_input.getText().toString().equals("")
                        && !email_input.getText().toString().equals("") && !password_input.getText().toString().equals("")
                        && !confirmPassword_input.getText().toString().equals("")){

                    creatAcct.setEnabled(true);
                }

                else{

                    creatAcct.setEnabled(false);
                }

                return;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void SignUp(View view) {
        String firstName = first_input.getText().toString();
        String lastName = last_input.getText().toString();
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();
        String confirmPassword = confirmPassword_input.getText().toString();
        if (!password.equals(confirmPassword)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage("Passwords do not match.");
            alertDialog.show();
        }
        else if (firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("ERROR");
            alertDialog.setMessage("Please enter all fields.");
            alertDialog.show();
        }
        else {
            CreateAccountAuthenticator createAccountAuthenticator = new CreateAccountAuthenticator(this);
            createAccountAuthenticator.execute(firstName,lastName,email,password);
        }
    }

    public void returnToLogin(View view){
        Intent returnToLogin = new Intent(this, LoginActivity.class);
        startActivity(returnToLogin);
    }
}
