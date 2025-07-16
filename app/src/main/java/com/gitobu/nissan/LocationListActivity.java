package com.gitobu.nissan;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.database.SQLException;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.Switch;
        import android.widget.Toast;

        import java.util.ArrayList;

public class LocationListActivity extends AppCompatActivity {
    LocationAdapter locationAdapter;
    RecyclerView recyclerView;
    ArrayList<Location> locations;

    /*Instantiate a new View.onClickListener*/
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder)view.getTag();
            /*use getTag to know the ViewHolder that sent the tag - who tagged me?*/
            int position = viewHolder.getAdapterPosition(); /*What is the position of the tagging list?*/
            /*With the position known we go to the the activity - here VehicleListActivity*/
            /*At this time we have the position but will do nothing with it*/
            int locationId = locations.get(position).getLocationID();
            Intent intent = new Intent(LocationListActivity.this, LocationActivity.class);
            intent.putExtra("locationKey", locationId);
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        initVehicleButton();
        initStaffButton();
        initLocationButton();
        initAddLocationButton();
        initDeleteSwitch();
        VehicleDataSource vehicleDataSource = new VehicleDataSource(this);
        // ArrayList<Vehicle> makes;

        try {
            vehicleDataSource.open();
            locations = vehicleDataSource.getLocations();
            vehicleDataSource.close();
            recyclerView  = findViewById(R.id.rvLocations);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            LocationAdapter locationAdapter = new LocationAdapter(locations, this);
            locationAdapter.setOnItemClickListener(onItemClickListener);
            recyclerView.setAdapter(locationAdapter);
        } catch (SQLException e) {
            Toast.makeText(this, "Error retrieving makes", Toast.LENGTH_LONG).show();
        }
    }
    private void initAddLocationButton() {
        Button newVehicle = findViewById(R.id.buttonAddLocation);
        newVehicle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LocationListActivity.this, VehicleActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initDeleteSwitch(){
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Boolean status = compoundButton.isChecked();
                locationAdapter.setDelete(status);
                locationAdapter.notifyDataSetChanged();
            }
        });
    }
    private void initVehicleButton() {
        Button buttonVaccineList = findViewById(R.id.btnVehicle);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationListActivity.this, VehicleListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initLocationButton() {
        Button buttonVaccineList = findViewById(R.id.btnLocation);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationListActivity.this, LocationListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initStaffButton() {
        Button buttonVaccineList = findViewById(R.id.btnStaff);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationListActivity.this, LocationMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}