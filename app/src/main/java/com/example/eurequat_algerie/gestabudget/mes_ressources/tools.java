package com.example.eurequat_algerie.gestabudget.mes_ressources;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Eurequat-Algerie on 25/04/2017.
 */
public class tools
{
    public static void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

   public static String setCurrentDate() {


        String date="";
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String dd="",mm="";
        if(day<10){
            dd="0"+day;
        }else{
            dd=""+day;
        }

        if((month+1)<10){
            mm="0"+(month+1);
        }else{
            mm=""+(month+1);
        }

        date = year + "-" + mm + "-" + dd;
        return date;
    }


    public static int TestLien(String lien){
        URL u ;
        HttpURLConnection huc = null;
        int code =0;
        try {
            u= new URL(lien);
            huc = (HttpURLConnection)u.openConnection();
            huc.setRequestMethod("HEAD");
            huc.connect() ;
            OutputStream os = huc.getOutputStream();
            code =huc.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("ErreurPage",""+code);
        return code;

    }

    public static boolean CheckDates(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        boolean b = false;
        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = true;  // If two dates are equal.
            } else {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    public static void ShowingCenterToast(Context context, String Message){
        Toast toast = Toast.makeText(context,Message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



}
