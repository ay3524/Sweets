package com.parse.starter1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ashishyadav271 on 2/15/2016.
 */
public class BuyNowSelectAddress extends AppCompatActivity {
    Toolbar tb;
    Button proceed;
    EditText name,pin,address,landmark,city,state,phone;
    String titleString,quantityString,priceString,discountString;
    TextView itemTitle,itemQuantity,itemPrice,itemDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buynowselectaddress);
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        itemTitle = (TextView) findViewById(R.id.itemTitle);
        itemQuantity = (TextView) findViewById(R.id.itemQuantity);
        itemPrice = (TextView) findViewById(R.id.itemPrice);
        itemDiscount = (TextView) findViewById(R.id.itemDiscount);


        titleString = getIntent().getStringExtra("Title");
        quantityString = getIntent().getStringExtra("Quantity");
        priceString = getIntent().getStringExtra("NewPrice");
        discountString = getIntent().getStringExtra("Discount");

        itemTitle.setText(titleString);
        itemQuantity.setText(quantityString);
        itemPrice.setText("Price : " + priceString);
        itemDiscount.setText("Discount : " + discountString);

        proceed= (Button) findViewById(R.id.proceed);
        name = (EditText) findViewById(R.id.input_name);
        pin = (EditText) findViewById(R.id.input_pincode);
        address = (EditText) findViewById(R.id.input_address);
        landmark = (EditText) findViewById(R.id.input_landmark);
        city = (EditText) findViewById(R.id.input_city);
        state = (EditText) findViewById(R.id.input_state);
        phone = (EditText) findViewById(R.id.input_phone);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String pin1 = pin.getText().toString();
                String address1 = address.getText().toString();
                String landmark1 = landmark.getText().toString();
                String city1 = city.getText().toString();
                String state1 = state.getText().toString();
                String phone1 = phone.getText().toString();

                if(name1.equals("")||pin1.equals("")||address1.equals("")||landmark1.equals("")||city1.equals("")||state1.equals("")||phone1.equals("")){
                    Toast.makeText(getApplicationContext(), "Some fields are empty.. Have a close look at them", Toast.LENGTH_LONG).show();
                    if(name1.length() < 3){
                        Toast.makeText(getApplicationContext(), "Name can't be of 2 characters", Toast.LENGTH_LONG).show();
                    }
                    else if(pin1.length() != 6){
                        Toast.makeText(getApplicationContext(), "Invalid Pincode", Toast.LENGTH_LONG).show();

                    }
                    else if(address1.length() < 10){
                        Toast.makeText(getApplicationContext(), "Provide more info in address field", Toast.LENGTH_LONG).show();

                    }
                    else if(landmark1.length() < 3){
                        Toast.makeText(getApplicationContext(), "Landmark can't be of 2 characters", Toast.LENGTH_LONG).show();
                    }
                    else if(city1.length() < 3){
                        Toast.makeText(getApplicationContext(), "City can't be of 2 characters", Toast.LENGTH_LONG).show();
                    }
                    else if(state1.length() < 2){
                        Toast.makeText(getApplicationContext(), "State can't be of 1 characters", Toast.LENGTH_LONG).show();
                    }
                    else if(phone1.length() != 10){
                        Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Thanks for shopping..Your order has been placed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        });
    }
}
