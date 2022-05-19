package com.ridemgmtsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.ridemgmtsystem.R;
import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.adapters.SeatsAdapter;
import com.ridemgmtsystem.models.Ride;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookRidesActivity extends AppCompatActivity {

    @BindView(R.id.seats_list) ListView seats_list;
    @BindView(R.id.start_address_text_view) TextView startAddress;
    @BindView(R.id.destination_address_text_view) TextView destinationAddress;
    @BindView(R.id.date_text_view) TextView dateTextView;

    Ride selectedRide;
    int selectedRideID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_rides);
        ButterKnife.bind(this);
        selectedRideID = StoredPreferences.getSelectedRideID(this);
        ArrayList<Ride> ridesList = StoredPreferences.getSavedRideListFromPreferences(this);
        for(Ride ride : ridesList) {
            if(ride.rideID == selectedRideID) {
                selectedRide = ride;
            }
        }

        String startAddressString = "<b>Start Address </b>: "+selectedRide.startAddress;
        startAddress.setText(Html.fromHtml(startAddressString));

        String destinationAddressString = "<b>Destination Address </b>: "+selectedRide.destinationAddress;
        destinationAddress.setText(Html.fromHtml(destinationAddressString));

        String dateString = "<b>Date and Time </b>: "+selectedRide.date;
        dateTextView.setText(Html.fromHtml(dateString));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListView();
    }

    public void updateListView() {
        SeatsAdapter seatsAdapter = new SeatsAdapter(selectedRide, this);
        seats_list.setAdapter(seatsAdapter);
        seatsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    void logoutUser() {
        StoredPreferences.saveLoggedInUsername(this,"");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}