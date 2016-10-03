package com.parse.starter1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by ashishyadav271 on 2/13/2016.
 */
public class Subscribe extends AppCompatActivity {
    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribe);
        tb= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Subscribe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
