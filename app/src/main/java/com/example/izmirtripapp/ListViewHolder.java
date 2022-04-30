package com.example.izmirtripapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.izmirtripapp.Model.PlaceData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListViewHolder extends FirebaseRecyclerAdapter<PlaceData, ListViewHolder.myViewHolder> {

    DatabaseReference favRef;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    public ListViewHolder(@NonNull FirebaseRecyclerOptions<PlaceData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull PlaceData placeData) {

        myViewHolder.textView.setText(placeData.getTitle());
        myViewHolder.nameText.setText(placeData.getName());
        Glide.with(myViewHolder.listImage.getContext()).load(placeData.getImage()).into(myViewHolder.listImage);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_list,parent,false);

       return new myViewHolder(view);
    }

    class myViewHolder extends  RecyclerView.ViewHolder{
        ImageView listImage;
        TextView textView;
        TextView nameText;
        ImageButton favBtn;

        public myViewHolder(@NonNull View itemView){
            super(itemView);
            listImage = itemView.findViewById(R.id.poplarListImageView);
            textView = itemView.findViewById(R.id.listName);
            nameText = itemView.findViewById(R.id.placeName);
        }
    }


}
