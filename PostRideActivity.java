package com.ridemgmtsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.ridemgmtsystem.R;
import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.models.Ride;
import com.ridemgmtsystem.models.Seat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class  PostRideActivity extends AppCompatActivity {

    @BindView(R.id.start_address) TextInputEditText startAddress;
    @BindView(R.id.destination_address) TextInputEditText destinationAddress;
    @BindView(R.id.seat_count) TextInputEditText seatCount;
    @BindView(R.id.price_per_seat) TextInputEditText pricePerSeat;
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.submit_button)
    public void onSubmitButtonClick() {
        if(!startAddress.getText().toString().isEmpty() &&
                !destinationAddress.getText().toString().isEmpty() &&
                !seatCount.getText().toString().isEmpty() &&
                !pricePerSeat.getText().toString().isEmpty() &&
                !selectedDate.isEmpty() &&
                !StoredPreferences.getLoggedInUsername(this).isEmpty()) {
            Ride ride = new Ride();
            ride.startAddress = startAddress.getText().toString();
            ride.destinationAddress = destinationAddress.getText().toString();
            ride.seatArrayList = new ArrayList<>();
            for(int i = 0;i<=Integer.parseInt(seatCount.getText().toString());i++ ) {
                Seat seat = new Seat();
                seat.isFilled = false;
                seat.seatAssignee = "";
                ride.seatArrayList.add(seat);
            }
            ride.pricePerSeat = Integer.parseInt(pricePerSeat.getText().toString());
            ride.date = selectedDate;
            ride.driver = StoredPreferences.getLoggedInUsername(this);
            ride.rideID = new Random().nextInt(999999);

            ArrayList<Ride> storedRidesList = StoredPreferences.getSavedRideListFromPreferences(this);
            storedRidesList.add(ride);
            StoredPreferences.saveRideListInPreferences(this, storedRidesList);
            finish();
        }
    }

    @OnClick(R.id.date_button)
    public void dateAndTimePicker() {
        new SingleDateAndTimePickerDialog.Builder(this)
                .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                    @Override
                    public void onDisplayed(SingleDateAndTimePicker picker) {

                    }
                })
                .title("Choose Date and time")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        selectedDate = date.toString();
                    }
                })
                .display();
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