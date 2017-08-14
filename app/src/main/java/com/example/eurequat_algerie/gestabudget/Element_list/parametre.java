package com.example.eurequat_algerie.gestabudget.Element_list;

/**
 * Created by HP on 8/14/2017.
 */

public class parametre {
    int id;
    String nom, valeur;
    public parametre(int id, String nm, String va) {
        this.id = id;
        this.nom = nm;
        this.valeur= va;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
