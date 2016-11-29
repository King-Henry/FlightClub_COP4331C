package com.teamflightclub.flightclub;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tim on 11/28/2016.
 */

public class Flight {

    String airlineName;
    String flightNumber;
    String departureTime;
    String arrivalTime;
    double price;
    String fromNameToDestName;
    int ID;
    int distance;
    String arrivalAirportCode;
    String departureAirportCode;
    String departureAirportLocation;
    String arrivalAirportLocation;
    Flight secondLeg;
    Flight thirdLeg;
    String departureDate;
    String arrivalDate;
    String duration;
    int seatsRemaining;
    String legId;

    Flight () {

    }

    Flight (String airlineName, String flightNumber, String departureTime, String arrivalTime, double price, int ID, int distance,
            String arrivalAirportCode, String departureAirportCode, String departureAirportLocation, String arrivalAirportLocation,
            String departureDate, String arrivalDate, String duration, int seatsRemaining, String legId) {
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.ID = ID;
        this.distance = distance;
        this.arrivalAirportCode = arrivalAirportCode;
        this.departureAirportCode = departureAirportCode;
        this.departureAirportLocation = departureAirportLocation;
        this.arrivalAirportLocation = arrivalAirportLocation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.duration = duration;
        this.seatsRemaining = seatsRemaining;
        this.legId = legId;
    }



//    public static Flight setFlight(JSONObject flightDataObject) throws JSONException {
//        Flight flight = new Flight();
//        airlineName = flightDataObject.getString("airlineName");
//        ID = flightDataObject.getInt("ID");
//        departureTime = flightDataObject.getString("departureTime");
//        arrivalTime = flightDataObject.getString("arrivalTime");
//        price = flightDataObject.getDouble("price");
//        distance = flightDataObject.getInt("distance");
//        arrivalAirportCode = flightDataObject.getString("arrivalAirportCode");
//        departureAirportCode = flightDataObject.getString("departureAirportCode");
//        departureAirportLocation = flightDataObject.getString("departureAirportLocation");
//        arrivalAirportLocation = flightDataObject.getString("arrivalAirportLocation");
//        departureDate = flightDataObject.getString("departureDate");
//        arrivalDate = flightDataObject.getString("arrivalDate");
//        duration = flightDataObject.getString("duration");
//        seatsRemaining = flightDataObject.getInt("seatsRemaining");
//        legId = flightDataObject.getString("legId");
//        return flight;
//    }

//    {u'distance': u'952', u'departureTime': u'4:55:00 PM', u'arrivalAirportCode': u'JFK', u'thirdLeg': None, u'arrivalDate': u'December 9, 2016', u'departureAirportCode': u'MCO', u'airlineName': u'JetBlue Airways', u'flightNumber': u'284', u'legId': u'ad56aeb8f011ed5a59a8d32f12120605', u'price': u'203.10', u'secondLeg': None, u'departureAirportLocation': u'Orlando, USA', u'departureDate': u'December 9, 2016', u'arrivalTime': u'7:31:00 PM', u'duration': u'2h 36m', u'arrivalAirportLocation': u'New York, USA', u'seatsRemaining': u'7', u'ID': u'1335'}

}
