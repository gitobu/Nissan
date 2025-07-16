package com.gitobu.nissan;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LocationActivity extends AppCompatActivity {
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initVehicleButton();
        initStaffButton();
        initLocationButton();
        initTextChangedEvents();
        initToggleButton();
        currentLocation = new Location();
        Bundle extras = getIntent().getExtras();
        /*if we have an extra we call the initVaccine method and pass the id associated
         * with the key vaccineKey*/
        if (extras != null) {
            initLocation(extras.getInt("pharmacyKey"));
            /*Think about it as open the extras and get the id mapped to the key vaccineKey
             * and pass that id to initVaccine function*/
        } else {
            currentLocation = new Location();
        }
        setForEditing(false);
        initSaveButton();
    }

    private void initLocation(int id) { /*The id here is the vaccineId*/
        VehicleDataSource dataSource = new VehicleDataSource(LocationActivity.this);
        try {
            dataSource.open();
            /*Get vaccine data and enter it into currentVaccine*/
            currentLocation = dataSource.getSpecificLocation(id);
            dataSource.close();
        } catch (SQLException e) {
            Toast.makeText(this, "Load Location failed", Toast.LENGTH_LONG).show();
        }
        /*Create a reference to the widgets*/
        EditText editLocationName = findViewById(R.id.editTextLocationName);
        EditText editStreetAddress = findViewById(R.id.editTextStreetAddress);
        EditText editCity = findViewById(R.id.editTextCity);
        EditText editState = findViewById(R.id.editTextState);
        EditText editZipCode = findViewById(R.id.editTextZipCode);
        /*Widgets are set to display the values of the selected location*/
        editLocationName.setText(currentLocation.getLocationName());
        editStreetAddress.setText(currentLocation.getStreetaddress());
        editCity.setText(currentLocation.getCity());
        editState.setText(currentLocation.getState());
        editZipCode.setText(currentLocation.getZipcCode());

    }

    private void initTextChangedEvents() {
        /*A reference to the vaccine name EditText is assigned to the variable etVaccineName
         * This is declared as a final because it is used inside the event code*/
        final EditText etPharmacyName = findViewById(R.id.editTextLocationName);
        /*A text changed listener is added by creating a TextWatcher object
         * The Textwatcher requires three methods as below*/
        etPharmacyName.addTextChangedListener(new TextWatcher() {
            @Override
            /*The beforeTextChanged is executed when a user presses down on a key to enter
             * the EditText but before the value in the EditText is actually changed*/
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            /*The onTextChanged method is executed after each and every character change in
             * the EditText*/
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            /*The afterTextChanged method is called after a user completes editing the data
             * and leaves the EditText*/
            public void afterTextChanged(Editable s) {
                currentLocation.setLocationName(etPharmacyName.getText().toString());
            }
        });
        final EditText etStreetAddress = findViewById(R.id.editTextStreetAddress);
        etStreetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentLocation.setStreetaddress(etStreetAddress.getText().toString());
            }
        });
        final EditText etCity = findViewById(R.id.editTextCity);
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentLocation.setCity(etCity.getText().toString());
            }
        });
        final EditText etState = findViewById(R.id.editTextState);
        etState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentLocation.setState(etState.getText().toString());
            }
        });
        final EditText etZipCode = findViewById(R.id.editTextZipCode);
        etZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentLocation.setZipcCode(etZipCode.getText().toString());
            }
        });

    }

    private void setForEditing(boolean enabled) {
        EditText editLocationName = findViewById(R.id.editTextLocationName);
        editLocationName.setEnabled(enabled);
        EditText editStreetAddress = findViewById(R.id.editTextStreetAddress);
        editStreetAddress.setEnabled(enabled);
        EditText editCity = findViewById(R.id.editTextCity);
        editCity.setEnabled(enabled);
        EditText editState = findViewById(R.id.editTextState);
        editState.setEnabled(enabled);
        EditText editZipCode = findViewById(R.id.editTextZipCode);
        editZipCode.setEnabled(enabled);

        if (enabled) {
            editLocationName.requestFocus();
        } else {
            ScrollView s = findViewById(R.id.scrollViewControls);
            s.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    private void initToggleButton() {
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButtonEdit);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setForEditing(toggleButton.isChecked());
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editPharmacyName = findViewById(R.id.editTextLocationName);
        imm.hideSoftInputFromWindow(editPharmacyName.getWindowToken(), 0);
        EditText editStreetAddress = findViewById(R.id.editTextStreetAddress);
        imm.hideSoftInputFromWindow(editStreetAddress.getWindowToken(), 0);
        EditText editCity = findViewById(R.id.editTextCity);
        imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);
        EditText editState = findViewById(R.id.editTextCity);
        imm.hideSoftInputFromWindow(editState.getWindowToken(), 0);
        EditText editZipCode = findViewById(R.id.editTextZipCode);
        imm.hideSoftInputFromWindow(editZipCode.getWindowToken(), 0);
    }

    private void initSaveButton() {
        // declare the widget to use the listener
        Button saveButton = findViewById(R.id.buttonSave);
        // set a listener
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyboard();

                boolean wasSuccessful; //variable captures the return values of VaccineDataSource methods
                // and determines the operations that should be performed based on success or failure of the methods
                VehicleDataSource dataSource = new VehicleDataSource(LocationActivity.this); //declare datasourse object
                try {
                    dataSource.open(); //open the database

                    if (currentLocation.getLocationID() == -1) {
                        //if database opens and is new vaccine save it
                        wasSuccessful = dataSource.insertLocation(currentLocation);
                        if (wasSuccessful) {
                            int newId = dataSource.getLastLocationID();
                            currentLocation.setLocationID(newId);
                            setForEditing(false);
                        }
                    } else {
                        //if database opens and is existing vaccine update it
                        wasSuccessful = dataSource.updateLocation(currentLocation);
                    }
                    //close the database
                    dataSource.close();
                } catch (Exception e) {
                    wasSuccessful = false;
                }

            }
        });
    }

    private void initVehicleButton() {
        Button buttonVaccineList = findViewById(R.id.btnVehicle);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationActivity.this, VehicleListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initLocationButton() {
        Button buttonVaccineList = findViewById(R.id.btnLocation);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationActivity.this, LocationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initStaffButton() {
        Button buttonVaccineList = findViewById(R.id.btnStaff);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(LocationActivity.this, StaffActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}