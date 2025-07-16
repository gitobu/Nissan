package com.gitobu.nissan;

import android.content.Context;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter {
    private ArrayList<Vehicle> vehicleData;
    /*Declare a private variable to hold the OnClickListener object*/
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public VehicleAdapter(ArrayList<Vehicle> arrayList, Context context){
        vehicleData = arrayList;
        parentContext = context;
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewVehicle;
        public TextView textViewModel;
        public Button deleteButton;

        public VehicleViewHolder(@NonNull View itemView){
            super(itemView);
            textViewVehicle = itemView.findViewById(R.id.textVehicleMake);
            textViewModel = itemView.findViewById(R.id.textModel);
            deleteButton = itemView.findViewById(R.id.buttonDeleteVehicle);
            itemView.setTag(this); /*Set a tag so we can identify which item was clicked*/
            itemView.setOnClickListener(mOnItemClickListener); /*Set the Viewholder's
            OnClickListener to the listener passed from the activity*/
        }
        public TextView getVehicleTextView(){
            return textViewVehicle;
        }
        public TextView getModelTextView(){
            return textViewModel;
        }
        public Button getDeleteButton(){
            return deleteButton;
        }
    }
    //new
    public void setOnItemClickListener (View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // a reference to the recycler view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new VehicleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VehicleViewHolder vvh = (VehicleViewHolder) holder;
        vvh.getVehicleTextView().setText(vehicleData.get(position).getMake());
        vvh.getModelTextView().setText(vehicleData.get(position).getModel());
            if (isDeleting) {
                vvh.getDeleteButton().setVisibility(View.VISIBLE);
                vvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteItem(position);
                    }
                });
            } else {
                vvh.getDeleteButton().setVisibility(View.INVISIBLE);
            }
    }
    public void setDelete(boolean b) {
        isDeleting = b;
    }
    public void deleteItem(int position) {
        Vehicle vehicle = vehicleData.get(position);
        VehicleDataSource dataSource = new VehicleDataSource(parentContext);
        try {
            dataSource.open();
            boolean didDelete = dataSource.deleteVehicle(vehicle.getVehicleID());
            dataSource.close();
            if (didDelete) {
                vehicleData.remove(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(parentContext, "Delete failed!", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public int getItemCount() {
        return vehicleData.size();
    }
}
