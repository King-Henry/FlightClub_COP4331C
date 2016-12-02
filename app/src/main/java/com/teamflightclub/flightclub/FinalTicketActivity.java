package com.teamflightclub.flightclub;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

public class FinalTicketActivity extends AppCompatActivity {
    ImageView qrCodeImageview;
    String QRcode;
    File pdfFile;
    TextView price;
    TextView address;
    TextView name;
    TextView creditCard;
    TextView ticketNumber;
    Bitmap bitmap2;
    Bitmap bitmap3;
    Bitmap screen;
    String name2;
    String last2;
    String address2;
    Button savePDF;
    public final static int WIDTH = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_ticket);
        price = (TextView)findViewById(R.id.final_price);
        address = (TextView)findViewById(R.id.final_address);
        name = (TextView)findViewById(R.id.final_name);
        creditCard = (TextView)findViewById(R.id.final_creditcard);
        ticketNumber = (TextView)findViewById(R.id.final_ticketnumber);
        savePDF = (Button)findViewById(R.id.final_save);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name2 = extras.getString("firstName");
            last2 = extras.getString("lastName");
            address2 = extras.getString("address");
            //The key argument here must match that used in the other activity
        }
        writeText();
        createQR();
        //getIMG();

    }

    @Override
    protected void onResume() {
        super.onResume();
        onWindowFocusChanged();
        savePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  savePDF.setVisibility(View.GONE);
                getIMG();
            }
        });
    }

    public void createQR(){
        getID();
        Thread t = new Thread(new Runnable() {
            public void run() {
                QRcode = "This is My first QR code";
                try {
                    synchronized (this) {
                        wait(2000);
// runOnUiThread method used to do UI task in main thread.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {


                                    bitmap2 = encodeAsBitmap(QRcode);
                                    qrCodeImageview.setImageBitmap(bitmap2);

                                } catch (WriterException e) {
                                    e.printStackTrace();
                                } // end of catch block

                            } // end of run method
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        t.start();
    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {

            case 200:

                boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                break;

        }

    }

    private boolean shouldAskPermission() {

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void getID() {
        qrCodeImageview = (ImageView) findViewById(R.id.img_qr_code_image);
    }

    // this is method call from on create and return bitmap image of QRCode.
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black2) : getResources().getColor(R.color.white2);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    } /// end of this method


    public void onWindowFocusChanged(){
        int view = R.id.final_layout_external;
        final ViewGroup v = (ViewGroup) ((ViewGroup) this
                .findViewById(view)).getChildAt(0).getRootView();
      //  Bitmap screen;
        //   View v = mRootView;
        ViewGroup v2 = v;
        v2.setDrawingCacheEnabled(true);
        Button lay = (Button)v2.findViewById(R.id.final_save);
       // lay.setVisibility(View.GONE);
       // savePDF.setVisibility(View.GONE);
// this is the important code :)
// Without it the view will have a dimension of 0,0 and the bitmap will be null
        v2.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v2.layout(0, 0, v2.getMeasuredWidth(), v2.getMeasuredHeight());

        v2.buildDrawingCache(true);
        screen = Bitmap.createBitmap(v2.getDrawingCache(true));
        v2.setDrawingCacheEnabled(false);

    }
    void getIMG() {
      //  View mRootView = findViewById(R.id.last_layout);
        verifyStoragePermissions(this);
        //Assuming your rootView is called mRootView like so

//First Check if the external storage is writable
        String state = Environment.getExternalStorageState();

//Then take the screen shot
       // final ViewGroup v = (ViewGroup) ((ViewGroup) this
      //          .findViewById(R.id.last_layout)).getChildAt(0).getRootView();
    //    Bitmap screen;
     //   View v = mRootView;
       // v.setDrawingCacheEnabled(true);

// this is the important code :)
// Without it the view will have a dimension of 0,0 and the bitmap will be null
       // v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        //        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

       // v.buildDrawingCache(true);
      //  screen = Bitmap.createBitmap(v.getDrawingCache(true));
     //   v.setDrawingCacheEnabled(false);


//Now create the name of your PDF file that you will generate
        //  pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"myPdfFile.pdf");
        pdfFile = new File(Environment.getExternalStorageDirectory() + "/Download", "FlightTicketNumber1045543262.pdf");

        convertPDF();
    }

    public void convertPDF() {
        try {
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
         //   ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //    screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
        //    byte[] byteArray = stream.toByteArray();
       //     addImage(document, byteArray);


           // Bitmap bmap = encodeAsBitmap(QRcode);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.PNG,100,stream2);
            byte[] byteArray2 = stream2.toByteArray();
            addImage(document,byteArray2);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addImage(Document document, byte[] byteArray) {
        Image image = null;
        try {
            image = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // image.scaleAbsolute(150f, 150f);
        try {
            document.add(image);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void savePDF() throws FileNotFoundException {
        File outputFile = new File("/sdcard/PDFDemo_AndroidSRC/", "demo");

        try {
            outputFile.createNewFile();
            OutputStream out = new FileOutputStream(pdfFile);


            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeText(){
        String confirmationMess = "This is your ticket confirmation";
        String ticketNumber = "Your ticket number is " + "1045543262";
        this.ticketNumber.setText(ticketNumber);
        String ticketPrice = "More Info...";
        price.setText(ticketPrice);
        String userName = "Your name is " + name2;
        name.setText(userName);
        String address = "Your address is \n" + address2;
        this.address.setText(address);
        String creditCard = "Your sale was under the credit card \n XXXX XXXX XXXX " + "4242";
        this.creditCard.setText(creditCard);
        String divMes= "-----------------------------------------------------------------";
        String Qrcode = "This will be your confirmation QR code";
    }
    }






