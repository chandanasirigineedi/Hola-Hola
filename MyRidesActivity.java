package com.ridemgmtsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ridemgmtsystem.LoginActivity;
import com.ridemgmtsystem.R;
import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.adapters.MyRidesAdapter;
import com.ridemgmtsystem.models.Ride;
import com.ridemgmtsystem.models.Seat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRidesActivity extends AppCompatActivity {

    ArrayList<Ride> filteredRideList;
    @BindView(R.id.my_rides_list) ListView myRidesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);
        ButterKnife.bind(this);
        filteredRideList = getFilteredRideList();
        Button button5=(Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            AnimFragment fragment1 = new AnimFragment();
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,fragment1).commit();
                Toast.makeText(getApplicationContext(), "Ride confirmed. Thank you for booking.", Toast.LENGTH_SHORT).show();// toast message to display
                finish();
            }
        });
    }

    private ArrayList<Ride> getFilteredRideList() {
        ArrayList<Ride> myRidesList = new ArrayList<>();
        String loggedInUser = StoredPreferences.getLoggedInUsername(this);
        ArrayList<Ride> storedRideList = StoredPreferences.getSavedRideListFromPreferences(this);
        for(Ride ride : storedRideList) {
            if(ride.driver.equalsIgnoreCase(loggedInUser)) {
                myRidesList.add(ride);
            } else {
                for(Seat seat : ride.seatArrayList) {
                    if(seat.seatAssignee.equalsIgnoreCase(loggedInUser)) {
                        myRidesList.add(ride);
                        break;
                    }
                }
            }
        }
        return  myRidesList;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListView();
    }

    public void updateListView() {
        MyRidesAdapter myRidesAdapter = new MyRidesAdapter(filteredRideList, this);
        myRidesList.setAdapter(myRidesAdapter);
        myRidesAdapter.notifyDataSetChanged();
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

}