package com.example.izmirtripapp.Museums;

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

public class mymuseumadapter extends FirebaseRecyclerAdapter<ancient, mymuseumadapter.myviewholder> {

    public mymuseumadapter(@NonNull FirebaseRecyclerOptions<ancient> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull ancient ancient) {
        myviewholder.name.setText(ancient.getName());
        myviewholder.address.setText(ancient.getAddress());
        Glide.with(myviewholder.img.getContext()).load(ancient.getImage()).into(myviewholder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name, address;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            address = (TextView) itemView.findViewById(R.id.coursetext);
        }
    }
}
