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
import com.ridemgmtsystem.adapters.ViewRidesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewRidesActivity extends AppCompatActivity {

    @BindView(R.id.view_rides_list) ListView viewRidesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rides);

        ButterKnife.bind(this);
        Button homepage=(Button)findViewById(R.id.button4);
        homepage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back button redirected to home page", Toast.LENGTH_SHORT).show();// toast message to display
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListView();
    }

    public void updateListView() {
        ViewRidesAdapter viewRidesAdapter = new ViewRidesAdapter(StoredPreferences.getSavedRideListFromPreferences(this), this);
        viewRidesList.setAdapter(viewRidesAdapter);
        viewRidesAdapter.notifyDataSetChanged();
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