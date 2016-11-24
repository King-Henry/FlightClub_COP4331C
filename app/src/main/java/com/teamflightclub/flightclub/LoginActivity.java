package com.teamflightclub.flightclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView account_creation;
    EditText email_input;
    EditText password_input;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account_creation = (TextView)findViewById(R.id.create_acct);
        email_input = (EditText)findViewById(R.id.email_text);
        password_input = (EditText)findViewById(R.id.password_text);
        login = (Button)findViewById(R.id.login_button);
        login.setEnabled(false);
        account_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ACCT_CLICK", "You have clicked on the create acct text");
                goToCreateAccount();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginClicked();

            }
        });

        password_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!password_input.getText().toString().equals("")
                        && !email_input.getText().toString().equals("")){

                    login.setEnabled(true);
                }
                else{
                    login.setEnabled(false);
                }

                return;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void LoginClicked(){

        String email = email_input.getText().toString();
        String password = password_input.getText().toString();
        LoginAuthenticator loginAuthenticator = new LoginAuthenticator(this);
        loginAuthenticator.execute(email,password);


    }

    public void goToCreateAccount(){
        Intent createAccount = new Intent(this, CreateAccountActivity.class);
        startActivity(createAccount);
    }



}
