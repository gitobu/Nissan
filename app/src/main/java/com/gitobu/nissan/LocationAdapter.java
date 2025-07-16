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

public class LocationAdapter extends RecyclerView.Adapter {
    private ArrayList<Location> locationData;
    /*Declare a private variable to hold the OnClickListener object*/
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public LocationAdapter(ArrayList<Location> arrayList, Context context){
        locationData = arrayList;
        parentContext = context;
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewLocation;
        public TextView textViewStreetAddress;
        public Button deleteButton;

        public LocationViewHolder(@NonNull View itemView){
            super(itemView);
            textViewLocation = itemView.findViewById(R.id.textVehicleMake);
            textViewStreetAddress = itemView.findViewById(R.id.textModel);
            deleteButton = itemView.findViewById(R.id.buttonDeleteVehicle);
            itemView.setTag(this); /*Set a tag so we can identify which item was clicked*/
            itemView.setOnClickListener(mOnItemClickListener); /*Set the Viewholder's
            OnClickListener to the listener passed from the activity*/
        }
        public TextView getLocationTextView(){
            return textViewLocation;
        }
        public TextView getStreetAddressTextView(){
            return textViewStreetAddress;
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
        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        LocationViewHolder locationViewHolder = (LocationViewHolder) holder;
        locationViewHolder.getLocationTextView().setText(locationData.get(position).getLocationName());
        locationViewHolder.getStreetAddressTextView().setText(locationData.get(position).getStreetaddress());
        if (isDeleting) {
            locationViewHolder.getDeleteButton().setVisibility(View.VISIBLE);
            locationViewHolder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position);
                }
            });
        } else {
            locationViewHolder.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }
    public void setDelete(boolean b) {
        isDeleting = b;
    }
    public void deleteItem(int position) {
        Location location = locationData.get(position);
        VehicleDataSource dataSource = new VehicleDataSource(parentContext);
        try {
            dataSource.open();
            boolean didDelete = dataSource.deleteLocation(location.getLocationID());
            dataSource.close();
            if (didDelete) {
                locationData.remove(position);
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
        return locationData.size();
    }
}

