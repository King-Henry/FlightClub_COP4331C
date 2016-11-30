package com.teamflightclub.flightclub;

/**
 * Created by Carrito on 11/29/2016.
 */

public class Hotel extends Reservation{

    int hotelId;
    String name;
    double rating;
    double distance;
    String airportCode;
    double price;
    String[] amenities;
    String thumbnailUrl;
    String largeThumbnailUrl;
    String description;

    public Hotel(String name, int hotelId, double rating, String airportCode, double distance, double price, String thumbnailUrl, String amenities, String largeThumbnailUrl, String description) {
        this.reservationId = Integer.toString(hotelId);
        this.reservationLocationCode = airportCode;
        this.reservationPrice = price;
        this.reservationDb = "Hotels";
        this.name = name;
        this.hotelId = hotelId;
        this.rating = rating;
        this.airportCode = airportCode;
        this.distance = distance;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.amenities = amenities.split("\\+");
        this.largeThumbnailUrl = largeThumbnailUrl;
        this.description = description;
    }
}
