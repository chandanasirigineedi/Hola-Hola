package com.ridemgmtsystem.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.models.Ride;
import com.ridemgmtsystem.models.Seat;
import com.ridemgmtsystem.R;

import java.util.ArrayList;

public class SeatsAdapter  extends ArrayAdapter {

    private Ride selectedRide;
    private ArrayList<Seat> seatArrayList = new ArrayList<>();
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView seatAssigneeName;
        Button bookButton;
    }

    public SeatsAdapter(Ride selectedRide, Context context) {
        super(context, R.layout.seat_item, selectedRide.seatArrayList);
        this.selectedRide = selectedRide;
        this.seatArrayList.addAll(selectedRide.seatArrayList);
        this.mContext = context;
    }

    @Nullable
    @Override
    public Seat getItem(int position) {
        return seatArrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder; // view lookup cache stored in tag
        Seat seat = getItem(position);
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.seat_item, parent, false);
            viewHolder.seatAssigneeName = (TextView) convertView.findViewById(R.id.seat_assignee_name);
            viewHolder.bookButton = (Button) convertView.findViewById(R.id.book_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String seatAssigneeString="";
        if(seat.seatAssignee.isEmpty()) {
            seatAssigneeString = "<b>Seat Assignee </b>: N/A ";
        } else {
            seatAssigneeString = "<b>Seat Assignee </b>: "+seat.seatAssignee;
        }
        viewHolder.seatAssigneeName.setText(Html.fromHtml(seatAssigneeString));

        if(seat.isFilled) {
            viewHolder.bookButton.setVisibility(View.GONE);
        } else {
            viewHolder.bookButton.setVisibility(View.VISIBLE);
        }

        viewHolder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to book this seat ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                seat.isFilled = true;
                                seat.seatAssignee = StoredPreferences.getLoggedInUsername(mContext);
                                selectedRide.seatArrayList.clear();
                                selectedRide.seatArrayList.addAll(seatArrayList);
                                ArrayList<Ride> rideArrayList = StoredPreferences.getSavedRideListFromPreferences(mContext);
                                for(int i = 0; i < rideArrayList.size(); i++) {
                                    if(rideArrayList.get(i).rideID == selectedRide.rideID) {
                                        rideArrayList.set(i, selectedRide);
                                        break;
                                    }
                                }
                                StoredPreferences.saveRideListInPreferences(mContext, rideArrayList);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}

