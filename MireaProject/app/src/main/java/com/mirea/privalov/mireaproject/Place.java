package com.mirea.privalov.mireaproject;

public class Place {
    private String name;
    private String address;
    private String description; // Добавляем поле для описания
    private double latitude;
    private double longitude;

    public Place(String name, String address, String description, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}