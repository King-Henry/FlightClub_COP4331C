package com.teamflightclub.flightclub;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    EditText first_input, last_input, email_input, password_input, confirmPassword_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        first_input = (EditText)findViewById(R.id.firstName_createAccount);
        last_input = (EditText)findViewById(R.id.lastName_createAccount);
        email_input = (EditText)findViewById(R.id.emailAddress_createAccount);
        password_input = (EditText)findViewById(R.id.password_createAccount);
        confirmPassword_input = (EditText)findViewById(R.id.confirmPassword_createAccount);
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
        Intent I = new Intent(this, LoginActivity.class);
        startActivity(I);
    }
}
