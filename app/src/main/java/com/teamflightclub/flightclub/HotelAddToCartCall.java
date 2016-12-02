package com.teamflightclub.flightclub;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tim on 12/1/2016.
 */

public class HotelAddToCartCall extends AsyncTask<Void,Void,String> {

    Hotel hotel;
    AsyncCallback asyncCallback;
    Context context;

    public HotelAddToCartCall(Hotel cartHotel, AsyncCallback callback, Context contxt){

        hotel = cartHotel;
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
                    .addFormDataPart("reservationName", hotel.reservationName)
                    .addFormDataPart("reservationId", hotel.reservationId)
                    .addFormDataPart("reservationPrice", Double.toString(hotel.reservationPrice))
                    .addFormDataPart("reservationLocationCode", hotel.reservationLocationCode)
                    .addFormDataPart("reservationDb", hotel.reservationDb)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(requestBody)
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            Log.v("AddTOCartCall", response.body().string());

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

