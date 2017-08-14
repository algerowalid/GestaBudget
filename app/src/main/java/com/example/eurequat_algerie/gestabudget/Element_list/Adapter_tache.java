package com.example.eurequat_algerie.gestabudget.Element_list;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eurequat_algerie.gestabudget.R;


import java.util.ArrayList;

/**
 * Created by Eurequat-Algerie on 25/04/2017.
 */

public class Adapter_tache extends BaseAdapter {
 

    ArrayList<tache> myList = new ArrayList<tache>();
    LayoutInflater inflater;
    Context context;
    int firstColorValue = Color.parseColor("#dff0f7");
    int secondColorValue = Color.parseColor("#f8fefe");
    private int[] colors = new int[] { firstColorValue, secondColorValue};


    public Adapter_tache(Context context, ArrayList<tache> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }


    public int getCount() {
        return myList.size();
    }

    public tache getItem(int position) {
        return myList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.element_tache, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        tache currentListData = getItem(position);

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        mViewHolder.nom.setText(currentListData.getNom());
        mViewHolder.info.setText(""+currentListData.getInfo());


        return convertView;
    }

    private class MyViewHolder {
        TextView nom, info;

        public MyViewHolder(View item) {
            nom= (TextView) item.findViewById(R.id.nom);
            info = (TextView) item.findViewById(R.id.inf);


        }
    }
}