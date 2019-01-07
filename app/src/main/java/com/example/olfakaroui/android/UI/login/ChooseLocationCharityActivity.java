package com.example.olfakaroui.android.UI.login;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.UI.MainActivity;
import com.example.olfakaroui.android.UI.interfaces_for_charity.AddEventLocationActivity;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.UserService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ChooseLocationCharityActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    Button next;
    double longi= 36.8665;
    double alti = 10.1647;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location_charity);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionManager sessionManager = new SessionManager(ChooseLocationCharityActivity.this);
                sessionManager.getLogin(user);
                UserService.getInstance().addCharityLocation(user.getId(),alti, longi, new UserService.UserServiceLocCallBack() {
                    @Override
                    public void onResponse(String event1) {

                        Intent intent = new Intent(ChooseLocationCharityActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(String error) {
                    }

                });



            }
        });


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(36.8665, 10.1647);
        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(this);
    }



    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Association's place"));
        alti = latLng.latitude;
        longi = latLng.longitude;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
