package com.parse.starter1;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ashishyadav271 on 2/12/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ClickListener clickListener;
    ArrayList<ItemsModel> itemsModels=null;
    Context context;

    RecyclerViewAdapter(ArrayList<ItemsModel> itemsModels,Context context){
        this.itemsModels=itemsModels;
        this.context=context;
    }
    public void updateData(ArrayList<ItemsModel> itemsModels){
        this.itemsModels=itemsModels;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(itemsModels.get(position).getTitle());
        holder.oldPrice.setText(itemsModels.get(position).getOldPrice());
        holder.newPrice.setText(itemsModels.get(position).getNewPrice());
        holder.newPrice.setText(itemsModels.get(position).getNewPrice());
        holder.availibility.setText(itemsModels.get(position).getAvailibilty());
        holder.discount.setText(itemsModels.get(position).getDiscount());
        Picasso.with(context).load(itemsModels.get(position).getImagefile1()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return itemsModels.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,oldPrice,newPrice,availibility,discount;
        ImageView productImage;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.view = itemView;
            productImage = (ImageView) itemView.findViewById(R.id.imgRow);
            title = (TextView) itemView.findViewById(R.id.title);
            oldPrice = (TextView) itemView.findViewById(R.id.oldPrice);
            oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            newPrice = (TextView) itemView.findViewById(R.id.newPrice);
            availibility = (TextView) itemView.findViewById(R.id.stock);
            discount = (TextView) itemView.findViewById(R.id.discount);
        }

        @Override
        public void onClick(View v) {
            if(clickListener!=null){
                clickListener.onItemClick(view , getAdapterPosition());
            }

        }
    }
    public interface ClickListener{
        void onItemClick(View v, int pos);
    }
}
