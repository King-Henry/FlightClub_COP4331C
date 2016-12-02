package com.teamflightclub.flightclub;

/**
 * Created by Carrito on 11/30/2016.
 */

public class Activity extends Reservation {

    String ID;
    String title;
    String duration;
    String fromPrice;
    String fromPriceTicketType;
    String imageUrl;
    String airportCode;

    public Activity(String ID, String title, String duration, String fromPrice, String fromPriceTicketType, String imageUrl, String airportCode) {
        this.reservationName = title;
        this.reservationId = ID;
        this.reservationDb = "Activities";
        this.reservationLocationCode = airportCode;
        this.reservationPrice = Double.parseDouble(fromPrice.substring(1));
        this.ID = ID;
        this.title = title;
        this.duration = duration;
        this.fromPrice = fromPrice;
        this.fromPriceTicketType = fromPriceTicketType;
        this.imageUrl = imageUrl;
        this.airportCode = airportCode;
    }

}

