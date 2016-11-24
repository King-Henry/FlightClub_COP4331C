package com.teamflightclub.flightclub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Carrito on 11/24/2016.
 */

public class ChangePasswordAuthenticator extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
//    AsyncCallback callback;
    String result = "";



    ChangePasswordAuthenticator(Context contxt) {

        context = contxt;
    }

    @Override
    protected String doInBackground(String... params) {
        String changePassword_url = "http://teamflightclubproject.com/changePassword.php";
        try {
            String rowID = params[0];
            String oldPassword = params[1];
            String newPassword = params[2];
            URL url = new URL(changePassword_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("rowID", "UTF-8") + "=" + URLEncoder.encode(rowID, "UTF-8") + "&"
                    + URLEncoder.encode("oldPassword", "UTF-8") + "=" + URLEncoder.encode(oldPassword, "UTF-8") + "&"
                    + URLEncoder.encode("newPassword", "UTF-8") + "=" + URLEncoder.encode(newPassword, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Change Password Status");
    }

    @Override
    protected void onPostExecute(String result) {
//        String resultMessage = "";
//        if (result.equals(""))
//            resultMessage = "Username/Password Not Found";
//        else {
//            //rowID = result;
//            resultMessage = "Login Successful";
//            Intent intent = new Intent(context, ControlPanelActivity.class);
//            intent.putExtra("rowID",result);
//            context.startActivity(intent);
//        }
        alertDialog.setMessage(result);
        alertDialog.show();

//        if (result.equals("Password Change Successful")) {
//
//            Log.v("ChangePassword", "Password CHANGED");
//            //LoginActivity.LOGIN_RESULT = 1000;
//        } else {
//
//            //LoginActivity.LOGIN_RESULT = 0;
//        }
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//                alertDialog.dismiss();
//                //callback.done();
//            }
//        },2000);


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
