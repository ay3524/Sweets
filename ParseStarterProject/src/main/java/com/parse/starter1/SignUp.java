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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by ashishyadav271 on 2/11/2016.
 */
public class SignUp extends Activity{

    EditText phone,password,confirmPassword;
    TextInputLayout phonelayout,passwordlayout,confirmpasswordlayout,checkboxlayout;
    CheckBox terms;
    Button b;
    TextView loginHere;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signup);
        phone = (EditText) findViewById(R.id.input_number);
        password = (EditText) findViewById(R.id.input_password);
        confirmPassword = (EditText) findViewById(R.id.input_confirm_password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        terms = (CheckBox) findViewById(R.id.chechbox);

        phonelayout = (TextInputLayout) findViewById(R.id.numberlayout);
        passwordlayout = (TextInputLayout) findViewById(R.id.passlayout);
        confirmpasswordlayout = (TextInputLayout) findViewById(R.id.confirmpasslayout);
        checkboxlayout = (TextInputLayout) findViewById(R.id.chechboxlayout);

        b = (Button) findViewById(R.id.button);

        phone.addTextChangedListener(new MyTextWatcher(phone));
        password.addTextChangedListener(new MyTextWatcher(password));
        confirmPassword.addTextChangedListener(new MyTextWatcher(confirmPassword));


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        loginHere= (TextView) findViewById(R.id.loginfromhere);
        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNextActivity = new Intent(getApplicationContext(), Login.class);
                finish();
                startActivity(launchNextActivity);
            }
        });
    }

    private void createAccount() {
        final String uphone = phone.getText().toString();
        final String upass = password.getText().toString();
        final String uConfirrmPass = confirmPassword.getText().toString();
        /*if(uphone.length() != 10){
            Toast.makeText(getApplicationContext(), "Phone Number Not Valid ", Toast.LENGTH_SHORT).show();
        }else if(upass.length() < 5){
            Toast.makeText(getApplicationContext(), "Password must be of at least 5 characters", Toast.LENGTH_SHORT).show();
            password.setText("");
        }else if(!upass.equals(uConfirrmPass)){
            Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
            confirmPassword.setText("");*/
        if (!validatePhone()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validateConfirmPassword()) {
            return;
        }
        if (!validateCheckbox()) {
            return;
        }
        /**/
        if(Utils.isOnline(getApplicationContext())) {
            phone.setEnabled(false);
            password.setEnabled(false);
            confirmPassword.setEnabled(false);
            b.setEnabled(false);
            terms.setEnabled(false);
            loginHere.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            ParseUser user = new ParseUser();
            user.setUsername(uphone);
            user.setPassword(upass);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        logUserIn(uphone, upass);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        phone.setEnabled(true);
                        password.setEnabled(true);
                        confirmPassword.setEnabled(true);
                        b.setEnabled(true);
                        loginHere.setEnabled(true);
                        terms.setEnabled(false);
                        phone.setText("");
                        password.setText("");
                        confirmPassword.setText("");
                    }
                }
            });
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            loginHere.setEnabled(true);
            Toast.makeText(SignUp.this, "Connect to Internet and Try Again", Toast.LENGTH_SHORT).show();
        }
    }


    private void logUserIn(final String uphone, String upass) {
        ParseUser.logInInBackground(uphone, upass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent launchNextActivity;
                    launchNextActivity = new Intent(getApplicationContext(), MainActivity.class);
                    launchNextActivity.putExtra("PHONE",uphone);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(launchNextActivity);
                }else{
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateConfirmPassword() {
        if (!password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
            confirmpasswordlayout.setError("Password do not match");
            requestFocus(confirmPassword);
            return false;
        } else {
            confirmpasswordlayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        String passwordString = password.getText().toString().trim();
        if (passwordString.isEmpty() || passwordString.length() < 5) {
            passwordlayout.setError("Password must be of at least 5 characters");
            requestFocus(password);
            return false;
        } else {
            passwordlayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhone() {
        String phoneNumber = phone.getText().toString().trim();
        if (phoneNumber.isEmpty() || phoneNumber.length() != 10) {
            phonelayout.setError("Invalid Phone Number");
            requestFocus(phone);
            return false;
        } else {
            phonelayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCheckbox() {
        if (!terms.isChecked()) {
            checkboxlayout.setError("Agree to terms and conditions");
            requestFocus(phone);
            return false;
        } else {
            checkboxlayout.setErrorEnabled(false);
        }
        return true;
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
                case R.id.input_number:
                    validatePhone();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_confirm_password:
                    validateConfirmPassword();
                    break;
            }
        }
    }

}
