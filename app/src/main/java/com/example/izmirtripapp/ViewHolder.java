package com.example.izmirtripapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View myView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            myView = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mClickListener.onItemClick(v,getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClickListener.onItemClick(v,getAdapterPosition());
                    return true;
                }
            });

        }

    public void setDetails(Context context, String image, String title){

        ImageView listImage = myView.findViewById(R.id.poplarListImageView);
        TextView textView = myView.findViewById(R.id.listName);

        textView.setText(title);
        Picasso.get().load(image).into(listImage);
    }

    private ClickListener mClickListener;

    public interface ClickListener {

        void onItemClick(View view, int position);
        void onItemLongListener(View view, int position);

    }

    public void setOnClickListener(ClickListener clickListener){

        mClickListener = clickListener;
    }
}
