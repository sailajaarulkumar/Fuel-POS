package com.sunmi.printerhelper.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.MainThread;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sunmi.printerhelper.utils.AidlUtil;

import org.json.JSONObject;

import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import woyou.aidlservice.jiuiv5.IWoyouService;

public class WebAppInterface extends BaseActivity {
    Context mContext;
    Boolean sunmi = true;
    private static final String SERVICE＿PACKAGE = "woyou.aidlservice.jiuiv5";
    private static final String SERVICE＿ACTION = "woyou.aidlservice.jiuiv5.IWoyouService";
    public static String vehic;

    Activity activity;
    WebView web_View;
    Dialog dialog;
    private Toast mToastToShow;
    public static String id;
    private IWoyouService woyouService;

    // Instantiate the interface and set the context
    public WebAppInterface(WebView webView, Context c) {
        mContext = c;
        activity = (Activity) c;
        web_View = webView;
    }

    // Show a toast from the web page
    @JavascriptInterface
    public void showToast(String send, String b) throws RemoteException {
        AidlUtil.getInstance().printLeft(send, true, false);
        AidlUtil.getInstance().printLeft(b, false, true);
        AidlUtil.getInstance().print3Line();
    }

    @JavascriptInterface
    public String get_mac_address() {
        String mac_address= getMacAddr();
        Log.i("mac address:",mac_address);
        return mac_address;
    }
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
    @JavascriptInterface
    public void showAndroidVersion(String versionName) {
        Toast.makeText(mContext, versionName, Toast.LENGTH_SHORT).show();
    }
    @JavascriptInterface
    public String print_check(String toast) {
        float s= (float) 20.0;
        final AllActivity allActivity = new AllActivity();
        final Boolean[] shouldReturn = {false};
        int i=1;



return "";

    }

