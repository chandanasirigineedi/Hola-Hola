package com.ridemgmtsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ridemgmtsystem.LoginActivity;
import com.ridemgmtsystem.PostRideActivity;
import com.ridemgmtsystem.R;
import com.ridemgmtsystem.Utils.StoredPreferences;
import com.ridemgmtsystem.ViewRidesActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activty);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.post_ride_button)
    public void onPostARideButtonClick() {
        Intent intent = new Intent(this, PostRideActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_rides_button)
    public void onViewRidesButtonClick() {
        Intent intent = new Intent(this, ViewRidesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.my_rides_button)
    public void onMyRidesButtonClick() {
        Intent intent = new Intent(this, MyRidesActivity.class);
        startActivity(intent);
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