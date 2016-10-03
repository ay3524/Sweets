package com.parse.starter1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ashishyadav271 on 2/12/2016.
 */
public class FavFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    LinearLayoutManager manager;
    ArrayList<ItemsModel> itemsModels = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fav, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.recycle);

        manager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(manager);

        try{
            SQLiteDatabase sqLiteDatabase=getActivity().openOrCreateDatabase("Favitems", Context.MODE_PRIVATE, null);
            Cursor c=sqLiteDatabase.rawQuery("SELECT *  FROM favitemtable", null);
            int titleColumnIndex = c.getColumnIndex("title");
            int oldPriceColumnIndex = c.getColumnIndex("oldPrice");
            int newPriceColumnIndex = c.getColumnIndex("newPrice");
            int discountColumnIndex = c.getColumnIndex("discount");
            int descriptionColumnIndex = c.getColumnIndex("description");
            int image1ColumnIndex = c.getColumnIndex("image1");
            int image2ColumnIndex = c.getColumnIndex("image2");
            int image3ColumnIndex = c.getColumnIndex("image3");
            int image4ColumnIndex = c.getColumnIndex("image4");

            c.moveToFirst();
            while(c!=null) {
                String title = c.getString(titleColumnIndex);
                String oldPrice = c.getString(oldPriceColumnIndex);
                String newPrice = c.getString(newPriceColumnIndex);
                String discount = c.getString(discountColumnIndex);
                String description = c.getString(descriptionColumnIndex);
                String image1 = c.getString(image1ColumnIndex);
                String image2 = c.getString(image2ColumnIndex);
                String image3 = c.getString(image3ColumnIndex);
                String image4 = c.getString(image4ColumnIndex);
                ItemsModel model = new ItemsModel();
                //model.setDate(object.getCreatedAt().toString());
                model.setTitle(title);
                model.setAvailibilty("In Stock");
                model.setOldPrice(oldPrice);
                model.setNewPrice(newPrice);
                model.setDiscount(discount);
                model.setDesc(description);
                model.setImagefile1(image1);
                model.setImagefile2(image2);
                model.setImagefile3(image3);
                model.setImagefile4(image4);
                //model.setObjectId(object.getObjectId());
                itemsModels.add(model);
                //Log.e("Title", c.getString(0));
                //Log.e("Price", c.getString(1));
                c.moveToNext();
            }
            adapter = new RecyclerViewAdapter(itemsModels,getActivity());
            //adapter.setClickListener(FavFragment.this);
            recyclerView.setAdapter(adapter);

        }catch (Exception e){

        }

            return layout;
    }
}
