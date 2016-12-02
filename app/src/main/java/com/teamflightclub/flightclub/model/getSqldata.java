package com.teamflightclub.flightclub.model;


/**
 * Created by Gio on 11/23/2016.
 */

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.teamflightclub.flightclub.Activity;
import com.teamflightclub.flightclub.Flight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getSqldata {
    private static final String[] titles = {"Nothingess cannot be defied", "The softest thing cannot be snapped", "be like water, my friend."};
    private static final int[] icons = {android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_delete};
    private static Context contxt;

    public getSqldata(Context contxt) {
        this.contxt = contxt;
    }

    /* Get data for the view purchased ticket activity */
    public static List<ListItem> getListData() {
        List<ListItem> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                ListItem item = new ListItem();
                item.setImageResId(icons[x]);
                item.setTitle(titles[x]);
                data.add(item);
            }
        }
        return data;
    }

    public static List<ListItem> getCartData() throws IOException {
        List<ListItem> cartData = new ArrayList<ListItem>();

        String url = "";
        String userId = PreferenceManager.getDefaultSharedPreferences(contxt).getString(("userRowID"), "");

        try {
            url = "http://teamflightclubproject.com/getCart.php?" + URLEncoder.encode("userId", "UTF-8")
                    + "=" + URLEncoder.encode(userId, "UTF-8");

            Log.v("Print URL", url.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();


        //flightIdSet.clear();

        try {
            Response response = client.newCall(request).execute();

            JSONArray data = new JSONArray(response.body().string());

            for (int i = 0; i < data.length(); i++) {

                JSONObject dataObject = data.getJSONObject(i);

                switch (dataObject.getString("Type")) {

                    case "Flight":
                        String airlineName = dataObject.getString("airlineName");
                        int ID = dataObject.getInt("ID");
                        String flightNumber = dataObject.getString("flightNumber");
                        String departureTime = dataObject.getString("departureTime");
                        String arrivalTime = dataObject.getString("arrivalTime");
                        double price = dataObject.getDouble("price");
                        int distance = dataObject.getInt("distance");
                        String arrivalAirportCode = dataObject.getString("arrivalAirportCode");
                        String departureAirportCode = dataObject.getString("departureAirportCode");
                        String departureAirportLocation = dataObject.getString("departureAirportLocation");
                        String arrivalAirportLocation = dataObject.getString("arrivalAirportLocation");
                        String departureDate = dataObject.getString("departureDate");
                        String arrivalDate = dataObject.getString("arrivalDate");
                        String duration = dataObject.getString("duration");
                        int seatsRemaining = dataObject.getInt("seatsRemaining");
                        String legId = dataObject.getString("legId");
                        Flight flight = new Flight(airlineName, flightNumber, departureTime, arrivalTime, price, ID, distance,
                                arrivalAirportCode, departureAirportCode, departureAirportLocation, arrivalAirportLocation,
                                departureDate, arrivalDate, duration, seatsRemaining, legId);
                        flight.setListInfo(1);
                        cartData.add(flight);
                        break;

                    case "Activity":
                        String fromPriceTicketType = dataObject.getString("FromPriceTicketType");
                        String fromPrice = dataObject.getString("FromPrice");
                        String title = dataObject.getString("Title");
                        String imageUrl = dataObject.getString("ImageUrl");
                        String airportCode = dataObject.getString("airportCode");
                        duration = dataObject.getString("Duration");
                        String activityID = dataObject.getString("ID");
                        Activity activity = new Activity(activityID, title, duration, fromPrice, fromPriceTicketType, imageUrl, airportCode);
                        activity.setListInfo(0);
                        cartData.add(activity);
                        break;

                }

            }


            return cartData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cartData;
//        CartRetrieveAuthenticator cartRetrieveAuthenticator = new CartRetrieveAuthenticator(contxt);
//        return cartRetrieveAuthenticator.execute();
    }
}
/* Get data for the MyCart Activity */
//    public static List<ListItem> getListData2() {
//        List<ListItem > data = new ArrayList<>();
//        for (int i=0; i<3; i++) {
//            for (int x = 0; x < 3; x++) {
//                ListItem item = new ListItem();
//                item.setImageResId(icons[x]);
//                item.setTitle(titles[x]);
//                data.add(item);
//            }
//        }
//        return data;
//    }


