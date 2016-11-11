package com.teamflightclub.flightclub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        account_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ACCT_CLICK", "You have clicked on the create acct text");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginClicked();
            }
        });



    }

    public void LoginClicked(){

        String email = email_input.getText().toString();
        String password = password_input.getText().toString();
        LoginAuthenticator loginAuthenticator = new LoginAuthenticator(this);
        loginAuthenticator.execute(email,password);

    }


}
