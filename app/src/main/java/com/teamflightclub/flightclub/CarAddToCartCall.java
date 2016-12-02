package com.teamflightclub.flightclub;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tim on 12/2/2016.
 */

public class CarAddToCartCall extends AsyncTask<Void,Void,String> {

    Car car;
    AsyncCallback asyncCallback;
    Context context;

    public CarAddToCartCall(Car cartCar, AsyncCallback callback, Context contxt){

        car = cartCar;
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
                    .addFormDataPart("reservationName", car.reservationName)
                    .addFormDataPart("reservationId", car.reservationId)
                    .addFormDataPart("reservationPrice", Double.toString(car.reservationPrice))
                    .addFormDataPart("reservationLocationCode", car.reservationLocationCode)
                    .addFormDataPart("reservationDb", car.reservationDb)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(requestBody)
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            Log.v("AddTOCartCall", response.message());



            return response.message();


        }catch (IOException e){

            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String myResponse) {


        if(!myResponse.isEmpty()){

            Log.v("asyncCallback","Point Reached");
            asyncCallback.done();

        }

        else{
            Log.v("DO we hit this?", "YAAHHHSS");
            new AlertDialog.Builder(context).setMessage("Adding to cart failed. Make sure you are connected to the internet").show();
        }


    }

}
