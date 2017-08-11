package com.example.eurequat_algerie.gestabudget.Connexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    public static boolean FlgDBR = true;


    public static final String TABLE_TACHE = "tache";
    public static final String TABLE_ACTION= "action";
    public static final String TABLE_RTA= "rta";
    public static final String TABLE_PARAMETRE= "parametre";




    //Colonne
        // tache
    public static final String KEY_ID= "id";
    public static final String KEY_NOM= "nom";
    public static final String KEY_INFO= "info";
    public static final String KEY_DESCRIPTION= "description";
    public static final String KEY_DATE_DEBUT= "dated";
    public static final String KEY_DATE_FIN= "datef";
    public static final String KEY_RENATABILITE= "rentabilite";
    public static final String KEY_FLAG_SUPP= "flag_supp";
    public static final String KEY_FLAG_SYNCHRO= "flag_synchro";


        // action
    public static final String KEY_DESIGNATION= "flag_synchro";
    public static final String KEY_FLAG_IO= "flag_supp";

        //RTA
    public static final String KEY_ID_TACHE = "id";
    public static final String KEY_ID_ACTION= "id";
    public static final String KEY_DATE= "id";
    public static final String KEY_QNT= "id";
    public static final String KEY_PU= "dated";
    public static final String KEY_TOTAL= "datef";


        //Parametre
    public static final String KEY_VALEUR= "valeur";
    public static final String KEY_FLAG= "valeur";



    ///////// les creations de table


    public static final String CREATE_TABLE_TACHE= "CREATE TABLE IF NOT EXISTS " + TABLE_ACTION+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_NOM + " text," +
            " " + KEY_INFO+ " text," +
            " " + KEY_DESCRIPTION+ " text," +
            " " + KEY_DATE_DEBUT+ " text," +
            " " + KEY_DATE_FIN+ " text DEFAULT '0'," +
            " " + KEY_RENATABILITE+ " integer," +
            " " + KEY_FLAG_SUPP+ " integer DEFAULT 0," +
            " " + KEY_FLAG_SYNCHRO+ " integer DEFAULT 1 " +
            ");";



    public static final String CREATE_TABLE_ACTION= "CREATE TABLE IF NOT EXISTS " + TABLE_ACTION+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_DESIGNATION+ " text," +
            " " + KEY_FLAG_IO+ " integer," +
            " " + KEY_FLAG_SUPP+ " integer DEFAULT 0," +
            " " + KEY_FLAG_SYNCHRO+ " integer DEFAULT 1 " +
            ");";


    public static final String CREATE_TABLE_RTA= "CREATE TABLE IF NOT EXISTS " + TABLE_RTA+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_ID_TACHE + " integer ," +
            " " + KEY_ID_ACTION + " integer ," +
            " " + KEY_DESCRIPTION+ " text," +
            " " + KEY_DATE + " text," +
            " " + KEY_QNT + " real," +
            " " + KEY_PU + " real," +
            " " + KEY_TOTAL + " real," +
            " " + KEY_FLAG_SYNCHRO+ " integer DEFAULT 1" +
            ");";

    public static final String CREATE_TABLE_PARAMETRE= "CREATE TABLE IF NOT EXISTS " + TABLE_PARAMETRE+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_NOM+ " text," +
            " " + KEY_VALEUR+ " text," +
            " " + KEY_FLAG+ " integer," +
            " " + KEY_FLAG_SUPP+ " integer DEFAULT 0," +
            " " + KEY_FLAG_SYNCHRO+ " integer DEFAULT 1 " +
            ");";




    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;


    /**
     * ************************************************************************************************************************************************************
     * **************************************************************************************************************************************************************
     * **************************************************************************************************************************************************************
     * **************************************************************************************************************************************************************
     */


    // Constructeur
    public DatabaseManager(Context context) {
        maBaseSQLite = new MySQLite(context);
    }

    /**
     * **************************** Ouverture et fermeture de la base de donnÃ©es ****************************************************************
     */
    public void open() {
        //on ouvre la table en lecture/-Ã©criture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accÃ¨s Ã  la BDD
        db.close();
    }

    public void setterFlag(boolean a) {
        this.FlgDBR = a;
    }
    /************************************************************ Les Methodes **********************************************************/


    public void execu(String sql) {
            db.execSQL(sql);
    }

    // Get
    public Cursor getCurs(String sql) {
    // sÃ©lection de tous les enregistrements de la table
        return db.rawQuery(sql, null);
    }


    public String getString(String sql, String column){

        Cursor at = db.rawQuery(sql, null);
        String info="";

        if (at.moveToFirst()) {
            do {
                info= at.getString(at.getColumnIndex(column));
            } while (at.moveToNext());
            at.close();
        }
        return info;
    }



    public  int getInt(String sql, String column){

        Cursor at = db.rawQuery(sql, null);
        int info=0;

        if (at.moveToFirst()) {
            do {
                info= at.getInt(at.getColumnIndex(column));
            } while (at.moveToNext());
            at.close();
        }
        return info;
    }


    public  double getDouble(String sql, String column){

        Cursor at = db.rawQuery(sql, null);
        double info=0;

        if (at.moveToFirst()) {
            do {
                info= at.getDouble(at.getColumnIndex(column));
            } while (at.moveToNext());
            at.close();
        }
        return info;
    }



    /**
     * ************************ TACHE *************************************
     */

    // Ajouter
    public long addTache(String nom, String info, String description , String dated, int rentabilite) {
        // Ajout d'un enregistrement dans la table

        long result = 0;
            ContentValues values = new ContentValues();
            values.put(KEY_NOM, nom);
            values.put(KEY_INFO, info);
            values.put(KEY_DESCRIPTION, description);
            values.put(KEY_DATE_DEBUT, dated);
            values.put(KEY_RENATABILITE, rentabilite);

        // insert() retourne l'id du nouvel enregistrement insÃ©rÃ©, ou -1 en cas d'erreur
            result = db.insert(TABLE_ACTION, null, values);
        return result;
    }

    // Get all TACHES
    public Cursor getAllTaches() {
        // sÃ©lection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_TACHE+" ORDER BY id DESC", null);
    }

    // supprimer employe
    public int SuppTache(int id) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la clause WHERE, 0 sinon

        int a = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_FLAG_SUPP,1);
            String where = KEY_ID + "=" + id;
            //  String[] whereArgs = {employe.getId()+""};
            a = db.delete(TABLE_TACHE, where, null);

        return a;
    }

    // modifier  Tache
    public int modTache(int id, String nom, String info, String description, int rentabilite) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la requÃªte

        int ap = 0;
            ContentValues values = new ContentValues();
            values.put(KEY_NOM, nom);
            values.put(KEY_INFO, info);
            values.put(KEY_DESCRIPTION, description);
            values.put(KEY_RENATABILITE, rentabilite);

            String where = KEY_ID + "=" + id;
            // String[] whereArgs = {employe.getId()+""};

            ap = db.update(TABLE_TACHE, values, where, null);

        return ap;
    }



    // Finaliser  Tache
    public int FinaliseTache(int id,String date) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la requÃªte
        int ap = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_FIN, date);
        String where = KEY_ID + "=" + id;
        // String[] whereArgs = {employe.getId()+""};

        ap = db.update(TABLE_TACHE, values, where, null);

        return ap;
    }




}