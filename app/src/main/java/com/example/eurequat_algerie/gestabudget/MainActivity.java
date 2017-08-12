package com.example.eurequat_algerie.gestabudget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eurequat_algerie.gestabudget.Connexion.DatabaseManager;

public class MainActivity extends AppCompatActivity {

    DatabaseManager d = new DatabaseManager(this);
    Context context= MainActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String req = "select count(id) as id from parametre";
        d.open();
        int cnt = d.getInt(req,"id");
        d.close();

        if(cnt>0){
            this.finish();
            Intent NewIntent = new Intent(MainActivity.this,Activity_login.class);
            startActivity(NewIntent);
            
        }else{
            d.open();
            d.addParametre("login","admin",0);
            d.addParametre("password","admin",0);
            d.close();

            this.finish();
            Intent NewIntent = new Intent(MainActivity.this,Activity_login.class);
            startActivity(NewIntent);

        }
    }
}
