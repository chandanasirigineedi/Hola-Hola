package com.ridemgmtsystem.models;

import java.util.ArrayList;

public class Ride {
    public String startAddress;
    public String destinationAddress;
    public ArrayList<com.ridemgmtsystem.models.Seat> seatArrayList;
    public int pricePerSeat;
    public int rideID;
    public String date;
    public String driver;
}
