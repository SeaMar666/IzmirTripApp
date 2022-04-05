package com.example.izmirtripapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.izmirtripapp.Model.Routes;

import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.siralama> {

    public SortAdapter(Context mcontext, List<Routes> siralamalist) {
        this.mcontext = mcontext;
        this.siralamalist = siralamalist;
    }

    private Context mcontext;
    private List<Routes> siralamalist;

    @NonNull
    @Override
    public siralama onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sortrecylerview, parent, false);
        return new siralama(view);
    }

    @Override
    public void onBindViewHolder(@NonNull siralama holder, int position) {
        Routes bilgi = siralamalist.get(position);

        holder.address.setText(bilgi.getAddress());
        holder.title.setText(bilgi.getAddressTitle());
        holder.time.setText(bilgi.getTime());

        if (position == siralamalist.size() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return siralamalist.size();
    }

    public class siralama extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView address;
        private TextView time;
        View line;

        public siralama(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            line = itemView.findViewById(R.id.line);
            time = itemView.findViewById(R.id.time);
        }
    }
}
