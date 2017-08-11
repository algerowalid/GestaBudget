package com.example.eurequat_algerie.gestabudget.Connexion;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Eurequat-Algerie on 31/05/2016.
 */
public class Network {

    private static final String url = "jdbc:mysql://192.168.1.100:3306/sopem";
    private static final String user = "root";
    private static final String pass = "";
    public static Connection con;
    public static Statement st;


    // Test et autres méthodes

    public static void connection(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Erreur: ", e.toString());
        }
    }

    public static void close(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDbConnected() {
        final String CHECK_SQL_QUERY = "SELECT 1";
        boolean isConnected = false;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Erreur: ", e.toString());
        }
        String result = "connection sucess\n";
        try {
            final PreparedStatement statement = con.prepareStatement(CHECK_SQL_QUERY);
            isConnected = true;
        } catch (SQLException | NullPointerException e) {
            // handle SQL error here!
        }
        return isConnected;
    }


    public void testDB(String sql) {

        // String sql="INSERT INTO `table`(`id`, `wa`, `wae`, `flg`) VALUES ('','android','android',5)";
        try {


            String result = "connection sucess\n";
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            st1.executeUpdate(sql);


         /*   ResultSet rs=st.executeQuery("select * from tabletest");

            ResultSetMetaData rsmd=rs.getMetaData();
            while (rs.next()){
                result += rsmd.getColumnName(1) + " : " + rs.getInt(1) + "\n";
                result += rsmd.getColumnName(2)+ " : " +rs.getString(2)+"\n";
            }
            */


        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Erreur: ", e.toString());
        }
    }


    public int GetStat(String Table) throws SQLException {

        int etat = 0;
        ResultSet rs;
        String sql = "Select * from synchronisation where nom_table='" + Table + "'";

        rs = st.executeQuery(sql);
        while (rs.next()) {
            etat = rs.getInt(3);
        }

        return etat;
    }


    public static int RecuperationInt(String req, String col){
        int idi = 0;
        try {

            ResultSet rs2 = st.executeQuery(req);
            boolean k=rs2.next();
            while (k){
                idi=rs2.getInt(col);
                k=rs2.next();
            }

        } catch (SQLException e) {
            System.out.println("Anomalie requete: "+req);
        }

        return idi;}

    public static String RecuperationString(String req, String col){
        String idi ="";
        try {

            ResultSet rs2 = st.executeQuery(req);
            boolean k=rs2.next();
            while (k){
                idi=rs2.getString(col);
                k=rs2.next();
            }

        } catch (SQLException e) {
            System.out.println("Anomalie requete: "+req);
        }

        return idi;}

    public static double RecuperationDouble(String req, String col){
        double idi = 0;
        try {

            ResultSet rs2 = st.executeQuery(req);
            boolean k=rs2.next();
            while (k){
                idi=rs2.getDouble(col);
                k=rs2.next();
            }

        } catch (SQLException e) {
            System.out.println("Anomalie requete: "+req);
        }

        return idi;}
    

     public static int query(String req) throws SQLException {

        int etat = 0;
         boolean a =false;
        ResultSet rs;

         a= st.execute(req);
         if(a){
             etat=1;
         }else{
             etat=0;
         }

         return etat;
    }



    public static ResultSet GetAll(String req ) throws SQLException {

        ResultSet rs;
        st= con.createStatement();
        rs = st.executeQuery(req);

        return rs;
    }

}