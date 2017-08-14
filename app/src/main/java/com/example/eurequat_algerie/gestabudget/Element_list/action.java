package com.example.eurequat_algerie.gestabudget.Element_list;

/**
 * Created by Eurequat-Algerie on 14/08/2017.
 */

public class action {

    int id;

    String designation ="";
    int flag =0;

    public action(int id, String designation, int flag) {
        this.id = id;
        this.designation = designation;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public int getFlag() {
        return flag;
    }
}