    @JavascriptInterface
    public void print_check1(String _id) {
        final AllActivity allActivity = new AllActivity();
        final android.app.AlertDialog dialog = AllActivity.dialog3;
        id = _id;
        if(id.equals("none")) {
            runOnUiThread(new Runnable() {

             @Override
             public void run() {

           dialog.show();
           dialog.cancel();

        }
    });
}
else {

    Toast.makeText(mContext, "Loading Sensor",
            Toast.LENGTH_SHORT).show();

    runOnUiThread(new Runnable() {

        @Override
        public void run() {

            dialog.show();

            Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button btnNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
            layoutParams.weight = 10;
            btnPositive.setLayoutParams(layoutParams);
            btnNegative.setLayoutParams(layoutParams);

        }
    });

}


    }
    @JavascriptInterface
    public void sunmi() {

        final String webUrl= "javascript:sunmi_check('" +sunmi+ "')";
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                web_View.loadUrl(webUrl);

            }
        });

    }

    public void send_vehicle_number(){
        String vehicle_num= AllActivity.textView.getText().toString();
        Log.i("idd"," "+id);
        final String webUrl= "javascript:post_vehicle_number('" +vehicle_num+"','"+id+ "')";
        if (!activity.isFinishing())
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    web_View.loadUrl(webUrl);
                }
            });
    }
    @JavascriptInterface
    public void print_header(String[] toast) throws RemoteException {
        float s= (float) 20.0;
       AidlUtil.getInstance().print_left_right(new String[]{"GSTIN:" + toast[0], "TIN:" + toast[1]},s, false, false);
       AidlUtil.getInstance().print_left_right(new String[]{"B.No:" + toast[2], toast[3]},s, false, false);
        AidlUtil.getInstance().printText(toast[4],s, false, false,0);

    }

    @JavascriptInterface
    public void send_receipt(String jsonData) throws RemoteException {
        try{
            JSONObject data = new JSONObject(jsonData);
            String username = data.getString("name");
            String password = data.getString("subtotal");


            Log.i("TAG",username + " - " + password );

        }catch (Exception ex){
            Log.i("TAG","error : " + ex);
        }

    }

    @JavascriptInterface
    public void print_toast(String msg) throws RemoteException {

        int toastDurationInMilliSeconds = 1000;
        mToastToShow =Toast.makeText(mContext,msg+" is added to the cart",Toast.LENGTH_SHORT);
        mToastToShow.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mToastToShow.cancel();
            }
        }, 500);
    }

    @JavascriptInterface
    public void print_company_details(String[] toast) throws RemoteException {
        float s1=(float) (21.5);
        float s= (float) 20.0;
        AidlUtil.getInstance().printText(toast[0],s1, true, false,0);
        AidlUtil.getInstance().printText(toast[1],s, false, false,0);
    }


    @JavascriptInterface
    public void print_customer_details(String[] toast) throws RemoteException {
        float s = (float) 20.5;
        boolean print=false;
        Log.i("GSTIN", "Value:" );
Boolean frompos=false;
        if ( toast[0] !=null) {
            String name=toast[0];
            Log.i("GSTIN", "Value:" +toast[0].length());
            AidlUtil.getInstance().print_left_right(new String[]{"M/S:" + toast[0], "V.No:" + toast[1]}, s, false, false);

            if (toast[2] !=null && !(toast[2].isEmpty())) {
                if(frompos.equals("true")){
                    print=true;
                    AidlUtil.getInstance().print_left_right(new String[]{"GSTIN:" + toast[2], "credit"}, s, false, false);
        }
        else{
                    AidlUtil.getInstance().printText("GSTIN:" + toast[2], s, false, false, 0);

                }
            }
if(frompos.equals("true") && print==false) {
    AidlUtil.getInstance().printText("credit", s, true, false, 0);
}
            AidlUtil.getInstance().printText(toast[3], s, false, false, 0);
        }
        }




    @JavascriptInterface
    public void print_product_details(String[] line) throws RemoteException {
        float s= (float) 20.0;
        float s1=(float) 21.5;
        AidlUtil.getInstance().print_left_right(new String[]{"PARTICULARS[HSN]", "AMOUNT" },s1, false, false);
        for (int i = 0; i < line.length; i++) {
           AidlUtil.getInstance().printText(line[i],s1, false, false,0);
       }

    }


    @JavascriptInterface
    public void print_tax_details(String[] line) throws RemoteException {
        float s= (float) 20.0;
        float s1=(float) 21.5;

        for (int i = 0; i < line.length; i++) {
            AidlUtil.getInstance().printText(line[i],s1, false, false,0);
        }

    }
    @JavascriptInterface
    public void print_total(String[] line) throws RemoteException {
        float s= (float) 20.0;
        float s1=(float) 21.5;
        AidlUtil.getInstance().printText(line[0],s1, false, false,0);

        AidlUtil.getInstance().print_left_right(new String[]{"TOTAL" ,line[1]},s1, true, false);
        AidlUtil.getInstance(). print3Line();

    }
    @JavascriptInterface
    private void sendData(String send) throws RemoteException {
        float s= (float) 20.0;
        Log.i("sendsuc","send");
//        for (int i = 0; i < send.length; i++) {
//            AidlUtil.getInstance().printText(send[i],s, false, false,1);
//        }
//        return;
         //AidlUtil.getInstance().printLeft(send);
        AidlUtil.getInstance().printText(send,s, false, false,0);
         //AidlUtil.getInstance(). printRight(b);
        //return;
    }
    //sales details printing
    @JavascriptInterface
    public void print_sales_details_company_part(String company_name,String date,String cashier,String s_bill_no,String e_bill_no) throws RemoteException {
        float s= (float) 22;
        try{
            //JSONObject data = new JSONObject(jsonData);
            //String username = data.getString("name");
            //String password = jsonData.getString("company");

            AidlUtil.getInstance().printText(company_name,s, true, false,1);
            AidlUtil.getInstance().printText(date,s, true, false,1);
            AidlUtil.getInstance().printText("Cashier:"+cashier,s, false, false,1);
            AidlUtil.getInstance().printText("Starting Bill_no:" + s_bill_no,s, false, false,0);
            AidlUtil.getInstance().printText("Ending Bill_no:" + e_bill_no,s, false, false,0);



        }catch (Exception ex){
            Log.i("TAG","error : " + ex);
        }

    }

    @JavascriptInterface
    public void print_sales_details_total_part(String line,String total) throws RemoteException {
        float s= (float) 20.0;
        float s1=(float) 21.5;

        AidlUtil.getInstance().printText(line,s, false, false,0);
        AidlUtil.getInstance().print_left_right(new String[]{"TOTAL" ,total},s1, true, false);
        AidlUtil.getInstance().printText(line,s, false, false,0);
    }
    @JavascriptInterface
    public void print_sales_details_payment_part(String[] payments) throws RemoteException {
        float s= (float) 20.0;
        float s1=(float) 21.5;

        AidlUtil.getInstance().printText("PAYMENTS:",s1, true, false,0);
        for (int i = 0; i < payments.length; i++) {
            AidlUtil.getInstance().printText(payments[i],s1, false, false,0);
        }

        AidlUtil.getInstance(). print3Line();
        AidlUtil.getInstance(). print3Line();
    }
    @JavascriptInterface
    public void print_sales_details_product_part(String line,String title,String[] products) throws RemoteException {
        float s= (float) 20.0;
        float s1=(float) 21.5;
        Log.i("Session report","Product part");
        AidlUtil.getInstance().printText(line,s, false, false,0);
        AidlUtil.getInstance().printText("PRODUCTS:",s1, true, false,0);
        AidlUtil.getInstance().printText(title,s, true, false,0);
        for (int i = 0; i < products.length; i++) {
            AidlUtil.getInstance().printText(products[i],s1, false, false,0);
        }

    }
    @JavascriptInterface
    public void print_sales_details_tax_part(String line,String[] payments) throws RemoteException {
        float s= (float) 20.0;
        float s1=(float) 21.5;

        AidlUtil.getInstance().printText(line,s, false, false,0);
        AidlUtil.getInstance().printText("TAXES:",s1, true, false,0);
        for (int i = 0; i < payments.length; i++) {
            AidlUtil.getInstance().printText(payments[i],s1, false, false,0);
        }

    }

    @JavascriptInterface
    public void print_sales_details_cancel_part(String title,String[] cancelled_bill_no,String line) throws RemoteException {
        float s = (float) 20.0;
        float s1 = (float) 21.5;
        if (cancelled_bill_no.length > 0) {

            AidlUtil.getInstance().printText("CANCELLED BILLS:", s1, true, false, 0);
            AidlUtil.getInstance().printText(title, s, true, false, 0);
            for (int i = 0; i < cancelled_bill_no.length; i++) {
                String padded = String.format("%1$-20s", cancelled_bill_no[i]);
                //String res= String.format("%15s", formatter.format(cancelled_bill_no[i]));
                //AidlUtil.getInstance().print_left_right(new String[]{"" ,cancelled_bill_no[i]},s1, false, false);
                AidlUtil.getInstance().printText(cancelled_bill_no[i], s1, false, false, 0);
            }
            AidlUtil.getInstance().printText(line, s, false, false, 0);


        }
    }
}
