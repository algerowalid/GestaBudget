package com.example.eurequat_algerie.gestabudget.Element_list;

/**
 * Created by Eurequat-Algerie on 07/06/2017.
 */

public class caisse {

    int id;
    String dated, datef;
    double montant;

    public caisse(int id, String dated, String datef, double montant) {
        this.id = id;
        this.dated = dated;
        this.datef = datef;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public String getDated() {
        return dated;
    }

    public String getDatef() {
        return datef;
    }

    public double getMontant() {
        return montant;
    }
}
