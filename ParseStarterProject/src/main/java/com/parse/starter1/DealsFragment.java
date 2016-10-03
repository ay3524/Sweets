package com.parse.starter1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashishyadav271 on 2/12/2016.
 */
public class DealsFragment extends Fragment  implements RecyclerViewAdapter.ClickListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    LinearLayoutManager manager;
    ArrayList<ItemsModel> itemsModels = new ArrayList<>();
    ProgressDialog dialog;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.deals, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.recycle);

        manager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(manager);

        recyclerView.setOnScrollListener(new EndlessRecyclerView(manager) {
            @Override
            public void onLoadMore(int current_page) {
                int limit = current_page * 8;
                loadMoreItems(limit);
            }
        });

        return layout;
    }

    public void loadMoreItems(int limit){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ProductDetails");
        query.setLimit(limit).orderByAscending("createdAt");
        dialog=new ProgressDialog(getActivity());
        dialog.setTitle("Loading");
        dialog.setMessage("Please Wait...");
        dialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                dialog.dismiss();
                if(e==null){
                    itemsModels.clear();
                    for(int i = 0 ; i < objects.size() ; i++){
                        ParseObject object=objects.get(i);
                        ItemsModel model = new ItemsModel();
                        model.setDate(object.getCreatedAt().toString());

                        model.setTitle(object.getString("title"));
                        model.setAvailibilty(object.getString("availibility"));
                        model.setOldPrice(object.getString("oldPrice"));
                        model.setNewPrice(object.getString("newPrice"));
                        model.setDiscount(object.getString("discount"));
                        model.setDesc(object.getString("description"));
                        model.setImagefile1(object.getParseFile("imagefile1")
                                .getUrl());
                        model.setImagefile2(object.getParseFile("imagefile2")
                                .getUrl());
                        model.setImagefile3(object.getParseFile("imagefile3")
                                .getUrl());
                        model.setImagefile4(object.getParseFile("imagefile4")
                                .getUrl());

                        model.setObjectId(object.getObjectId());
                        itemsModels.add(model);

                    }
                    adapter.updateData(itemsModels);
                }else if (e.getCode() == ParseException.CONNECTION_FAILED){
                    Toast.makeText(getActivity(), "No Internet Connection ! Please Check your Internet Connection", Toast.LENGTH_LONG).show();


                }else{
                    Toast.makeText(getActivity(),"Error : "+e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void loadLessData(){
        if(adapter != null){
            dialog.dismiss();
        }else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("ProductDetails");
            query.setLimit(8).orderByAscending("createdAt");
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Loading");
            dialog.setMessage("Please Wait...");
            dialog.show();
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    dialog.dismiss();
                    if (e == null) {
                        itemsModels.clear();
                        for (int i = 0; i < objects.size(); i++) {
                            ParseObject object = objects.get(i);
                            ItemsModel model = new ItemsModel();
                            model.setDate(object.getCreatedAt().toString());

                            model.setTitle(object.getString("title"));
                            model.setAvailibilty(object.getString("availibility"));
                            model.setOldPrice(object.getString("oldPrice"));
                            model.setNewPrice(object.getString("newPrice"));
                            model.setDiscount(object.getString("discount"));
                            model.setDesc(object.getString("description"));
                            model.setImagefile1(object.getParseFile("imagefile1")
                                    .getUrl());
                            model.setImagefile2(object.getParseFile("imagefile2")
                                    .getUrl());
                            model.setImagefile3(object.getParseFile("imagefile3")
                                    .getUrl());
                            model.setImagefile4(object.getParseFile("imagefile4")
                                    .getUrl());

                            model.setObjectId(object.getObjectId());
                            itemsModels.add(model);

                        }
                        adapter = new RecyclerViewAdapter(itemsModels, getActivity());
                        adapter.setClickListener(DealsFragment.this);
                        recyclerView.setAdapter(adapter);
                    } else if (e.getCode() == ParseException.CONNECTION_FAILED) {
                        Toast.makeText(getActivity(), "No Internet Connection ! Please Check your Internet Connection", Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setOnScrollListener(new EndlessRecyclerView(manager) {
            @Override
            public void onLoadMore(int current_page) {
                int limit = current_page * 8;
                loadMoreItems(limit);
            }
        });

        loadLessData();
    }

    @Override
    public void onItemClick(View v, int pos) {
        Intent i = new Intent(getActivity(),ActivityItemDetails.class);
        ItemsModel model=itemsModels.get(pos);
        i.putExtra("title",model.getTitle());
        i.putExtra("oldPrice",model.getOldPrice());
        i.putExtra("newPrice",model.getNewPrice());
        i.putExtra("date",model.getDate());
        i.putExtra("description",model.getDesc());
        i.putExtra("availibility",model.getAvailibilty());
        i.putExtra("discount",model.getDiscount());
        i.putExtra("imageUrl1",model.getImagefile1());
        i.putExtra("imageUrl2",model.getImagefile2());
        i.putExtra("imageUrl3",model.getImagefile3());
        i.putExtra("imageUrl4",model.getImagefile4());
        i.putExtra("objectId",model.getObjectId());
        startActivity(i);
    }
}
