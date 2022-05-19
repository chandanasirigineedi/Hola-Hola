package com.ridemgmtsystem.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ridemgmtsystem.models.Ride;
import com.ridemgmtsystem.models.UserAccount;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StoredPreferences {
    public static ArrayList<UserAccount> getSavedUserAccountsListFromPreferences(Context context) {
        ArrayList<UserAccount> userAccountArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
        String serializedObject = sharedPreferences.getString("user_account_list", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<UserAccount>>(){}.getType();
            userAccountArrayList = gson.fromJson(serializedObject, type);
        }
        return userAccountArrayList;
    }

    public static void saveUserAccountListInPreferences(Context context, ArrayList<UserAccount> userAccountArrayList) {
        Gson gson = new Gson();
        String userAccountListString = gson.toJson(userAccountArrayList);

        SharedPreferences sharedPref = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("user_account_list", userAccountListString);
        editor.commit();
    }

    public static ArrayList<Ride> getSavedRideListFromPreferences(Context context) {
        ArrayList<Ride> rideArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
        String serializedObject = sharedPreferences.getString("ride_list", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Ride>>(){}.getType();
            rideArrayList = gson.fromJson(serializedObject, type);
        }
        return rideArrayList;
    }

    public static void saveRideListInPreferences(Context context, ArrayList<Ride> rideArrayList) {
        Gson gson = new Gson();
        String rideListString = gson.toJson(rideArrayList);

        SharedPreferences sharedPref = context.getSharedPreferences("app_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("ride_list", rideListString);
        editor.commit();
    }

    public static int getSelectedRideID(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("selected_ride_id", 0);
    }

    public static void saveSelectedRideID(Context context,int selectedRideID) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("selected_ride_id", selectedRideID).apply();
    }

    public static String getLoggedInUsername(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("logged_user", "");
    }

    public static void saveLoggedInUsername(Context context,String loggedInUserName) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("logged_user", loggedInUserName).apply();
    }
}
