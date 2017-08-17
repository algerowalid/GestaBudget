package com.example.eurequat_algerie.gestabudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }
    public void tst(View arg){
        Intent NewIntent = new Intent(Activity_accueil.this,Activity_tache.class);
        startActivity(NewIntent);
    }

    public void para(View arg){
        Intent NewIntent = new Intent(Activity_accueil.this,Activity_parametre.class);
    startActivity(NewIntent);
}


    public void go_action(View arg){
        Intent NewIntent = new Intent(Activity_accueil.this,Activity_Action.class);
        startActivity(NewIntent);
    }
}
