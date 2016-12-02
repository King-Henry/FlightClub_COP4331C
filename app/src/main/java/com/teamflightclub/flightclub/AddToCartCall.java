package com.teamflightclub.flightclub;

import android.content.Context;
import android.os.AsyncTask;
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


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
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
        try {
            Response response = okHttpClient.newCall(request).execute();
            Log.v("AddTOCartCall", response.body().string());
            return response.body().string();


        }catch (IOException e){

            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {

        if(response != null && response.equals("Successfully Added to Cart")){

            asyncCallback.done();
        }

        else{

            new AlertDialog.Builder(context).setMessage("Adding to cart failed. Make sure you are connected to the internet");
        }


    }


}
