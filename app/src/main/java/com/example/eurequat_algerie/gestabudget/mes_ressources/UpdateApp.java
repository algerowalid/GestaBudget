package com.example.eurequat_algerie.gestabudget.mes_ressources;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateApp extends AsyncTask<String,Void,Void> {
    private Context context;
    public String lien ;
    ProgressDialog progressDialog;

    public void setContext(Context context, String li){
        this.context = context;
        this.lien = li;
    }

  /*  protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Téléchargement de la derniére version","Veuillez patienter S.V.P...",false,false);
    }
*/



    @Override
    protected Void doInBackground(String... arg0) {
        try {
            URL url = new URL(lien);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String PATH = "/mnt/sdcard/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "app-debug.apk");
            if(outputFile.exists()){
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/Download/app-debug.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            context.startActivity(intent);


        } catch (Exception e) {
            Log.i("VersionUpdateError", "Update error! " + e.getMessage());
        }
        return null;
    }


    protected void onPostExecute(Void v) {
      //  progressDialog.dismiss();

        Log.i("VersionAffichage","Me here");

    }


}