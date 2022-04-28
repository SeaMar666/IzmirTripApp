package com.example.izmirtripapp.Events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.izmirtripapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class myfestivaladapter extends FirebaseRecyclerAdapter<festivals, myfestivaladapter.myviewholder> {

    public myfestivaladapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull festivals festivals) {
        myviewholder.name.setText(festivals.getName());
        myviewholder.address.setText(festivals.getAddress());
        myviewholder.date.setText(festivals.getDate());
        Glide.with(myviewholder.img.getContext()).load(festivals.getImage()).into(myviewholder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerows,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name, address, date;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            address = (TextView) itemView.findViewById(R.id.coursetext);
            date = (TextView) itemView.findViewById(R.id.emailtext);
        }
    }
}
