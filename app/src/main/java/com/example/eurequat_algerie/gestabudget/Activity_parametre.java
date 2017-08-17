package com.example.eurequat_algerie.gestabudget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eurequat_algerie.gestabudget.Connexion.DatabaseManager;
import com.example.eurequat_algerie.gestabudget.Element_list.Adapter_parametre;
import com.example.eurequat_algerie.gestabudget.Element_list.parametre;
import com.example.eurequat_algerie.gestabudget.Element_list.tache;

import java.util.ArrayList;

public class Activity_parametre extends AppCompatActivity {
    DatabaseManager d= new DatabaseManager(this);
    Context context =Activity_parametre.this;
    public ListView listP;
    ArrayList<parametre> mylist ;
    EditText nom;
    EditText val;
    AlertDialog DetailalertDialog;
    public int POS=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);
        nom=(EditText)findViewById(R.id.name);
        val=(EditText)findViewById(R.id.valeur);
        listP =(ListView) findViewById(R.id.liste_parametre);
        fillingList();
        registerClickCallback();
    }
    public void ajouter_parametre(View arg){
        String n=nom.getText().toString().replaceAll("'","''");
        String v=val.getText().toString().replaceAll("'","''");
        d.open();
        d.addParametre(n,v,0);
        d.close();
        fillingList();
    }
    public void modifier_parametre(View arg){
     if(POS !=-1) {





                    String i = val.getText().toString().replaceAll("'","''");




                    d.open();

                    int id = mylist.get(POS).getId();
                    d.modParametre(id, i);
                    d.close();
                    fillingList();

                }


        else{
            Toast.makeText(getApplicationContext(), "Il faut séléctionner un paramètre!" , Toast.LENGTH_LONG).show();

        }



    }
    public void supprimer_parametre(View arg){
        if(POS !=-1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View popupView = getLayoutInflater().inflate(R.layout.pop_confirmation, null);

            final TextView msg = (TextView) popupView.findViewById(R.id.msg1);


            Button ajouter = (Button) popupView.findViewById(R.id.oui);
            Button quitter = (Button) popupView.findViewById(R.id.non);


            msg.setText("Veuillez confirmer la supression de cette parametre " + mylist.get(POS).getNom());


            builder.setView(popupView);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);

            ajouter.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {


                    d.open();

                    int id = mylist.get(POS).getId();
                    d.SuppParametre(id);
                    d.close();
                    DetailalertDialog.dismiss();
                    fillingList();

                }
            });
            quitter.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    DetailalertDialog.dismiss();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Il faut séléctionner un parametre !" , Toast.LENGTH_LONG).show();

        }

    }
    public void renit_parametre(View arg){
        Intent t2 = new Intent(Activity_parametre.this, Activity_parametre.class);
        startActivity(t2);
    }
    public void fillingList(){

        mylist = new ArrayList<parametre>();
        d.open();
        Cursor c  = d.getAllParametres();
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DatabaseManager.KEY_ID));
                String nm= c.getString(c.getColumnIndex(DatabaseManager.KEY_NOM));
                String va= c.getString(c.getColumnIndex(DatabaseManager.KEY_VALEUR));


              parametre t = new parametre(id,nm,va);
                mylist.add(t);

            } while (c.moveToNext());
            c.close();
        }

        if(mylist.size()>0) {

            listP.setAdapter(new Adapter_parametre(context, mylist));
        }

        d.close();
    }
    private void registerClickCallback(){

        listP.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, final int position, long id) {


                POS =position;
                 nom.setText(mylist.get(POS).getNom());
                 nom.setEnabled(false);
                 val.setText(mylist.get(POS).getValeur());
            }
        });
    }

}
