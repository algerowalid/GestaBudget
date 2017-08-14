package com.example.eurequat_algerie.gestabudget.Connexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 21/09/2015.
 */
public class MySQLite extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "innodev.sqlite";
    private static final int DATABASE_VERSION =1;

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Cr?ation de la base de donn?es
        // on ex?cute ici les requ?tes de cr?ation des tables
       sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_ACTION); // création table "employe"
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_TACHE); // création table "client"
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_RTA); // création table "client"
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_PARAMETRE); // création table "client"
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Mise ? jour de la base de donn?es
        // m?thode appel?e sur incr?mentation de DATABASE_VERSION
        // on peut faire ce qu'on veut ici, comme recr?er la base :
        onCreate(sqLiteDatabase);
    }
}
