package com.sunmi.printerhelper.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.content.Intent;
import android.provider.UserDictionary;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.sunmi.printerhelper.R;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by Administrator on 2017/5/27.
 */
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;


public class AllActivity extends AppCompatActivity  {

    String[] data;
    private WebAppInterface webAppInterface;
    SurfaceView cameraView;
   public static TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1;
    public static String vehicle,ip_address;
   public static AlertDialog dialog;
    public static AlertDialog dialog1;
    public static AlertDialog dialog3;
    AlertDialog.Builder builder1,builder3;

     View view1,view;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }
    @Override
    public  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long startTime = System.nanoTime();
        setContentView(R.layout.activity_all);
        Intent i=getIntent();
         ip_address=i.getStringExtra("ip");
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WebView webView = (WebView) findViewById(R.id.webview);

        Context context = this.getApplicationContext();
        builder3 = new AlertDialog.Builder(AllActivity.this,R.style.AppTheme);
        view = getLayoutInflater().inflate(R.layout.vehicle, null);
        view1 = getLayoutInflater().inflate(R.layout.vehicle, null);
        cameraView = (SurfaceView) view1.findViewById(R.id.surface_view);

        textView = (TextView) view1.findViewById(R.id.text_view);
Thread th=new Thread()
{
    public void run()
    {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.i("MainActivity", "Detector dependencies are not yet available");
        } else {
            Log.i("MainActivity1", "Detector dependencies are not yet available");

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(100, 100)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(false)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(AllActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); ++i) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                String text=stringBuilder.toString();
                                text= text.replaceAll("[^a-zA-Z0-9 ]","");
                                textView.setText(text);
                            }
                        });
                    }
                }
            });
        }
    }
};
        th.start();
        builder3.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog3.cancel();

            }
        });
        builder3.setPositiveButton("Capture", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                vehicle = textView.getText().toString();
                webAppInterface.send_vehicle_number();
                dialog3.cancel();

            }
        });

        builder3.setView(view1);
        dialog3 = builder3.create();



       // String imei=telephonyManager.getDeviceId();
       // Toast.makeText(getApplicationContext(),"IMEI"+imei,Toast.LENGTH_SHORT).show();

        webView.loadUrl(ip_address);
        webAppInterface= new WebAppInterface(webView,AllActivity.this);
        webView.addJavascriptInterface(webAppInterface, "AndroidInterface"); // To call methods in Android from using js in the html, AndroidInterface.showToast, AndroidInterface.getAndroidVersion etc
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setUserAgentString("my-user-agent");
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);

        webSettings.setAppCachePath("");
        webSettings.setGeolocationEnabled(true);
        webSettings.setSaveFormData(false);


            webSettings.getAllowContentAccess();
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new MyWebChromeClient());
        //TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

         //String device_id = tm.
        //Toast.makeText(getApplicationContext(),"IMEI:"+device_id,Toast.LENGTH_SHORT).show();

    }


    //
//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            //Calling a javascript function in html page
//            //view.loadUrl("javascript:alert(showVersion('called by Android'))");
//        }
//    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            result.confirm();
            return true;
        }

    }
    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(AllActivity.this);
        builder.setMessage("Are you sure want to exit POS ?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Close!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}

