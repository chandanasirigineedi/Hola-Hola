package com.ridemgmtsystem.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ridemgmtsystem.R;
import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.models.Ride;
import com.ridemgmtsystem.models.Seat;

import java.util.ArrayList;

public class MyRidesAdapter extends ArrayAdapter {

    private ArrayList<Ride> rideArrayList;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView rideID;
        TextView startAddress;
        TextView destinationAddress;
        TextView dateTextView;
        TextView driver;
        TextView seatsBooked;
        TextView totalPrice;
    }

    public MyRidesAdapter(ArrayList data, Context context) {
        super(context, R.layout.my_ride_item, data);
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
            convertView = inflater.inflate(R.layout.my_ride_item, parent, false);
            viewHolder.rideID = (TextView) convertView.findViewById(R.id.ride_id_text_view);
            viewHolder.startAddress = (TextView) convertView.findViewById(R.id.start_address_text_view);
            viewHolder.destinationAddress = (TextView) convertView.findViewById(R.id.destination_address_text_view);
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.date_text_view);
            viewHolder.driver = (TextView) convertView.findViewById(R.id.driver);
            viewHolder.seatsBooked = (TextView) convertView.findViewById(R.id.seats_booked_text_view);
            viewHolder.totalPrice = (TextView) convertView.findViewById(R.id.total_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String rideID = "<b>Ride ID </b>: "+ride.rideID;
        viewHolder.rideID.setText(Html.fromHtml(rideID));

        String startAddress = "<b>Start Address </b>: "+ride.startAddress;
        viewHolder.startAddress.setText(Html.fromHtml(startAddress));

        String destinationAddress = "<b>Destination Address </b>: "+ride.destinationAddress;
        viewHolder.destinationAddress.setText(Html.fromHtml(destinationAddress));

        String dateString = "<b>Date and Time </b>: "+ride.date;
        viewHolder.dateTextView.setText(Html.fromHtml(dateString));

        String driver = "<b>Driver </b>: "+ride.driver;
        viewHolder.driver.setText(Html.fromHtml(driver));

        String loggedInUser = StoredPreferences.getLoggedInUsername(mContext);

        int mySeatCount = 0;
        int myTotalPrice = 0;
        for(Seat seat : ride.seatArrayList) {
            if(seat.seatAssignee.equalsIgnoreCase(loggedInUser)) {
                mySeatCount++;
            }
        }

        if(mySeatCount != 0) {
            myTotalPrice = ride.pricePerSeat * mySeatCount;
        }

        if(mySeatCount != 0) {
            String seatsBookedString = "<b>Seats Booked </b>: "+mySeatCount;
            viewHolder.seatsBooked.setText(Html.fromHtml(seatsBookedString));
        } else {
            String seatsBookedString = "<b>Seats Booked </b>: "+"Self Driving";
            viewHolder.seatsBooked.setText(Html.fromHtml(seatsBookedString));
        }

        String totalPriceString = "<b>Total Price </b>: $"+myTotalPrice;
        viewHolder.totalPrice.setText(Html.fromHtml(totalPriceString));

        // Return the completed view to render on screen
        return convertView;
    }
}
