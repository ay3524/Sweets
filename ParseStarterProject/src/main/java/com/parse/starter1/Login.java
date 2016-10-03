package com.parse.starter1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by ashishyadav271 on 2/11/2016.
 */
public class Login extends Activity {
    EditText phone,password;
    Button b;
    TextView createAccount;
    TextInputLayout phoneLayout,passwordLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        phone = (EditText) findViewById(R.id.input_number1);
        password = (EditText) findViewById(R.id.input_password1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        phone.addTextChangedListener(new MyTextWatcher(phone));
        password.addTextChangedListener(new MyTextWatcher(password));

        phoneLayout = (TextInputLayout) findViewById(R.id.numberlayout1);
        passwordLayout = (TextInputLayout) findViewById(R.id.passlayout1);

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logUserIn();
            }
        });
        createAccount = (TextView) findViewById(R.id.create);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNextActivity = new Intent(getApplicationContext(), SignUp.class);
                finish();
                startActivity(launchNextActivity);
            }
        });
    }

    private void logUserIn() {

        final String uphone = phone.getText().toString();
        final String upass = password.getText().toString();

        if (!validatePhone()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }


        if(Utils.isOnline(getApplicationContext())) {
            createAccount.setEnabled(false);
            phone.setEnabled(false);
            password.setEnabled(false);
            b.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            ParseUser.logInInBackground(uphone, upass, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        Intent launchNextActivity;
                        launchNextActivity = new Intent(getApplicationContext(), MainActivity.class);
                        launchNextActivity.putExtra("PHONE",uphone);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(launchNextActivity);
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        phone.setEnabled(true);
                        password.setEnabled(true);
                        b.setEnabled(true);
                        createAccount.setEnabled(true);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        password.setText("");
                    }
                }
            });
        }else{
            createAccount.setEnabled(false);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Login.this, "Connect to Internet and Try Again", Toast.LENGTH_SHORT).show();
        }
        }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_number1:
                    validatePhone();
                    break;
                case R.id.input_password1:
                    validatePassword();
                    break;
            }
        }


    }

    private boolean validatePhone() {
        String phoneNumber = phone.getText().toString().trim();
        if (phoneNumber.isEmpty() || phoneNumber.length() != 10) {
            phoneLayout.setError("Invalid Phone Number");
            requestFocus(phone);
            return false;
        } else {
            phoneLayout.setErrorEnabled(false);
        }
        return true;

    }
    private boolean validatePassword() {
        String passwordString = password.getText().toString().trim();
        if (passwordString.isEmpty()) {
            passwordLayout.setError("Password field is empty");
            requestFocus(password);
            return false;
        } else {
            passwordLayout.setErrorEnabled(false);
        }
        return true;
    }
}
