package com.teamflightclub.flightclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teamflightclub.flightclub.adapter.AdapterRecPaymentSystem;
import com.teamflightclub.flightclub.model.ListItem;
import com.teamflightclub.flightclub.model.getSqldata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyCartActivity extends AppCompatActivity implements AdapterRecPaymentSystem.ItemClickCallback {

    private RecyclerView recView;
    private AdapterRecPaymentSystem adapter;
    private List<ListItem> listData;
    Button returnBTN;
    private static final String[] titles = {"Nothingess cannot be defied", "The softest thing cannot be snapped", "be like water, my friend."};
    private static final int[] icons = {android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_delete};
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
//        try {
//            listData = (ArrayList) new getSqldata(this).getCartData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        recView = (RecyclerView) findViewById(R.id.rec_list);

        listData  = new ArrayList<>();
        userId = PreferenceManager.getDefaultSharedPreferences(this).getString(("userRowID"), "");
        loadCart(userId);
        //LayoutManager: GridLayoutManager or StaggeredGridLayoutManager
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRecPaymentSystem(listData, this);
        adapter.setItemClickCallback(this);
        recView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recView);
        returnBTN = (Button) findViewById(R.id.btn_return_item);
        returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            moveMainActivity();
            }
        });
    }


    @Override
    public void onItemClick(int p) {
        ListItem item = (ListItem) listData.get(p);

        Intent intent = new Intent(this, PrePaymentSystemActivity.class);
//        intent.putExtra(listData);
        startActivity(intent);

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        //      moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                                deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;

    }

    private void deleteItem(final int position) {
        Reservation reservation = (Reservation) listData.get(position);
        Log.v("ReservationParams:",reservation.reservationId);
        AsyncTask<String,Void,String> task = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String deleteUrl = "http://teamflightclubproject.com/removeFromCart.php";
                Log.v("UserId",params[0]);
                Log.v("ReservationId",params[1]);
                try {
                    URL url = new URL(deleteUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("userId", "UTF-8")
                            + "=" + URLEncoder.encode(params[0], "UTF-8")+"&"+URLEncoder.encode("reservationId", "UTF-8")
                            + "=" + URLEncoder.encode(params[1], "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                adapter.notifyDataSetChanged();
                Toast.makeText(MyCartActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        };

        task.execute(userId,reservation.reservationId);
        listData.remove(position);
        adapter.notifyItemRemoved(position);
    }



    public void moveMainActivity(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    private void loadCart(String userId) {

        AsyncTask<String,Void,Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://teamflightclubproject.com/getCart.php?userId="+params[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray data = new JSONArray(response.body().string());

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject dataObject = data.getJSONObject(i);
                        Log.v("Cart Class",dataObject.getString("Type"));
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
                                flight.setListInfo(icons[1]);
                                listData.add(flight);
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
                                activity.setListInfo(icons[0]);
                                listData.add(activity);
                                break;

                            case "Car":
                                String PIID = dataObject.getString("PIID");
                                String supplierName = dataObject.getString("SupplierName");
                                int adultCount = dataObject.getInt("AdultCount");
                                String carMakeModel = dataObject.getString("CarMakeModel");
                                String ratePeriodCode = dataObject.getString("RatePeriodCode");
                                double unitPrice = dataObject.getDouble("UnitPrice");
                                double totalPrice = dataObject.getDouble("TotalPrice");
                                String carClass = dataObject.getString("CarClass");
                                int largeLuggageCount = dataObject.getInt("LargeLuggageCount");
                                int carDoorCount = dataObject.getInt("CarDoorCount");
                                String thumbnailUrl = dataObject.getString("ThumbnailUrl");
                                String dropOffCode = dataObject.getString("DropOffCode");
                                String pickupCode = dataObject.getString("PickupCode");
                                Car car = new Car(PIID,supplierName,adultCount,carMakeModel,ratePeriodCode,unitPrice,totalPrice,carClass,largeLuggageCount,carDoorCount,thumbnailUrl,dropOffCode,pickupCode);
                                car.setListInfo(icons[1]);
                                listData.add(car);
                                break;

                            case "Hotel":
                                String hotelName = dataObject.getString("Name");
                                int hotelId = dataObject.getInt("hotelId");
                                double rating = dataObject.getDouble("Rating");
                                String hotelAirportCode = dataObject.getString("airportCode");
                                double hotelDistance = dataObject.getDouble("Distance");
                                double hotelPrice = dataObject.getDouble("Price");
                                String amenities = dataObject.getString("Amenities");
                                String hotelThumbnailUrl = dataObject.getString("ThumbnailUrl");
                                String largeThumbnailUrl = dataObject.getString("LargeThumbnailUrl");
                                String description = dataObject.getString("Description");
                                Hotel hotel = new Hotel(hotelName,hotelId, rating, hotelAirportCode, hotelDistance, hotelPrice,
                                        hotelThumbnailUrl, amenities, largeThumbnailUrl, description);
                                hotel.setListInfo(icons[0]);
                                listData.add(hotel);
                                break;


                        }

                    }



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(userId);
    }

}