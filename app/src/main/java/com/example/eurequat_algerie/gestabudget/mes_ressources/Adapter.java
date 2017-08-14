package com.example.eurequat_algerie.gestabudget.mes_ressources;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.eurequat_algerie.gestabudget.Connexion.DatabaseManager;

/**
 * Created by HP on 8/14/2017.
 */

public class Adapter {
    SQLiteDatabase database_ob;
    DatabaseManager helper;
    Context ccontext;
    public Adapter(Context context)
    {
        ccontext=context;
    }
    public Adapter openToWrite() throws SQLException {
        helper=new DatabaseManager(ccontext);
       // database_ob=helper.getWritableDatabase();
        return this;}
    public void Close(){
        database_ob.close();
    }

}
