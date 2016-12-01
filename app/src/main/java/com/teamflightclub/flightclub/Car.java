package com.teamflightclub.flightclub;

/**
 * Created by Carrito on 11/29/2016.
 */

public class Car extends Reservation{

    String PIID;
    String supplierName;
    String carMakeModel;
    int adultCount;
    String ratePeriodCode;
    double unitPrice;
    double totalPrice;
    String carClass;
    int carDoorCount;
    int largeLuggageCount;
    String thumbnailUrl;
    String dropOffCode;
    String pickupCode;

    public Car() {
    }

    public Car(String PIID, String supplierName, int adultCount, String carMakeModel, String ratePeriodCode, double unitPrice, double totalPrice, String carClass, int largeLuggageCount, int carDoorCount, String thumbnailUrl, String dropOffCode, String pickupCode) {
        this.reservationDb = "Cars";
        this.PIID = PIID;
        this.reservationId = PIID;
        this.supplierName = supplierName;
        this.adultCount = adultCount;
        this.carMakeModel = carMakeModel;
        this.reservationName = carMakeModel;
        this.ratePeriodCode = ratePeriodCode;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.reservationPrice = totalPrice;
        this.carClass = carClass;
        this.largeLuggageCount = largeLuggageCount;
        this.carDoorCount = carDoorCount;
        this.thumbnailUrl = thumbnailUrl;
        this.dropOffCode = dropOffCode;
        this.reservationLocationCode = dropOffCode;
        this.pickupCode = pickupCode;
    }

}
