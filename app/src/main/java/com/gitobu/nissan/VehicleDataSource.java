package com.gitobu.nissan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class VehicleDataSource {
    private SQLiteDatabase database;
    private VehicleDBHelper dbHelper;

    public VehicleDataSource (Context context) {
        dbHelper = new VehicleDBHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public boolean insertVehicleMake (Vehicle v) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("make", v.getMake());
            initialValues.put("model", v.getModel());
            initialValues.put("category", v.getCategory());

            didSucceed = database.insert("vehicle", null, initialValues) > 0;
        } catch (Exception e) {
            //Do nothing - will return false if there is an exception
        }
        return didSucceed;
    }
    public boolean updateUpdateMake (Vehicle v){
       boolean didSucceed = false;
       try {
           Long rowId = (long) v.getVehicleID();
           ContentValues updateValues = new ContentValues();

           updateValues.put("make", v.getMake());
           updateValues.put("model" ,v.getCategory());
           updateValues.put("category" ,v.getCategory());
       } catch (Exception e) {
           //Do nothing - will return false if there is an exception
       }
        return didSucceed;
    }
    public boolean insertLocation(Location c){
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("locationname", c.getLocationName());
            initialValues.put("streetaddress", c.getStreetaddress());
            initialValues.put("city", c.getCity());
            initialValues.put("state", c.getState());
            initialValues.put("zipcode", c.getZipcCode());

            didSucceed = database.insert("location", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing - will return false if there is no exception
        }
        return didSucceed;
    }

    public boolean updateLocation(Location c) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) c.getLocationID();

            ContentValues updateValues = new ContentValues();
            updateValues.put("locationname", c.getLocationName());
            updateValues.put("streetaddress", c.getStreetaddress());
            updateValues.put("city", c.getCity());
            updateValues.put("state", c.getState());
            updateValues.put("zipcode", c.getZipcCode());

            didSucceed = database.update("location", updateValues, "_id=" + rowId, null) > 0;


        }
        catch (Exception e) {

        }
        return didSucceed;
    }

    public int getLastLocationID(){
        int lastId;
        try {
            String query = "Select MAX(_ID) from location";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }
    public int getLastVehicleId(){ //this method gets the id to support creating a new one
        int lastVehicleId;
        try {
            String query = "Select MAX(_Id) from Vehicle";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastVehicleId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastVehicleId = -1;
        }
        return lastVehicleId;
    }
    public ArrayList<String> getVehicleMake(){
        ArrayList<String> vehicleMakes = new ArrayList<>();
        try {
            String query = "Select make from vehicle";
            Cursor cursor = database.rawQuery(query, null);
            //The cursor object holds the results of the query
            cursor.moveToFirst();
            //A loop through the query results
            while (!cursor.isAfterLast()){
                vehicleMakes.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            vehicleMakes = new ArrayList<String>();
        }

        return vehicleMakes;
    }
    public ArrayList<Vehicle> getVehicles(){
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        try {
            String query = "SELECT * from vehicle";
            Cursor cursor = database.rawQuery(query, null);
            Vehicle newVehicle;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                newVehicle = new Vehicle();
                /*The fields are referenced by their location in the table*/
                newVehicle.setVehicleID(cursor.getInt(0));
                newVehicle.setMake(cursor.getString(1));
                newVehicle.setModel(cursor.getString(2));
                newVehicle.setCategory(cursor.getString(3));
                vehicles.add(newVehicle);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            vehicles = new ArrayList<Vehicle>();
        }
        return vehicles;
    }

    public Vehicle getSpecificVehicle(int vehicleId){
        Vehicle vehicle = new Vehicle();
        String query = "SELECT * FROM Vehicle where _id =" + vehicleId;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            vehicle.setVehicleID(cursor.getInt(0));
            vehicle.setMake(cursor.getString(1));
            vehicle.setModel(cursor.getString(2));
            vehicle.setCategory(cursor.getString(3));
            cursor.close();
        }
        return vehicle;
    }
    public boolean deleteVehicle(int vehicleId){
        boolean didDelete = false;
        try {
            didDelete = database.delete("vehicle", "_id=" + vehicleId, null) > 0;
        } catch (Exception e) {
            //Do nothing - return value already set to false
        }
        return didDelete;
    }
    public ArrayList<Location> getLocations() {
        ArrayList<Location>  locations = new ArrayList<Location>();
        try {
            String query = "SELECT * from location";
            Cursor cursor = database.rawQuery(query, null);
            Location newLocation;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                newLocation = new Location();
                newLocation.setLocationID(cursor.getInt(0));
                newLocation.setLocationName(cursor.getString(1));
                newLocation.setStreetaddress(cursor.getString(2));
                newLocation.setCity(cursor.getString(3));
                newLocation.setState(cursor.getString(4));
                newLocation.setZipcCode(cursor.getString(5));
                locations.add(newLocation);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception exception){
            locations = new ArrayList<Location>();
        }
        return locations;
    }
    public Location getSpecificLocation(int locationID) { /*The id for the specific record*/
        Location location = new Location();
        String query = "SELECT * FROM location WHERE _id =" + locationID; /*.Query has where clause*/
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            location.setLocationID(cursor.getInt(0));
            location.setLocationName(cursor.getString(1));
            location.setStreetaddress(cursor.getString(2));
            location.setCity(cursor.getString(3));
            location.setState(cursor.getString(4));
            location.setZipcCode(cursor.getString(5));
            cursor.close();
        }
        return location;
    }
    public boolean deleteLocation(int locationID){
        boolean didDelete = false;
        try {
            /*A delete method requires the table to delete from and the where clause*/
            didDelete = database.delete("location", "_id" + locationID, null) > 0;
        } catch (Exception e) {
            //Do nothing - return value is alread set to false
        }
        return didDelete;
    }
}
