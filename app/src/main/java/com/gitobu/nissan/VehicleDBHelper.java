package com.gitobu.nissan;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class VehicleDBHelper extends SQLiteOpenHelper { // a subclass of SQLiteOpenHelper
    private static final String DATABASE_NAME = "vehicle.db"; //a static variable to name the db
    private static final int DATABASE_VERSION = 1; //a variable to hold the version number

    //database sql statement
    private static final String CREATE_TABLE_MAKE = // a string variable for query
            "create table vehicle (_id integer not null primary key autoincrement, "
                + "make text not null, "
                + "model text not null, "
                + "category text not null);";

    //Database creation sql statement
    private static final String CREATE_TABLE_LOCATION = //a string variable for query
            "create table location (_id integer primary key autoincrement, "
                    + "locationname text not null, "
                    + "streetaddress text, "
                    + "city text,"
                    + "state text,"
                    + "zipcode int);";

    public VehicleDBHelper(Context context) { // a constructor method to call the superclass constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MAKE);
        sqLiteDatabase.execSQL(CREATE_TABLE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) { //method to upgrade version
        Log.w(VehicleDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy old data");
                onCreate(sqLiteDatabase);
    }
}
