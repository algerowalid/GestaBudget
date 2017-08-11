package com.example.eurequat_algerie.gestabudget.mes_ressources;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Check_connection extends AsyncTask<String, Void, String> {

    private Context context;

    public Check_connection(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {

        int flag = Integer.parseInt(arg0[0]);
        String idvente = arg0[1];
        String date= arg0[2];
        String montant= arg0[3];
        String employe= arg0[4];


        String link = null;
        String data;
        BufferedReader bufferedReader;
        String result;


        try {
            if(flag==1){
            data = "?idvente=" + URLEncoder.encode(idvente, "UTF-8");
            data += "&date=" + URLEncoder.encode(date, "UTF-8");
            data += "&total=" + URLEncoder.encode(montant, "UTF-8");
            data += "&idemploye=" + URLEncoder.encode(employe, "UTF-8");
            data += "&idclient="+ URLEncoder.encode("1", "UTF-8");


            // link = "http://testmehdy.netne.net/signup.php"+data;
            link = "http://192.168.1.5/tiger/addVente.php" + data;
        }else{
                data = "?id=" + URLEncoder.encode(date, "UTF-8");
                data += "&idvente=" + URLEncoder.encode(idvente, "UTF-8");
                data += "&idproduit=" + URLEncoder.encode(montant, "UTF-8");
                data += "&remise=" + URLEncoder.encode(employe, "UTF-8");
                data += "&prix=" + URLEncoder.encode(arg0[5], "UTF-8");
                data += "&qnt=" + URLEncoder.encode(arg0[6], "UTF-8");

                // link = "http://testmehdy.netne.net/signup.php"+data;
                link = "http://192.168.1.5/tiger/addLienVente.php" + data;


            }

            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result.toString();
        Log.i("Mon JSON", jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully. Signup successfull.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}