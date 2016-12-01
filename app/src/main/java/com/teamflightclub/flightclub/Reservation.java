package com.teamflightclub.flightclub;

import com.teamflightclub.flightclub.model.ListItem;

import java.util.List;

/**
 * Created by Carrito on 11/29/2016.
 */

public class Reservation extends ListItem{
    String reservationName;
    double reservationPrice;
    String reservationId;
    String reservationLocationCode;
    String reservationDb;

    public void setListInfo(int imageResId) {
        setImageResId(imageResId);
        setTitle(this.reservationName);
    }
}
