package com.example.izmirtripapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.izmirtripapp.Search.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button logoutBtn;

    DatabaseReference reference;
    FirebaseDatabase database;
    private static final String USERS = "users";
    String name, email, phoneNo, username;

    ImageView userImage;
    TextView userName, userMail, userPhone;
    public Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView set, image;
    Button browse, upload;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        set = findViewById(R.id.settings);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        Intent intent2 = getIntent();
        email = intent2.getStringExtra("email");

        Intent intent3 = getIntent();
        phoneNo = intent3.getStringExtra("phoneNo");

        Intent intent4 = getIntent();
        username = intent4.getStringExtra("username");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        userImage = findViewById(R.id.userImage);
        browse = findViewById(R.id.select);
        upload = findViewById(R.id.upload);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.route:
                        startActivity(new Intent(getApplicationContext(), Route.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.likes:
                        startActivity(new Intent(getApplicationContext(), Likes.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

        logoutBtn = findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, LoginActivity.class));
            }
        });

        userMail = findViewById(R.id.userMail);
        userPhone = findViewById(R.id.userPhone);
        userName = findViewById(R.id.userName);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("name").getValue().equals(name));
                    userName.setText(ds.child("name").getValue(String.class));

                    if(ds.child("email").getValue().equals(email));
                    userMail.setText(ds.child("email").getValue(String.class));

                    if(ds.child("phoneNo").getValue().equals(phoneNo));
                    userPhone.setText(ds.child("phoneNo").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}




