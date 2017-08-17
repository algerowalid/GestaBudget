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
 * Created by HP on 8/15/2017.
 */

public class Adapter_detail extends BaseAdapter {
    ArrayList<detail> myList = new ArrayList<detail>();
    LayoutInflater inflater;
    Context context;
    int firstColorValue = Color.parseColor("#dff0f7");
    int secondColorValue = Color.parseColor("#f8fefe");
    private int[] colors = new int[] { firstColorValue, secondColorValue};


    public Adapter_detail(Context context, ArrayList<detail> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }


    public int getCount() {
        return myList.size();
    }

    public detail getItem(int position) {
        return myList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.element_tache_detail, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        detail currentListData = getItem(position);

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        mViewHolder.desi.setText(currentListData.getDesignation());
        mViewHolder.desc.setText(currentListData.getDescritpion());
        mViewHolder.qntit.setText(String.valueOf(currentListData.getQuant()));
        mViewHolder.prixuni.setText(String.valueOf(currentListData.getPrixu()));
        mViewHolder.dat.setText(currentListData.getDate());
        mViewHolder.totale.setText(String.valueOf(currentListData.getTotal()));
        return convertView;
    }

    private class MyViewHolder {
        TextView desi, desc, qntit, prixuni, dat, totale;

        public MyViewHolder(View item) {
            desi= (TextView) item.findViewById(R.id.designation);
            desc= (TextView) item.findViewById(R.id.description);
            qntit= (TextView) item.findViewById(R.id.qnt);
            prixuni= (TextView) item.findViewById(R.id.pu);
            dat= (TextView) item.findViewById(R.id.date);
            totale= (TextView) item.findViewById(R.id.total);

        }
    }
}
