package com.example.eurequat_algerie.gestabudget.Element_list;

/**
 * Created by HP on 8/15/2017.
 */

public class detail {
    int id;
    String designation, descritpion, date;
    double total,quant,prixu;

    public detail(int id, String designation, String date, String descritpion, double quant, double prixu, double total) {
        this.id = id;
        this.designation = designation;
        this.date = date;
        this.descritpion = descritpion;
        this.quant = quant;
        this.prixu = prixu;
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPrixu() {
        return prixu;
    }

    public void setPrixu(double prixu) {
        this.prixu = prixu;
    }

    public double getQuant() {
        return quant;
    }

    public void setQuant(double quant) {
        this.quant = quant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
