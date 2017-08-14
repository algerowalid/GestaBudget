package com.example.eurequat_algerie.gestabudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eurequat_algerie.gestabudget.Connexion.DatabaseManager;

public class Activity_login extends AppCompatActivity {

    DatabaseManager d= new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


public void toaction(View arg){
    EditText lg = (EditText) findViewById(R.id.name);
    EditText pass= (EditText) findViewById(R.id.password);

    String LOG = lg.getText().toString();
    String PASS= pass.getText().toString();


    String req_login="select count(id) as id from parametre where nom='login' and valeur='"+LOG+"'";
    String req_pass="select count(id) as id from parametre where nom='password' and valeur='"+PASS+"'";
    d.open();
    int A = d.getInt(req_login,"id");
    int B = d.getInt(req_pass,"id");
    d.close();


    if((A+B)==2){

        Intent NewIntent = new Intent(Activity_login.this,Activity_accueil.class);
        startActivity(NewIntent);

    }else{
        Toast.makeText(getApplicationContext(), "Votre compte n'existe pas !" , Toast.LENGTH_LONG).show();

    }

}




}
