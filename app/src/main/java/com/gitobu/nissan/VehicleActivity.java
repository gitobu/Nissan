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

public class VehicleActivity extends AppCompatActivity {
    private Vehicle currentVehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        //currentVehicle = new Vehicle();
        initVehicleButton();
        initStaffButton();
        initLocationButton();
        initTextChangedEvents();
        initToggleButton();
        initSaveButton();
        Bundle extras = getIntent().getExtras();
        /*if we have an extra we call the initVaccine method and pass the id associated
         * with the key vaccineKey*/
        if (extras != null) {
            initVehicle(extras.getInt("vehicleKey"));
            /*Think about is as open the extras and get the id mapped to the key vaccineKey
             * and pass that id to initVehicle function*/
        } else {
            currentVehicle = new Vehicle();
        }
        setForEditing(false);
    }
    private void initVehicle(int id){
        VehicleDataSource vehicleDataSource = new VehicleDataSource(VehicleActivity.this);
        try{
            vehicleDataSource.open();
            currentVehicle = vehicleDataSource.getSpecificVehicle(id);
            vehicleDataSource.close();
        } catch (SQLException e) {
            Toast.makeText(this,"Load vehicle failed", Toast.LENGTH_LONG).show();
        }
        EditText editMake = findViewById(R.id.editTextMake);
        EditText editModel = findViewById(R.id.editTextModel);
        EditText editCategory = findViewById(R.id.editTextCategory);

        editMake.setText(currentVehicle.getMake());
        editModel.setText(currentVehicle.getModel());
        editCategory.setText(currentVehicle.getCategory());
    }
    private void initTextChangedEvents(){
        /*A reference to the vehicle make EditText is assigned to the variable etMake
         * This is declared as a final because it is used inside the event code*/
        final EditText etMake = findViewById(R.id.editTextMake);
        /*A text changed listener is added by creating a TextWatcher object
         * The Textwatcher requires three methods as below*/
        etMake.addTextChangedListener(new TextWatcher() {
            @Override
            /*The beforeTextChanged is executed when a user presses down on a key to enter
             * the EditText but before the value in the EditText is actually changed*/
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            /*The onTextChanged method is executed after each and every character change in
             * the EditText*/
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            /*The afterTextChanged method is called after a user completes editing the data
             * and leaves the EditText*/
            public void afterTextChanged(Editable editable) {
                currentVehicle.setMake(etMake.getText().toString());
            }
        });

        final EditText etModel = findViewById(R.id.editTextModel);
        etModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentVehicle.setModel(etModel.getText().toString());
            }
        });

        final EditText etCategory = findViewById(R.id.editTextCategory);
        etCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentVehicle.setCategory(etCategory.getText().toString());

            }
        });
    }
    private void setForEditing(boolean enabled) {
        EditText editMake = findViewById(R.id.editTextMake);
        editMake.setEnabled(enabled);
        EditText editModel = findViewById(R.id.editTextModel);
        editModel.setEnabled(enabled);
        EditText editCategory = findViewById(R.id.editTextCategory);
        editCategory.setEnabled(enabled);

        if (enabled) {
            editMake.requestFocus();
        } else {
            ScrollView s = findViewById(R.id.scrollView);
            s.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    private void initToggleButton() {
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.tbEdit);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setForEditing(toggleButton.isChecked());
                setForEditing(true);
            }
        });
    }
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editMake = findViewById(R.id.editTextMake);
        imm.hideSoftInputFromWindow(editMake.getWindowToken(), 0);
        EditText editCategory = findViewById(R.id.editTextModel);
        imm.hideSoftInputFromWindow(editCategory.getWindowToken(), 0);
    }
    private void initSaveButton(){
        // declare the widget to use the listener
        Button saveButton = findViewById(R.id.btnSave);
        // set a listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                boolean wasSuccessful;//variable captures the return values of VehicleDataSource methods
                // and determines the operations that should be performed based on success or failure of the methods
                VehicleDataSource dataSource = new VehicleDataSource(VehicleActivity.this);
                try {
                    dataSource.open(); //open the database
                    if (currentVehicle.getVehicleID() == -1) {
                        //if database opens and is new vehicle save it
                        // if -1 we will insert data
                        wasSuccessful = dataSource.insertVehicleMake(currentVehicle);
                        //get the id we will use
                        int newId = dataSource.getLastVehicleId();
                        currentVehicle.setVehicleID(newId);
                    } else {
                        //if database opens and is existing vehicle update it
                        wasSuccessful = dataSource.updateUpdateMake(currentVehicle);
                    }
                    //close the database
                    dataSource.close();
                } catch (SQLException e) {
                    wasSuccessful = false;
                }
                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.tbEdit);
                    editToggle.toggle();
                    Bundle extras = getIntent().getExtras();
                    if(extras != null) {
                        initVehicle(extras.getInt("vehicleID"));
                    } else {
                        currentVehicle = new Vehicle();
                    }
                    setForEditing(false);
                }
            }
        });
    }

    private void initVehicleButton() {
        Button buttonVaccineList = findViewById(R.id.btnVehicle);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(VehicleActivity.this, VehicleListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initLocationButton() {
        Button buttonVaccineList = findViewById(R.id.btnLocation);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(VehicleActivity.this, LocationListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initStaffButton() {
        Button buttonVaccineList = findViewById(R.id.btnStaff);
        buttonVaccineList.setOnClickListener(v -> {
            Intent intent = new Intent(VehicleActivity.this, StaffActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}