package com.parse.starter1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

/**
 * Created by ashishyadav271 on 2/13/2016.
 */
public class ActivityItemDetails extends AppCompatActivity implements View.OnClickListener {
    Toolbar tb;
    TextView title,oldPrice,newPrice,discount,description,stock;
    ImageView productImage;
    ImageButton selectImage1,selectImage2,selectImage3,selectImage4;
    String titleString,oldPriceString,newPriceString,stockString,discountString,descriptionString,url1,url2,url3,url4;
    Button buyNow;
    Button addToFav;
    MaterialFavoriteButton materialFavoriteButtonNice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        materialFavoriteButtonNice =
                (MaterialFavoriteButton) findViewById(R.id.favorite_nice);

        titleString = getIntent().getStringExtra("title");
        descriptionString = getIntent().getStringExtra("description");
        oldPriceString = getIntent().getStringExtra("oldPrice");
        newPriceString = getIntent().getStringExtra("newPrice");
        discountString=getIntent().getStringExtra("discount");
        stockString=getIntent().getStringExtra("availibility");
        url1 = getIntent().getStringExtra("imageUrl1");
        url2 = getIntent().getStringExtra("imageUrl2");
        url3 = getIntent().getStringExtra("imageUrl3");
        url4 = getIntent().getStringExtra("imageUrl4");
        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Favitems", Context.MODE_PRIVATE, null);
        try{
            Cursor c=sqLiteDatabase.rawQuery("SELECT *  FROM favitemtable",null);
            c.moveToFirst();
            while(c != null) {
                String geTtitleFromDb = c.getString(0);
                Log.e("DetailsTitle", geTtitleFromDb);
                if(geTtitleFromDb.equals(titleString)){
                    //materialFavoriteButtonNice.performClick();
                    materialFavoriteButtonNice.setFavorite(true, false);
                    break;
                }
                //Log.e("DetailsPrice", c.getString(1));
                c.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqLiteDatabase.close();
        }
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Item Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        title = (TextView) findViewById(R.id.itemtitle);
        oldPrice = (TextView) findViewById(R.id.itemoldprice);
        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        newPrice = (TextView) findViewById(R.id.itemnewprice);
        discount = (TextView) findViewById(R.id.itemdiscount);
        description = (TextView) findViewById(R.id.itemdesc);
        stock = (TextView) findViewById(R.id.itemstock);

        productImage = (ImageView) findViewById(R.id.itemimage);
        selectImage1 = (ImageButton) findViewById(R.id.itemimagebutton1);
        selectImage2 = (ImageButton) findViewById(R.id.itemimagebutton2);
        selectImage3 = (ImageButton) findViewById(R.id.itemimagebutton3);
        selectImage4 = (ImageButton) findViewById(R.id.itemimagebutton4);

        title.setText(titleString);
        description.setText(descriptionString);
        oldPrice.setText(oldPriceString);
        newPrice.setText(newPriceString);
        discount.setText(discountString);
        stock.setText(stockString);

        Picasso.with(this).load(url1).into(productImage);
        Picasso.with(this).load(url1).into(selectImage1);
        Picasso.with(this).load(url2).into(selectImage2);
        Picasso.with(this).load(url3).into(selectImage3);
        Picasso.with(this).load(url4).into(selectImage4);

        selectImage1.setOnClickListener(this);
        selectImage2.setOnClickListener(this);
        selectImage3.setOnClickListener(this);
        selectImage4.setOnClickListener(this);

        buyNow = (Button) findViewById(R.id.buyNow);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),BuyNowSelectAddress.class);
                i.putExtra("Title",titleString);
                i.putExtra("Quantity",stockString);
                i.putExtra("NewPrice",newPriceString);
                i.putExtra("Discount",discountString);
                startActivity(i);
            }
        });
        addToFav = (Button) findViewById(R.id.fav);
        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ActivityItemDetails.this, "Item Added to CART...", Toast.LENGTH_SHORT).show();
            }
        });

        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {

                            SQLiteDatabase sqLiteDatabase = getApplicationContext().openOrCreateDatabase("Favitems", MODE_PRIVATE, null);
                            try {
                                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS favitemtable (title VARCHAR,oldPrice VARCHAR,newPrice VARCHAR,discount VARCHAR,description VARCHAR,image1 VARCHAR,image2 VARCHAR,image3 VARCHAR,image4 VARCHAR)");
                                sqLiteDatabase.execSQL("INSERT INTO favitemtable(title,oldPrice,newPrice,discount,description,image1,image2,image3,image4) VALUES ('" + titleString + "','" + oldPriceString + "','" + newPriceString + "','" + discountString + "','" + descriptionString + "','" + url1 + "','" + url2 + "','" + url3 + "','" + url4 + "')");
                                Cursor c = sqLiteDatabase.rawQuery("SELECT *  FROM favitemtable", null);
                                c.moveToFirst();
                                while (c != null) {
                                    Log.e("Title", c.getString(0));
                                    Log.e("Title", c.getString(1));
                                    c.moveToNext();
                                }
                                Toast.makeText(ActivityItemDetails.this, "Item Added as favorite YAY :)", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.e("SQLITE EERROR", e.getMessage());
                            }finally {
                                sqLiteDatabase.close();
                            }

                            //niceCounterValue++;
                        } else {
                            SQLiteDatabase sqLiteDatabase = getApplicationContext().openOrCreateDatabase("Favitems", MODE_PRIVATE, null);
                            sqLiteDatabase.execSQL("DELETE FROM favitemtable where title = '"+titleString+"'");
                            sqLiteDatabase.close();
                            Toast.makeText(ActivityItemDetails.this, "Item Removed from favorites :(", Toast.LENGTH_SHORT).show();
                            //niceCounterValue--;
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.itemimagebutton1:

                Picasso.with(this).load(url1).into(productImage);
                break;
            case R.id.itemimagebutton2:

                Picasso.with(this).load(url2).into(productImage);
                break;
            case R.id.itemimagebutton3:

                Picasso.with(this).load(url3).into(productImage);
                break;
            case R.id.itemimagebutton4:

                Picasso.with(this).load(url4).into(productImage);
                break;
        }
    }
}
