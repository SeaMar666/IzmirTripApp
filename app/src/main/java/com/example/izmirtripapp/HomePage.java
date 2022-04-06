package com.example.izmirtripapp;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.izmirtripapp.Model.PlaceData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;

    FirebaseDatabase database;
    DatabaseReference ref;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerAdapter<PlaceData,ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<PlaceData> options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView = findViewById(R.id.popularPlacesRecView);
        recyclerView.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Data");

        showData();

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.route:
                        startActivity(new Intent(getApplicationContext(),Route.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.likes:
                        startActivity(new Intent(getApplicationContext(),Likes.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void showData(){

        options = new FirebaseRecyclerOptions.Builder<PlaceData>().setQuery(ref,PlaceData.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PlaceData, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull PlaceData placeData) {

                viewHolder.setDetails(getApplicationContext(),placeData.getTitle(), placeData.getImage());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_list,parent,false);

                ViewHolder viewHolder = new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(HomePage.this, "Hi", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongListener(View view, int position) {
                        Toast.makeText(HomePage.this, "Hiiii", Toast.LENGTH_SHORT).show();

                    }
                });
                return viewHolder;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    protected void  onStart(){

        super.onStart();

        if(firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
            Log.i("BAŞARILı","aaa");
        }

    }
}
