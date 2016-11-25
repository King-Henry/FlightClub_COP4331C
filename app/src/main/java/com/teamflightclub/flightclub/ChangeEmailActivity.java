package com.teamflightclub.flightclub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeEmailActivity extends AppCompatActivity {

    Button changeEmailbutton;
    TextView changeEmailText;
    EditText changeEmailCurrentEmail;
    EditText changeEmailNewEmail;
    EditText changeEmailNewConfirmEmail;
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

        changeEmailCurrentEmail.addTextChangedListener( mTextWatcher);
        changeEmailNewEmail.addTextChangedListener( mTextWatcher);
        changeEmailNewConfirmEmail.addTextChangedListener( mTextWatcher);

        changeEmailbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
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


        String s1 = changeEmailNewEmail.getText().toString();
        String s2 = changeEmailNewConfirmEmail.getText().toString();
        String s3 =  changeEmailCurrentEmail.getText().toString();

        if(s1.equals("")|| s2.equals("") || s3.equals("")){
            changeEmailbutton.setEnabled(false);
        } else {
            changeEmailbutton.setEnabled(true);
        }
    }

}

