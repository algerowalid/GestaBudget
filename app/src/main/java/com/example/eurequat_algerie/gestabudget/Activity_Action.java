package com.example.eurequat_algerie.gestabudget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eurequat_algerie.gestabudget.Connexion.DatabaseManager;
import com.example.eurequat_algerie.gestabudget.Element_list.Adapter_action;
import com.example.eurequat_algerie.gestabudget.Element_list.action;
import com.example.eurequat_algerie.gestabudget.Element_list.tache;

import java.util.ArrayList;
import java.util.List;

public class Activity_Action extends AppCompatActivity {

    DatabaseManager d= new DatabaseManager(this);
    Context context =Activity_Action.this;

    public ListView list;
    TextView nbr;
    AlertDialog DetailalertDialog;

    int POS;

    ArrayList<action> mylist ;


    EditText nm;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__action);

        list=  (ListView) findViewById(R.id.list);
    nm = (EditText) findViewById(R.id.designation);
    spin = (Spinner) findViewById(R.id.spinner);


        // Remplir le spinner par les deux valeurs possibles
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Entrée d'argent");
        spinnerArray.add("Dépense");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);


        fillingList();
        registerClickCallback();

    }




    public void fillingList(){

        mylist = new ArrayList<action>();
        d.open();
        Cursor c  = d.getAllActions();
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DatabaseManager.KEY_ID));
                String nm= c.getString(c.getColumnIndex(DatabaseManager.KEY_DESIGNATION));
                int inf = c.getInt(c.getColumnIndex(DatabaseManager.KEY_FLAG_IO));

                action t = new action(id,nm,inf);
                mylist.add(t);
            } while (c.moveToNext());
            c.close();
        }

        if(mylist.size()>0) {

            list.setAdapter(new Adapter_action(context, mylist));
        }

        d.close();
    }

    private void registerClickCallback(){

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, final int position, long id) {
                POS =position;
                nm.setText(mylist.get(POS).getDesignation());
                if(mylist.get(POS).getFlag()==0){
                    spin.setSelection(0);
                }else{
                    spin.setSelection(1);
                }

            }
        });
    }



    public void ajout (View arg){

        if(nm.getText().toString().trim().length()>0){

            String des =  nm.getText().toString().replaceAll("'","''");
            int a = spin.getSelectedItemPosition();

            Log.i("information",a+"  "+des);
            d.open();
            d.addAction(des,a);
            d.close();
            nm.setText("");
            fillingList();

        }else{

            Toast.makeText(context,"Veuillez saisir une action à ajouter",Toast.LENGTH_LONG).show();
        }

    }


    public void modif(View arg){

        if(POS>0) {
            if (nm.getText().toString().trim().length() > 0) {

                String des = nm.getText().toString().replaceAll("'", "''");
                int a = spin.getSelectedItemPosition();
                int id =mylist.get(POS).getId();
                d.open();
                d.modAction(id,des, a);
                d.close();
                nm.setText("");
                fillingList();

            } else {

                Toast.makeText(context, "Veuillez saisir une désignation à ajouter", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(context, "Veuillez sélectionner une action à modifier", Toast.LENGTH_LONG).show();
        }

    }

    public void supp(View arg){

        if(POS>0) {


                int id =mylist.get(POS).getId();
                d.open();
                d.SuppAction(id);
                d.close();
                nm.setText("");
                fillingList();


        }else{
            Toast.makeText(context, "Veuillez sélectionner une action à supprimer", Toast.LENGTH_LONG).show();
        }

    }

    public void reinit(View art){

        this.finish();

            Intent launchactivity = new Intent(Activity_Action.this, Activity_Action.class);
            startActivity(launchactivity);

    }



}
