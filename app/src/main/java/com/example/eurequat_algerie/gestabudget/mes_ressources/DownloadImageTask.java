package com.example.eurequat_algerie.gestabudget.mes_ressources;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String,Void, Bitmap> {
    ImageView bmImage;
    String lien ="";
    public DownloadImageTask(ImageView bmImage, String a ) {
        this.bmImage = bmImage;
        this.lien = a;
    }



    protected Bitmap doInBackground(String... urls) {
        String urldisplay = lien;
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            if(in.available()>0) {
                mIcon11 = BitmapFactory.decodeStream(in);
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }


    protected void onPostExecute(Bitmap result) {
        if(result!=null) {
            bmImage.setImageBitmap(result);
        }
        }
}