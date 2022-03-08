package com.example.izmirtripapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.izmirtripapp.Model.PlaceData;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {

    PlaceData[] placeData;
    Context context;

    public PlaceListAdapter(PlaceData[] placeData, HomePage activity) {
        this.placeData = placeData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.popular_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PlaceData placeDataList = placeData[position];
        holder.textView.setText(placeDataList.getTitle());
        holder.listImage.setImageResource(placeDataList.getImage());
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlaceDetailActivity.class);

            }
        });

    }

    @Override
    public int getItemCount() {
        return placeData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView listImage ;
        TextView textView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            listImage = itemView.findViewById(R.id.poplarListImageView);
            textView = itemView.findViewById(R.id.listName);
        }
    }
}
