package com.parse.starter1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by ashishyadav271 on 2/13/2016.
 */
public class MyAccount extends AppCompatActivity {
    Toolbar tb;
    String phoneNumber;
    TextView phone;

    public void addressCardClicked(View v){
        //Intent i=new Intent(getApplicationContext(),ListOfAddresses.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount);
        phoneNumber = getIntent().getStringExtra("PHONE1");
        tb= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Button b = (Button) findViewById(R.id.logout);
        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.parse.starter1", MODE_PRIVATE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            b.setClickable(false);
                            sharedPreferences.edit().putString("Hello","No").apply();
                            Intent mainIntent = new Intent(getApplicationContext(), ChooseLoginOrSignUp.class);
                            startActivity(mainIntent);
                            finish();
                        }else{
                            b.setClickable(true);
                            Toast.makeText(MyAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_account, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {

            startActivity(new Intent(getApplicationContext(),EditMyAccount.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
