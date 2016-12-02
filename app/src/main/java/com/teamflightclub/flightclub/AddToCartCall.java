package com.teamflightclub.flightclub;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tim on 12/1/2016.
 */

public class AddToCartCall extends AsyncTask<Void,Void,String> {

    Flight flight;
    AsyncCallback asyncCallback;
    Context context;

    public AddToCartCall(Flight cartFlight, AsyncCallback callback, Context contxt){

        flight = cartFlight;
        asyncCallback = callback;
        context = contxt;
    }

    @Override
    protected String doInBackground(Void ...params) {

        String url = "http://teamflightclubproject.com/addToCart.php";

        final OkHttpClient okHttpClient = new OkHttpClient();

        try {


            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("userId", PreferenceManager.getDefaultSharedPreferences(context).getString(("userRowID"), ""))
                    .addFormDataPart("reservationName", flight.reservationName)
                    .addFormDataPart("reservationId", flight.reservationId)
                    .addFormDataPart("reservationPrice", Double.toString(flight.reservationPrice))
                    .addFormDataPart("reservationLocationCode", flight.reservationLocationCode)
                    .addFormDataPart("reservationDb", flight.reservationDb)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(requestBody)
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            Log.v("AddTOCartCall", response.body().string());

            if (flight.secondLeg != null) {

                RequestBody requestBodyTwo = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("userId", PreferenceManager.getDefaultSharedPreferences(context).getString(("userRowID"), ""))
                        .addFormDataPart("reservationName", flight.secondLeg.reservationName)
                        .addFormDataPart("reservationId", flight.secondLeg.reservationId)
                        .addFormDataPart("reservationPrice", Double.toString(flight.secondLeg.reservationPrice))
                        .addFormDataPart("reservationLocationCode", flight.secondLeg.reservationLocationCode)
                        .addFormDataPart("reservationDb", flight.secondLeg.reservationDb)
                        .build();

                Request requestTwo = new Request.Builder()
                        .url(url)
                        .method("POST", RequestBody.create(null, new byte[0]))
                        .post(requestBodyTwo)
                        .build();

                Response responseTwo = okHttpClient.newCall(requestTwo).execute();
                Log.v("AddTOCartCall", responseTwo.body().string());

                if (flight.thirdLeg != null) {

                    RequestBody requestBodyThree = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("userId", PreferenceManager.getDefaultSharedPreferences(context).getString(("userRowID"), ""))
                            .addFormDataPart("reservationName", flight.thirdLeg.reservationName)
                            .addFormDataPart("reservationId", flight.thirdLeg.reservationId)
                            .addFormDataPart("reservationPrice", Double.toString(flight.thirdLeg.reservationPrice))
                            .addFormDataPart("reservationLocationCode", flight.thirdLeg.reservationLocationCode)
                            .addFormDataPart("reservationDb", flight.thirdLeg.reservationDb)
                            .build();

                    Request requestThree = new Request.Builder()
                            .url(url)
                            .method("POST", RequestBody.create(null, new byte[0]))
                            .post(requestBodyThree)
                            .build();

                    Response responseThree = okHttpClient.newCall(requestThree).execute();
                    Log.v("AddTOCartCall", responseThree.body().string());

                    return (responseThree.body().string());

                }

                return (responseTwo.body().string());
            }

            return (response.body().string());


        }catch (IOException e){

            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {

        Log.v("PostExecuteResponse",response);

        if(!response.equals("") && !response.equals(null) && response.equals("Successfully Added to Cart")){
            Log.v("asyncCallback","Point Reached");
            asyncCallback.done();
        }

        else{

            new AlertDialog.Builder(context).setMessage("Adding to cart failed. Make sure you are connected to the internet");
        }


    }


}
