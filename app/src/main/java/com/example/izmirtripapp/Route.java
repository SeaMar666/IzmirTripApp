package com.example.izmirtripapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.izmirtripapp.Model.Routes;
import com.example.izmirtripapp.Search.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Route extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FloatingActionButton addRoute;
    private RecyclerView recyclerView;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private SortAdapter adapter;
    private ArrayList<Routes> RouteList;
    Button datePickerButton;

    int year = 0;
    int month = 0;
    int dayOfMonth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        addRoute = findViewById(R.id.addRoute);
        datePickerButton = findViewById(R.id.datePicker);
        bottomNavigationView.setSelectedItemId(R.id.route);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        datePickerButton.setText(currentDateandTime);


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putString("selectedDate", currentDateandTime).apply();

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(Route.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int yearC, int monthC, int dayC) {

                                calendar.set(yearC, monthC, dayC);

                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                String strDate = format.format(calendar.getTime());

                                datePickerButton.setText(strDate);
                                sort(strDate);

                                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                        .putString("selectedDate", strDate).apply();

                                year = yearC;
                                month = monthC;
                                dayOfMonth = dayC;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Route.this, AddRouteActivity.class));

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.route:
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

        recyclerView = findViewById(R.id.recylerview);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Routes");

        sort(currentDateandTime);
    }

    public void sort(String currentDateandTime) {
        RouteList = new ArrayList<>();


        String username = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("username", "default");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("Routes").orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                RouteList = new ArrayList<>();

                for (DataSnapshot s : snapshot.getChildren()) {
                    Routes bilgi = s.getValue(Routes.class);
                    if (bilgi.getDate().equals(currentDateandTime))
                        RouteList.add(bilgi);

                }


                adapter = new SortAdapter(Route.this, RouteList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Route.this));
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}