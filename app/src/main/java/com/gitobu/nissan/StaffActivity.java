package com.gitobu.nissan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        initVehicleButton();
        initStaffButton();
        initLocationButton();
    }
    private void initVehicleButton() {
        Button buttonVaccineList = findViewById(R.id.btnVehicle);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(StaffActivity.this, VehicleListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initLocationButton() {
        Button buttonVaccineList = findViewById(R.id.btnLocation);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(StaffActivity.this, LocationListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initStaffButton() {
        Button buttonVaccineList = findViewById(R.id.btnStaff);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(StaffActivity.this, StaffActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}