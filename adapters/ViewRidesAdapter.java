package com.ridemgmtsystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ridemgmtsystem.BookRidesActivity;
import com.ridemgmtsystem.R;
import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.models.Ride;
import com.ridemgmtsystem.models.Seat;

import java.util.ArrayList;

public class ViewRidesAdapter extends ArrayAdapter {

    private ArrayList<Ride> rideArrayList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView startAddress;
        TextView destinationAddress;
        TextView seatsAvailable;
        TextView seatsOccupied;
        TextView pricePerSeats;
        TextView dateString;
        TextView driver;
        LinearLayout rideItemLayout;
        TextView rideID;
    }

    public ViewRidesAdapter(ArrayList data, Context context) {
        super(context, R.layout.ride_item, data);
        this.rideArrayList = data;
        this.mContext = context;
    }

    @Nullable
    @Override
    public Ride getItem(int position) {
        return rideArrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder; // view lookup cache stored in tag
        Ride ride = getItem(position);
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ride_item, parent, false);
            viewHolder.startAddress = (TextView) convertView.findViewById(R.id.start_address_text_view);
            viewHolder.destinationAddress = (TextView) convertView.findViewById(R.id.destination_address_text_view);
            viewHolder.seatsAvailable = (TextView) convertView.findViewById(R.id.seats_available_text_view);
            viewHolder.seatsOccupied = (TextView) convertView.findViewById(R.id.seats_occupied_text_view);
            viewHolder.pricePerSeats = (TextView) convertView.findViewById(R.id.price_per_seats_text_view);
            viewHolder.dateString = (TextView) convertView.findViewById(R.id.date_text_view);
            viewHolder.driver = (TextView) convertView.findViewById(R.id.driver);
            viewHolder.rideID = (TextView) convertView.findViewById(R.id.ride_id_text_view);
            viewHolder.rideItemLayout = (LinearLayout) convertView.findViewById(R.id.ride_item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String startAddress = "<b>Start Address </b>: "+ride.startAddress;
        viewHolder.startAddress.setText(Html.fromHtml(startAddress));

        String destinationAddress = "<b>Destination Address </b>: "+ride.destinationAddress;
        viewHolder.destinationAddress.setText(Html.fromHtml(destinationAddress));

        int seatsAvailableCount = 0;
        int seatsOccupiedCount = 0;
        for(Seat seat : ride.seatArrayList) {
            if(seat.isFilled) {
                seatsOccupiedCount++;
            } else {
                seatsAvailableCount++;
            }
        }

        String seatsAvailable = "<b>Seats Available </b>: "+seatsAvailableCount;
        viewHolder.seatsAvailable.setText(Html.fromHtml(seatsAvailable));

        String seatsOccupied = "<b>Seats Occupied </b>: "+seatsOccupiedCount;
        viewHolder.seatsOccupied.setText(Html.fromHtml(seatsOccupied));

        String pricePerSeats = "<b>Price per Seat </b>: $"+ride.pricePerSeat;
        viewHolder.pricePerSeats.setText(Html.fromHtml(pricePerSeats));

        String dateString = "<b>Date and Time </b>: "+ride.date;
        viewHolder.dateString.setText(Html.fromHtml(dateString));

        String driver = "<b>Driver </b>: "+ride.driver;
        viewHolder.driver.setText(Html.fromHtml(driver));

        String rideID = "<b>Ride ID </b>: "+ride.rideID;
        viewHolder.rideID.setText(Html.fromHtml(rideID));

        viewHolder.rideItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoredPreferences.saveSelectedRideID(mContext, ride.rideID);
                Intent intent = new Intent(mContext, BookRidesActivity.class);
                mContext.startActivity(intent);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}

