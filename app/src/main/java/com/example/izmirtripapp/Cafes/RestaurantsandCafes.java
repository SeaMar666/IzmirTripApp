package com.example.izmirtripapp.Cafes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.izmirtripapp.HomePage;
import com.example.izmirtripapp.Likes;
import com.example.izmirtripapp.Profile;
import com.example.izmirtripapp.R;
import com.example.izmirtripapp.Route;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class RestaurantsandCafes extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    RecyclerView recview;
    mycafeadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantsand_cafes);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.search);

        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<cafes> options = new FirebaseRecyclerOptions.Builder<cafes>().setQuery(FirebaseDatabase.getInstance().getReference().child("Cafes"), cafes.class).build();
        adapter = new mycafeadapter(options);
        recview.setAdapter(adapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.route:
                        startActivity(new Intent(getApplicationContext(), Route.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.likes:
                        startActivity(new Intent(getApplicationContext(), Likes.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}