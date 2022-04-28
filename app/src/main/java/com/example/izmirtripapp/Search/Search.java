package com.example.izmirtripapp.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.izmirtripapp.Events.Events;
import com.example.izmirtripapp.HomePage;
import com.example.izmirtripapp.Likes;
import com.example.izmirtripapp.Museums.Museum;
import com.example.izmirtripapp.Others.Others;
import com.example.izmirtripapp.Profile;
import com.example.izmirtripapp.R;
import com.example.izmirtripapp.Cafes.RestaurantsandCafes;
import com.example.izmirtripapp.Route;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<Place> list;
    RecyclerView recyclerView;
    SearchView searchView;

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    Area area;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.search);

        ref = FirebaseDatabase.getInstance().getReference().child("Place");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.searchView);

        area = new Area();
        listView = (ListView) findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Area");
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.area_info,R.id.areaInfo,arrayList);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    area= ds.getValue(Area.class);
                    arrayList.add(area.getName().toString());
                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0){
                            Intent intent = new Intent(view.getContext(), Museum.class);
                            startActivity(intent);
                        }
                        if (position == 1){
                            Intent intent = new Intent(view.getContext(), RestaurantsandCafes.class);
                            startActivity(intent);
                        }
                        if (position == 2){
                            Intent intent = new Intent(view.getContext(), Events.class);
                            startActivity(intent);
                        }
                        if (position == 3){
                            Intent intent = new Intent(view.getContext(), Others.class);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        if (ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            list.add(ds.getValue(Place.class));
                        }
                        AdapterClass adapterClass = new AdapterClass(list);
                        recyclerView.setAdapter(adapterClass);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Search.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str){
        ArrayList<Place> myList =new ArrayList<>();
        for (Place object : list){
            if (object.getPlaceDisc().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        AdapterClass adapterClass = new AdapterClass(myList);
        recyclerView.setAdapter(adapterClass);

    }


}