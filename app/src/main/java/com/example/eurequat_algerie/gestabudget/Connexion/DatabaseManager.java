package com.example.eurequat_algerie.gestabudget.Connexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.ResultSet;
import java.util.ArrayList;

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
    public static final String KEY_DESIGNATION= "designation";
    public static final String KEY_FLAG_IO= "flag_io";

        //RTA
    public static final String KEY_ID_TACHE = "id_tache";
    public static final String KEY_ID_ACTION= "id_action";
    public static final String KEY_DATE= "date";
    public static final String KEY_QNT= "qnt";
    public static final String KEY_PU= "pu";
    public static final String KEY_TOTAL= "total";


        //Parametre

    public static final String KEY_VALEUR= "valeur";
    public static final String KEY_FLAG= "flag";



    ///////// les creations de table


    public static final String CREATE_TABLE_TACHE= "CREATE TABLE IF NOT EXISTS " + TABLE_TACHE+
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
    //j'ai changé le type de rentabilite de int vers string
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
            result = db.insert(TABLE_TACHE, null, values);
        return result;
    }

    // Get all TACHES
    public Cursor getAllTaches() {
        // sÃ©lection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_TACHE+" WHERE "+KEY_FLAG_SUPP+"<2 ORDER BY id DESC", null);
    }



    // supprimer employe
    public int SuppTache(int id) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la clause WHERE, 0 sinon

        int a = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_FLAG_SUPP,2);
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
        values.put(KEY_FLAG_SUPP, 1);
        String where = KEY_ID + "=" + id;
        // String[] whereArgs = {employe.getId()+""};

        ap = db.update(TABLE_TACHE, values, where, null);

        return ap;
    }




    // Parametre

    // Ajouter
    public long addParametre(String nom, String valeur , int flag) {
        // Ajout d'un enregistrement dans la table

        long result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, nom);
        values.put(KEY_VALEUR, valeur);
        values.put(KEY_FLAG, flag);

        // insert() retourne l'id du nouvel enregistrement insÃ©rÃ©, ou -1 en cas d'erreur
        result = db.insert(TABLE_PARAMETRE, null, values);
        return result;
    }

    public int modParametre(int id, String valeur) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la requÃªte

        int ap = 0;
        ContentValues values = new ContentValues();

        values.put(KEY_VALEUR, valeur);

        String where = KEY_ID + "=" + id;
        // String[] whereArgs = {employe.getId()+""};

        ap = db.update(TABLE_PARAMETRE, values, where, null);

        return ap;
    }


    public Cursor getAllParametres() {
        // sÃ©lection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_PARAMETRE+" WHERE "+KEY_FLAG_SUPP+"<2 ORDER BY id DESC", null);
    }


    // supprimer employe
    public int SuppParametre(int id) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la clause WHERE, 0 sinon

        int a = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_FLAG_SUPP,2);
        String where = KEY_ID + "=" + id;
        //  String[] whereArgs = {employe.getId()+""};
        a = db.delete(TABLE_PARAMETRE, where, null);

        return a;
    }



    // Action

    // Ajouter
    public long addAction(String nom, int flag) {
        // Ajout d'un enregistrement dans la table

        long result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_DESIGNATION, nom);
        values.put(KEY_FLAG_IO, flag);

        // insert() retourne l'id du nouvel enregistrement insÃ©rÃ©, ou -1 en cas d'erreur
        result = db.insert(TABLE_ACTION, null, values);
        return result;
    }

    // modifier  Tache
    public int modAction(int id, String nom, int info) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la requÃªte

        int ap = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_DESIGNATION, nom);
        values.put(KEY_FLAG_IO, info);

        String where = KEY_ID + "=" + id;
        // String[] whereArgs = {employe.getId()+""};

        ap = db.update(TABLE_ACTION, values, where, null);

        return ap;
    }



    // supprimer employe
    public int SuppAction(int id) {

        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la clause WHERE, 0 sinon

        int a = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_FLAG_SUPP,2);
        String where = KEY_ID + "=" + id;
        //  String[] whereArgs = {employe.getId()+""};

        a = db.delete(TABLE_ACTION, where, null);

        return a;
    }
    public int SuppRta(int id) {

        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la clause WHERE, 0 sinon

        int a = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_FLAG_SUPP,2);
        String where = KEY_ID + "=" + id;
        //  String[] whereArgs = {employe.getId()+""};

        a = db.delete(TABLE_RTA, where, null);

        return a;
    }



    public Cursor getAllActions() {
        // sÃ©lection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_ACTION+" WHERE "+KEY_FLAG_SUPP+"<2 ORDER BY id DESC", null);
    }

   public int modRta(int id,int id_t, int ida, String desciption, double qn, double prix, double tot) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la requÃªte

        int ap = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_ID_TACHE,id_t);
        values.put(KEY_ID_ACTION,ida);
        values.put(KEY_DESCRIPTION, desciption);
        values.put(KEY_QNT, qn);
        values.put(KEY_PU, prix);
        values.put(KEY_TOTAL, tot);
        String where = KEY_ID + "=" + id;
        // String[] whereArgs = {employe.getId()+""};

        ap = db.update(TABLE_RTA, values, where, null);

        return ap;
    }
    public long addRta(int id_ta, int id_ac, String description , String dat, double qn, double prix, double tot) {
        // Ajout d'un enregistrement dans la table

        long result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_ID_TACHE, id_ta);
        values.put(KEY_ID_ACTION, id_ac);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_DATE, dat);
        values.put(KEY_QNT, qn);
        values.put(KEY_PU, prix);
        values.put(KEY_TOTAL, tot);

        // insert() retourne l'id du nouvel enregistrement insÃ©rÃ©, ou -1 en cas d'erreur
        result = db.insert(TABLE_RTA, null, values);
        return result;
    }
    public Cursor getAllRTA() {
        // sÃ©lection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_RTA, null);
    }



}