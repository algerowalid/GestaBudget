package com.example.eurequat_algerie.gestabudget.Element_list;

/**
 * Created by Eurequat-Algerie on 07/06/2017.
 */

public class tache {

   int id, rentabilite;
    String nom, info, description,date;



    public tache(int id, String nm, String in, String des, String dated, int rent) {
        this.id = id;
        this.nom = nm;
        this.info= in;
        this.description = des;
        this.date = dated;

        this.rentabilite = rent;
    }


    public int getId() {
        return id;
    }

    public int getRentabilite() {
        return rentabilite;
    }

    public String getNom() {
        return nom;
    }

    public String getInfo() {
        return info;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }


}
