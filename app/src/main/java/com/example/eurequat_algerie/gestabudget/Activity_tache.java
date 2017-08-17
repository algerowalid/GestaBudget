package com.example.eurequat_algerie.gestabudget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eurequat_algerie.gestabudget.Connexion.DatabaseManager;
import com.example.eurequat_algerie.gestabudget.Element_list.Adapter_tache;
import com.example.eurequat_algerie.gestabudget.Element_list.tache;
import com.example.eurequat_algerie.gestabudget.mes_ressources.tools;

import java.util.ArrayList;
import java.util.List;

public class Activity_tache extends AppCompatActivity {
    DatabaseManager d= new DatabaseManager(this);
    Context context =Activity_tache.this;
    public ListView listV;
    TextView nbr;
    TextView de;
    TextView totaleV;
    AlertDialog DetailalertDialog;
    public int POS=-1;
    ArrayList<tache> mylist ;
    Spinner spine;
    public static int idtache;
     @Override
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache);
        listV =(ListView) findViewById(R.id.listev);
        nbr =(TextView) findViewById(R.id.nombre);
        totaleV=(TextView)findViewById(R.id.montantgv);
        de=(TextView)findViewById(R.id.montantgd);
        String req_nbr="select count(id) as id from tache";
        String req1=" select sum(rt.total) as total from rta rt " +
                 " inner join action a on rt.id_action = a.id " +
                 " where a.flag_io="+0;
        String req2=" select sum(rt.total) as total from rta rt " +
                 " inner join action a on rt.id_action = a.id " +
                 " where a.flag_io="+1;
        d.open();
        int res=d.getInt(req_nbr,"id");
        double totlV=d.getDouble(req1,"total");
        double dep=d.getDouble(req2,"total");
        d.close();
        nbr.setText("Vous avez " +res+ " Tache(s)");
        totaleV.setText("Vous avez " +totlV+ " Total verser");
        de.setText("Vous avez " +dep+ " Total dépenser");
        fillingList();
        registerClickCallback();

    }
  public void ajoute(View arg){

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      View popupView = getLayoutInflater().inflate(R.layout.pop_ajout_tache, null);
      spine = (Spinner) popupView.findViewById(R.id.spin);
      List<String> spinnerArray =  new ArrayList<String>();
      spinnerArray.add("Par temps");
      spinnerArray.add("chiffre d'affaire");
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spine.setAdapter(adapter);
              final EditText nm=(EditText) popupView.findViewById(R.id.nom);
              final EditText inf=(EditText) popupView.findViewById(R.id.info);
              final EditText des=(EditText) popupView.findViewById(R.id.description);
              final int a = spine.getSelectedItemPosition();
              Button ajouter=(Button)popupView.findViewById(R.id.Ajouter);
              Button quitter=(Button)popupView.findViewById(R.id.quitter);
      builder.setView(popupView);
      DetailalertDialog = builder.create();

      DetailalertDialog.show();
      DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
      DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
      DetailalertDialog.setCanceledOnTouchOutside(false);

           ajouter.setOnClickListener(new Button.OnClickListener() {
          @Override
          public void onClick(View v) {

               String n= nm.getText().toString().replaceAll("'","''");
              String i=inf.getText().toString().replaceAll("'","''");
              String de=des.getText().toString().replaceAll("'","''");
              String da= tools.setCurrentDate();

             // int ren =  Integer.parseInt(rent.getText().toString());

              d.open();

            d.addTache(n, i, de, da, a);
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
  public void fillingList(){

        mylist = new ArrayList<tache>();
        d.open();
        Cursor c  = d.getAllTaches();
         if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DatabaseManager.KEY_ID));
                String nm= c.getString(c.getColumnIndex(DatabaseManager.KEY_NOM));
                String inf= c.getString(c.getColumnIndex(DatabaseManager.KEY_INFO));
                String des= c.getString(c.getColumnIndex(DatabaseManager.KEY_DESCRIPTION));
                String date= c.getString(c.getColumnIndex(DatabaseManager.KEY_DATE_DEBUT));

                int ren = c.getInt(c.getColumnIndex(DatabaseManager.KEY_RENATABILITE));

                tache t = new tache(id,nm,inf,des,date,ren);
                mylist.add(t);

            } while (c.moveToNext());
            c.close();
        }

        if(mylist.size()>0) {

            listV.setAdapter(new Adapter_tache(context, mylist));
        }

        d.close();
    }
  private void registerClickCallback(){

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, final int position, long id) {


                POS =position;


            }
        });
    }
  public void Mod(View arg) {
if(POS !=-1) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    View popupView = getLayoutInflater().inflate(R.layout.pop_ajout_tache, null);

    final EditText nm = (EditText) popupView.findViewById(R.id.nom);
    final EditText inf = (EditText) popupView.findViewById(R.id.info);
    final EditText des = (EditText) popupView.findViewById(R.id.description);
    spine = (Spinner) popupView.findViewById(R.id.spin);
    List<String> spinnerArray =  new ArrayList<String>();
    spinnerArray.add("Par temps");
    spinnerArray.add("chiffre d'affaire");
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spine.setAdapter(adapter);
    Button ajouter = (Button) popupView.findViewById(R.id.Ajouter);
    Button quitter = (Button) popupView.findViewById(R.id.quitter);
    nm.setText(mylist.get(POS).getNom());
    inf.setText(mylist.get(POS).getInfo());
    des.setText(mylist.get(POS).getDescription());
    builder.setView(popupView);
    DetailalertDialog = builder.create();
    DetailalertDialog.show();
    DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    DetailalertDialog.setCanceledOnTouchOutside(false);

    ajouter.setOnClickListener(new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            String n = nm.getText().toString().replaceAll("'","''");
            String i = inf.getText().toString().replaceAll("'","''");
            String de = des.getText().toString().replaceAll("'","''");
            int a = spine.getSelectedItemPosition();

            d.open();

            int id = mylist.get(POS).getId();
            d.modTache(id, n, i, de, a);
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
    Toast.makeText(getApplicationContext(), "Il faut séléctionner une tache!" , Toast.LENGTH_LONG).show();

}

}
  public void Supp(View arg) {
if (POS !=-1) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    View popupView = getLayoutInflater().inflate(R.layout.pop_confirmation, null);

    final TextView msg = (TextView) popupView.findViewById(R.id.msg1);


    Button ajouter = (Button) popupView.findViewById(R.id.oui);
    Button quitter = (Button) popupView.findViewById(R.id.non);


    msg.setText("Veuillez confirmer la supression de la tache " + mylist.get(POS).getNom());


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
            d.SuppTache(id);
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

}  else{
    Toast.makeText(getApplicationContext(), "Il faut séléctionner une tache!" , Toast.LENGTH_LONG).show();

}
    }
  public void Finaliser_Tache(View arg){
      if(POS !=-1){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    View popupView = getLayoutInflater().inflate(R.layout.pop_confirmation, null);

    final TextView msg=(TextView) popupView.findViewById(R.id.msg1);


    Button ajouter=(Button)popupView.findViewById(R.id.oui);
    Button quitter=(Button)popupView.findViewById(R.id.non);


    msg.setText("Veuillez vous confirmer la finalisation de la tache "+mylist.get(POS).getNom());



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
            String da= tools.setCurrentDate();
            int id = mylist.get(POS).getId();
            d.FinaliseTache(id, da);
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
    });} else{
          Toast.makeText(getApplicationContext(), "Il faut séléctionner une tache!" , Toast.LENGTH_LONG).show();

      }
}
  public void Renit(View arg){
        Intent t2 = new Intent(Activity_tache.this, Activity_tache.class);
        startActivity(t2);
    }
  public void detail(View arg){
    if(POS != -1){
        idtache = mylist.get(POS).getId();
        Intent t2 = new Intent(Activity_tache.this, Activity_Tache_detail.class);
        startActivity(t2);
    }
    else{
        Toast.makeText(getApplicationContext(), "Il faut séléctionner une tache!" , Toast.LENGTH_LONG).show();
    }
}
}

