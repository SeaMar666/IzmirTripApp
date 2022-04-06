package com.example.izmirtripapp;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.izmirtripapp.Model.Routes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddRouteActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {

    Geocoder geocoder;
    List<Address> addresses;
    FusedLocationProviderClient fusedLocationClient;
    private GoogleMap googleMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    TextView selected_location_text;
    ProgressBar find_user_location_progress;
    Button button_use_address, save_btn;
    final Handler handler = new Handler();
    LinearLayout save_layout, indicator;
    ConstraintLayout address_result_layout;
    View mapFragmentView;
    EditText address_edit, address_desc_edit;
    double userLastLocationLat = 0;
    double userLastLocationLon = 0;
    int statusCode = 0;
    String cityString = "",
            stateString = "",
            latString = "",
            lonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        selected_location_text = findViewById(R.id.selected_location_text);
        find_user_location_progress = findViewById(R.id.find_user_location_progress);
        save_layout = findViewById(R.id.save_layout);
        address_result_layout = findViewById(R.id.address_result_layout);
        button_use_address = findViewById(R.id.button_use_address);
        save_btn = findViewById(R.id.save_btn);
        mapFragmentView = findViewById(R.id.map);
        indicator = findViewById(R.id.indicator);
        address_edit = findViewById(R.id.address);

        address_desc_edit = findViewById(R.id.address_desc);

        fusedLocationClient = new FusedLocationProviderClient(this);

        button_use_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double lat = googleMap.getCameraPosition().target.latitude;
                double lon = googleMap.getCameraPosition().target.longitude;

                float[] results = new float[1];
                Location.distanceBetween(userLastLocationLat, userLastLocationLon,
                        lat, lon, results);

                getAddressString();
                animateSaveLayout(true);


            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (address_edit.getText().length() < 1) {
                    address_edit.setError(getResources().getString(R.string.cannot_be_blank));
                } else if (address_desc_edit.getText().length() < 1) {
                    address_desc_edit.setError(getResources().getString(R.string.cannot_be_blank));
                } else {


                    String Address = address_edit.getText().toString();
                    String AddressTitle = address_desc_edit.getText().toString();
                    String Latitude = latString;
                    String Longitude = lonString;

                    String username = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getString("username", "default");

                    connectFirebase(Address, AddressTitle, Latitude, Longitude, username);

                }

            }
        });


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.add_address));
//        actionBar.setDisplayHomeAsUpEnabled(true);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton bGoToMyLocation = findViewById(R.id.bGoToMyLocation);
        bGoToMyLocation.setOnClickListener(this);
    }

    private void animateSaveLayout(Boolean isStart) {
        if (isStart) {
            save_layout.setVisibility(View.VISIBLE);
            save_layout.animate()
                    .translationY(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                        }
                    });

            mapFragmentView.animate()
                    .translationY(-330)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                        }
                    });

            indicator.animate()
                    .translationY(-330)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                        }
                    });


            address_result_layout.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            address_result_layout.setVisibility(View.INVISIBLE);


                        }
                    });

            googleMap.getUiSettings().setAllGesturesEnabled(false);
        } else {
            googleMap.getUiSettings().setAllGesturesEnabled(true);

            final float distance = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, save_layout.getHeight(),
                    getResources().getDisplayMetrics()
            );

            save_layout.animate()
                    .translationY(distance)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            save_layout.setVisibility(View.GONE);
                        }
                    });

            mapFragmentView.animate()
                    .translationY(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                        }
                    });

            indicator.animate()
                    .translationY(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                        }
                    });


            address_result_layout.setVisibility(View.VISIBLE);
            address_result_layout.animate()
                    .alpha(1f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {


                        }
                    });
        }
    }

    private void getAddressString() {
        try {
            double lat = googleMap.getCameraPosition().target.latitude;
            double lon = googleMap.getCameraPosition().target.longitude;
            latString = String.valueOf(lat);
            lonString = String.valueOf(lon);

            LatLng latLng = new LatLng(lat, lon);

            String myAddress = "";
            geocoder = new Geocoder(AddRouteActivity.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                String getSubLocality = addresses.get(0).getSubLocality();
                String getThoroughfare = addresses.get(0).getThoroughfare();
                String getSubThoroughfare = addresses.get(0).getSubThoroughfare();
                String fullAddress = addresses.get(0).getAddressLine(0);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String getSubAdminArea = addresses.get(0).getSubAdminArea();
                String getPremises = addresses.get(0).getPremises();

                cityString = state;
                stateString = getSubAdminArea;


                StringBuilder stringBuilder = new StringBuilder();
                if (getSubLocality != null) {
                    stringBuilder.append(getSubLocality);
                }
                if (getThoroughfare != null) {
                    if (getSubLocality != null) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(getThoroughfare);
                }

                if (getSubThoroughfare != null) {
                    stringBuilder.append(" No:").append(getSubThoroughfare);
                } else {
                    String s = fullAddress.toLowerCase();
                    String s2 = fullAddress.toLowerCase();
                    if (s2.contains("no:")) {
                        if (getSubLocality != null) {
                            stringBuilder.append(", ");
                        }
                        s = s.substring(s2.indexOf("no:") + 3);
                        s = s.substring(0, s.indexOf(","));
                        stringBuilder.append(" No:").append(s);
                    }

                }

                address_edit.setText(stringBuilder.toString());


            } catch (IOException e) {
                e.printStackTrace();

            }

        } catch (Exception ex) {

        }
    }

    public void setPaddingForGoogleLogo(GoogleMap map, Context context) {

        map.setPadding(0, 0, 0, context.getResources().getDimensionPixelSize(R.dimen.maps_bottom_padding));
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + 500);
        googlePlacesUrl.append("&query=" + nearbyPlace);
        googlePlacesUrl.append("&language=" + "tr");
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyAcQljVoKubcTBBWyaJ59T3H21cOgiyYC4");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (save_layout.getVisibility() == View.VISIBLE) {

                    animateSaveLayout(false);

                } else {
                    finish();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        setPaddingForGoogleLogo(googleMap, AddRouteActivity.this);
        LatLng latLng = new LatLng(41.00820020669427, 28.98015458691306);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        enableMyLocation();
        googleMap.setOnMyLocationClickListener(this);
        googleMap.setOnMyLocationButtonClickListener(this);
    }

    @Override
    public void onClick(View view) {
        goToMyLocation();
    }

    @Override
    public void onBackPressed() {
        if (save_layout.getVisibility() == View.VISIBLE) {

            animateSaveLayout(false);

        } else {
            super.onBackPressed();
        }

    }

    private boolean isLocationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {

            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            return false;

        } else {
            return true;
        }
    }

    private void goToMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {

            if (isLocationEnabled()) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        find_user_location_progress.setVisibility(View.VISIBLE);
                        selected_location_text.setText("");
                    }
                });
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {

                        try {
                            double lat = googleMap.getCameraPosition().target.latitude;
                            double lon = googleMap.getCameraPosition().target.longitude;

                            LatLng latLng = new LatLng(lat, lon);
                            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            String myAddress = "";
                            geocoder = new Geocoder(AddRouteActivity.this, Locale.getDefault());
                            try {
                                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                                String getSubLocality = addresses.get(0).getSubLocality();
                                String getThoroughfare = addresses.get(0).getThoroughfare();
                                String getSubThoroughfare = addresses.get(0).getSubThoroughfare();
                                String fullAddress = addresses.get(0).getAddressLine(0);
                                String address = addresses.get(0).getAddressLine(0);
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();
                                String getSubAdminArea = addresses.get(0).getSubAdminArea();
                                String getPremises = addresses.get(0).getPremises();


                                Log.e("Address", fullAddress + "--" + address + "--" + city + "--" + state + "--" + getSubAdminArea + "--" + getPremises);

                                StringBuilder stringBuilder = new StringBuilder();
                                if (getSubLocality != null) {
                                    stringBuilder.append(getSubLocality);
                                }
                                if (getThoroughfare != null) {
                                    if (getSubLocality != null) {
                                        stringBuilder.append(", ");
                                    }
                                    stringBuilder.append(getThoroughfare);
                                }

                                if (getSubThoroughfare != null) {
                                    stringBuilder.append(" No:").append(getSubThoroughfare);
                                } else {
                                    String s = fullAddress.toLowerCase();
                                    String s2 = fullAddress.toLowerCase();
                                    if (s2.contains("no:")) {
                                        if (getSubLocality != null) {
                                            stringBuilder.append(", ");
                                        }
                                        s = s.substring(s2.indexOf("no:") + 3);
                                        s = s.substring(0, s.indexOf(","));
                                        stringBuilder.append(" No:").append(s);
                                    }

                                }
                                myAddress = stringBuilder.toString();
                                if (myAddress.length() > 1) {
                                    find_user_location_progress.setVisibility(View.GONE);
                                    selected_location_text.setText(myAddress);
                                } else {
                                    selected_location_text.setText(AddRouteActivity.this.getResources().getString(R.string.cant_find_address));
                                    find_user_location_progress.setVisibility(View.GONE);
                                }


                            } catch (IOException e) {
                                selected_location_text.setText(AddRouteActivity.this.getResources().getString(R.string.cant_find_address));
                                find_user_location_progress.setVisibility(View.GONE);
                                e.printStackTrace();
                            }

                        } catch (Exception ex) {
                            selected_location_text.setText(AddRouteActivity.this.getResources().getString(R.string.cant_find_address));
                            find_user_location_progress.setVisibility(View.GONE);
                        }

                    }
                });

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {


                                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    userLastLocationLat = location.getLatitude();
                                    userLastLocationLon = location.getLongitude();
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));


                                    //   googleMap.addMarker(new MarkerOptions().position(latLng).title(myAddress));

                                }
                            }
                        });

            }

        }
    }

    // need for android 6 and above
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (googleMap != null) {
            googleMap.setMyLocationEnabled(true);
            goToMyLocation();
        }
    }

    // need for android 6 and above
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Log.d("", "onMyLocationClick() called with: location = [" + location + "]");
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Log.d("", "onMyLocationButtonClick() called");
        goToMyLocation();
        return true;
    }


    public void connectFirebase(String address, String addressTitle, String lat, String longing, String username) {
        ProgressDialog dialog = ProgressDialog.show(AddRouteActivity.this, "Loading", "Please wait...", true);
        dialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String Timee = sdf2.format(new Date());

        String date = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("selectedDate", currentDateandTime);


        Routes route = new Routes(address, addressTitle, lat, longing, username, date, Timee);

        myRef.child("Routes").push().setValue(route);

        Toast.makeText(AddRouteActivity.this, "Adres Kaydedildi", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        finish();

    }

}