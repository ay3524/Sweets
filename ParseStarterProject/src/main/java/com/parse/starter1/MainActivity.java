/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity{
    Toolbar tb;
    DrawerLayout mDrawerLayout;
    ActionBar ab;
    Intent mShareIntent;
    ShareActionProvider mShareActionProvider;
    String phoneNumber;

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }



    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        //ParseUser.getCurrentUser().logOut();
        setContentView(R.layout.activity_main);
        //ParseUser.getCurrentUser().logOut();

        phoneNumber = getIntent().getStringExtra("PHONE");

        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "From me to you, this text is new.");

        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.parse.starter1", MODE_PRIVATE);
        sharedPreferences.edit().putString("Hello","Yes").apply();

        tb= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Sweet's Shop");

        ab=getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);




    ParseAnalytics.trackAppOpenedInBackground(getIntent());

      /*ParseUser currentUser = ParseUser.getCurrentUser();
      currentUser.logOut();*/

      /*ParseObject testObject = new ParseObject("TestObject");
      testObject.put("foo", "bar");
      testObject.saveInBackground(new SaveCallback() {
          public void done(ParseException e) {
              if (e != null) {
                  e.printStackTrace();
              }
          }
      });*/

  }

    private void setupDrawerContent(NavigationView navigationView) {
        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.parse.starter1", MODE_PRIVATE);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.nav_home:

                                break;
                            case R.id.nav_account:
                                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null){
                                            //b.setClickable(false);
                                            sharedPreferences.edit().putString("Hello","No").apply();
                                            Intent mainIntent = new Intent(getApplicationContext(), ChooseLoginOrSignUp.class);
                                            startActivity(mainIntent);
                                            finish();
                                        }else{
                                            //b.setClickable(true);
                                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                            case R.id.nav_notify:
                                startActivity(new Intent(getApplicationContext(),Notifications.class));
                                break;
                            case R.id.nav_fav:
                                Toast.makeText(getApplicationContext(),"Fav",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_subs:
                                startActivity(new Intent(getApplicationContext(),Subscribe.class));
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        // Get its ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Connect the dots: give the ShareActionProvider its Share Intent
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(mShareIntent);
        }

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    /*if (id == R.id.action_share) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hello World");

        try {
            startActivity(Intent.createChooser(intent, "Select an action"));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }

      return true;
    }*/
      if (id == R.id.action_play) {
          Toast.makeText(MainActivity.this, "This app is not available on store yet", Toast.LENGTH_SHORT).show();

          return true;
      }
      if(id == android.R.id.home) {
          mDrawerLayout.openDrawer(GravityCompat.START);
          return true;
      }

    return super.onOptionsItemSelected(item);
  }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(MyFragment.getInstance(0), "HOT DEALS");
        adapter.addFragment(MyFragment.getInstance(1), "FAVORITES");
        viewPager.setAdapter(adapter);
    }
}
