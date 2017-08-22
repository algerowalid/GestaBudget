package com.example.eurequat_algerie.gestabudget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eurequat_algerie.gestabudget.Connexion.DatabaseManager;
import com.example.eurequat_algerie.gestabudget.Element_list.Adapter_detail;
import com.example.eurequat_algerie.gestabudget.Element_list.Adapter_tache;
import com.example.eurequat_algerie.gestabudget.Element_list.detail;
import com.example.eurequat_algerie.gestabudget.Element_list.tache;
import com.example.eurequat_algerie.gestabudget.mes_ressources.tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Activity_Tache_detail extends AppCompatActivity {
    DatabaseManager d= new DatabaseManager(this);
    Context context =Activity_Tache_detail.this;
    TextView entre;
    public String acti;
    TextView dep;
    TextView res;
    int POS=-1;
    AlertDialog DetailalertDialog;
    ArrayList<detail> mylist ;
    public ListView listV;
    public ListView listB;
    public double tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tache_detail);
         entre=(TextView)findViewById(R.id.entre_dargent);
         dep=(TextView)findViewById(R.id.depense);
         res=(TextView)findViewById(R.id.reste);
        listV =(ListView) findViewById(R.id.list_rta);
         String req1=" select sum(rt.total) as total from rta rt " +
                    " inner join action a on rt.id_action = a.id " +
                    " where rt.id_tache="+Activity_tache.idtache+" and a.flag_io="+0;
         String req2=" select sum(rt.total) as total from rta rt " +
                " inner join action a on rt.id_action = a.id " +
                " where rt.id_tache="+Activity_tache.idtache+" and a.flag_io="+1;
        d.open();
      Double en=d.getDouble(req1,"total");
      Double de=d.getDouble(req2,"total");
        d.close();
      entre.setText(String.valueOf(en));
      dep.setText(String.valueOf(de));
      Double rs=en-de;
      res.setText(String.valueOf(rs));
        fillingList();
        registerClickCallback();
    }
    public void ajout_action(View arg){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View popupView = getLayoutInflater().inflate(R.layout.pop_ajout_rta_action, null);
            final EditText desc = (EditText) popupView.findViewById(R.id.description);

        final EditText quant = (EditText) popupView.findViewById(R.id.quantit);
            final EditText prix = (EditText) popupView.findViewById(R.id.prix_unitaire);
            final TextView toota=(TextView)popupView.findViewById(R.id.toot);

            final Spinner spina = (Spinner) popupView.findViewById(R.id.spin_action);
            Button ajouter = (Button) popupView.findViewById(R.id.Ajouter);
            Button quitter = (Button) popupView.findViewById(R.id.quitter);



        quant.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

               if(quant.getText().toString().length()>0 && prix.getText().toString().length()>0){
                   double q = Double.parseDouble(quant.getText().toString());
                   double p = Double.parseDouble(prix.getText().toString());

                   double tt= q*p;

                   toota.setText(""+tt);
               }else{
                   toota.setText("0");
               }
            }
        });


        prix.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(quant.getText().toString().length()>0 && prix.getText().toString().length()>0){
                    double q = Double.parseDouble(quant.getText().toString());
                    double p = Double.parseDouble(prix.getText().toString());

                    tt= q*p;

                    toota.setText(""+tt);
                }else{
                    toota.setText("0");
                }
            }
        });

        Fill_spinner(spina);


            builder.setView(popupView);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);

            ajouter.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String de = desc.getText().toString();
                    String qua = quant.getText().toString();
                    String p = prix.getText().toString();
                    String act = spina.getSelectedItem().toString();
                    String[] ab = act.split(Pattern.quote("."));
                    int ida = Integer.parseInt(ab[0]);
                    acti = ab[1];
                    String da = tools.setCurrentDate();





                    d.open();
                    d.addRta(Activity_tache.idtache, ida, de, da, Double.valueOf(qua), Double.valueOf(p),tt);
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

        mylist = new ArrayList<detail>();
        d.open();
        Cursor c  = d.getAllRTA();
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DatabaseManager.KEY_ID));
                int idd=c.getInt(c.getColumnIndex(DatabaseManager.KEY_ID_ACTION));
                String des= c.getString(c.getColumnIndex(DatabaseManager.KEY_DESCRIPTION));
                String date= c.getString(c.getColumnIndex(DatabaseManager.KEY_DATE));
                String qn= c.getString(c.getColumnIndex(DatabaseManager.KEY_QNT));
                String p= c.getString(c.getColumnIndex(DatabaseManager.KEY_PU));
                String to= c.getString(c.getColumnIndex(DatabaseManager.KEY_TOTAL));
                String req="select designation from action where id="+idd;
                String desig=d.getString(req,"designation");
                detail t =new detail(id,desig,date,des,Double.valueOf(qn),Double.valueOf(p),Double.valueOf(to));
                mylist.add(t);

            } while (c.moveToNext());
            c.close();
        }

        if(mylist.size()>0) {

            listV.setAdapter(new Adapter_detail(context, mylist));
        }

        d.close();
    }
    public void mod_rta(View arg){
        if(POS !=-1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View popupView = getLayoutInflater().inflate(R.layout.pop_ajout_rta_action, null);
            final EditText desc = (EditText) popupView.findViewById(R.id.description);
            final EditText quant = (EditText) popupView.findViewById(R.id.quantit);
            final EditText prix = (EditText) popupView.findViewById(R.id.prix_unitaire);
            final EditText tot = (EditText) popupView.findViewById(R.id.total);
            final Spinner spina = (Spinner) popupView.findViewById(R.id.spin_action);
            Button ajouter = (Button) popupView.findViewById(R.id.Ajouter);
            Button quitter = (Button) popupView.findViewById(R.id.quitter);
            Fill_spinner(spina);
            desc.setText(mylist.get(POS).getDescritpion());
            quant.setText(String.valueOf(mylist.get(POS).getQuant()));
            prix.setText(String.valueOf(mylist.get(POS).getPrixu()));
            tot.setText(String.valueOf(mylist.get(POS).getTotal()));
            builder.setView(popupView);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);

            ajouter.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String de = desc.getText().toString();
                    String qua = quant.getText().toString();
                    String p = prix.getText().toString();
                    String t = tot.getText().toString();
                    String act = spina.getSelectedItem().toString();
                    String[] ab = act.split(Pattern.quote("."));
                    int ida = Integer.parseInt(ab[0]);
                    acti = ab[1];
                    String da = tools.setCurrentDate();
                    d.open();
                    int id = mylist.get(POS).getId();
                    d.modRta(id, Activity_tache.idtache, ida, de, Double.valueOf(qua), Double.valueOf(p), Double.valueOf(t));
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
        } else{
            Toast.makeText(getApplicationContext(), "Il faut séléctionner une détail !" , Toast.LENGTH_LONG).show();

        }


    }
    public void sup_rta(View arg){
        if(POS !=-1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View popupView = getLayoutInflater().inflate(R.layout.pop_confirmation, null);

            final TextView msg = (TextView) popupView.findViewById(R.id.msg1);


            Button ajouter = (Button) popupView.findViewById(R.id.oui);
            Button quitter = (Button) popupView.findViewById(R.id.non);


            msg.setText("Veuillez confirmer la supression de la détail " + mylist.get(POS).getDescritpion());


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
                    d.SuppRta(id);
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
        } else{
            Toast.makeText(getApplicationContext(), "Il faut séléctionner une détail !" , Toast.LENGTH_LONG).show();

        }

    }
    private void registerClickCallback(){

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, final int position, long id) {


                POS =position;


            }
        });
    }
    public void Fill_spinner(Spinner spina){


        ArrayList<String> listaction=new ArrayList<String>();
        d.open();

        String req ="select id, designation from action where flag_supp=0";
        Cursor c =d.getCurs(req);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DatabaseManager.KEY_ID));
                String nm= c.getString(c.getColumnIndex(DatabaseManager.KEY_DESIGNATION));

                String  result = id+"."+nm;
                listaction.add(result);

            } while (c.moveToNext());
            c.close();
        }


        if(listaction.size()>0) {

            ArrayAdapter adapter3 = new ArrayAdapter(Activity_Tache_detail.this, android.R.layout.simple_list_item_1, listaction);
            spina.setAdapter(adapter3);

        }
        d.close();


    }
    public void renit(View arg){
        Intent t2 = new Intent(Activity_Tache_detail.this, Activity_Tache_detail.class);
        startActivity(t2);
    }
    public void recherche(View arg){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = getLayoutInflater().inflate(R.layout.pop_recherche_detail, null);
        final EditText desc = (EditText) popupView.findViewById(R.id.description);
        final EditText dat = (EditText) popupView.findViewById(R.id.date);
        final EditText tot = (EditText) popupView.findViewById(R.id.total);
        final Spinner spina = (Spinner) popupView.findViewById(R.id.spin_action);
        Button ajouter = (Button) popupView.findViewById(R.id.Ajouter);
        Button quitter = (Button) popupView.findViewById(R.id.quitter);
        Fill_spinner(spina);
        builder.setView(popupView);
        DetailalertDialog = builder.create();
        DetailalertDialog.show();
        DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        DetailalertDialog.setCanceledOnTouchOutside(false);
        ajouter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String de = desc.getText().toString();
               String da = dat.getText().toString();
                String t = tot.getText().toString();
                String act = spina.getSelectedItem().toString();
                String[] ab = act.split(Pattern.quote("."));
                int ida = Integer.parseInt(ab[0]);
                acti = ab[1];
                String req=" select * from rta rt "+
                           " inner join action a on rt.id_action=a.id " +
                           " where rt.id_tache="+Activity_tache.idtache;
                String condition= "";
                if(de.trim().length()>0){
                    condition = " rt.description like '%"+de+"%'";
                }

               if(da.trim().length()>0){
                    if(condition.length()>0){
                    condition = condition +" and rt.date like '%"+da+"%'";
                }else{
                        condition = " rt.date like '%"+da+"%'";
                    }}
                if(t.trim().length()>0){
                    if(condition.length()>0){
                        condition = condition +" and rt.total like '%"+t+"%'";
                    }else{
                        condition = " rt.total like '%"+t+"%'";
                    }}
                if(acti.trim().length()>0){
                    if(condition.length()>0){
                        condition = condition +" and a.designation like '%"+acti+"%'";
                    }else{
                        condition = " a.designation like '%"+acti+"%'";
                    }}
                if(condition.length()>0){
                    req = req +" and "+condition;


                }else{
                     req=" select * from rta rt "+
                            " inner join action a on rt.id_action=a.action " +
                            " where rt.id_tache="+Activity_tache.idtache;
                   }

                   mylist = new ArrayList<detail>();
                d.open();
                Cursor c  = d.getCurs(req);
                if (c.moveToFirst()) {
                    do {
                        int id = c.getInt(c.getColumnIndex(DatabaseManager.KEY_ID));
                        int idd=c.getInt(c.getColumnIndex(DatabaseManager.KEY_ID_ACTION));
                        String des= c.getString(c.getColumnIndex(DatabaseManager.KEY_DESCRIPTION));
                        String date= c.getString(c.getColumnIndex(DatabaseManager.KEY_DATE));
                        String qn= c.getString(c.getColumnIndex(DatabaseManager.KEY_QNT));
                        String p= c.getString(c.getColumnIndex(DatabaseManager.KEY_PU));
                        String to= c.getString(c.getColumnIndex(DatabaseManager.KEY_TOTAL));
                        String req1="select designation from action where id="+idd;
                        String desig=d.getString(req,"designation");
                        detail b =new detail(id,desig,date,des,Double.valueOf(qn),Double.valueOf(p),Double.valueOf(to));
                        mylist.add(b);

                    } while (c.moveToNext());
                    c.close();
                }

                DetailalertDialog.dismiss();
                listV.setAdapter(new Adapter_detail(context, mylist));



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
}
