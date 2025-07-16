package com.gitobu.nissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class LocationMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
        initLocationButton();
        initVehicleButton();
        initMapButton();
        initLocationButton();
    }
    private void initGetLocationButton(){
        Button locationButton = findViewById(R.id.buttonAddLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editAddress = findViewById(R.id.editAddress);
                EditText editCity = findViewById(R.id.editCity);
                EditText editState = findViewById(R.id.editState);
                EditText editZipCode = findViewById(R.id.editZipCode);

                String address = editAddress.getText().toString() + ", " +
                        editCity.getText().toString() + ", " +
                        editState.getText().toString() + ", " +
                        editZipCode.getText().toString();

                List<Address> addresses = null;
                Geocoder geo = new Geocoder(LocationMapActivity.this);
                try {
                    addresses = geo.getFromLocationName(address, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TextView txtLatitude = findViewById(R.id.textLatitude);
                TextView txtLongitude = findViewById(R.id.textLongitude);

                txtLatitude.setText(String.valueOf(addresses.get(0).getLatitude()));
                txtLongitude.setText(String.valueOf(addresses.get(0).getLongitude()));
            }
        });
    }
    private void initVehicleButton() {
        Button buttonVaccineList = findViewById(R.id.btnVehicle);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationMapActivity.this, VehicleListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initLocationButton() {
        Button buttonVaccineList = findViewById(R.id.btnLocation);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationMapActivity.this, LocationListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initMapButton() {
        Button buttonVaccineList = findViewById(R.id.btnStaff);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationMapActivity.this, LocationMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}