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
 * Created by HP on 8/14/2017.
 */

public class Adapter_parametre extends BaseAdapter {
    ArrayList<parametre> myList = new ArrayList<parametre>();
    LayoutInflater inflater;
    Context context;
    int firstColorValue = Color.parseColor("#dff0f7");
    int secondColorValue = Color.parseColor("#f8fefe");
    private int[] colors = new int[] { firstColorValue, secondColorValue};

    public Adapter_parametre(Context context, ArrayList<parametre> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public int getCount() {
        return myList.size();
    }

    public parametre getItem(int position) {
        return myList.get(position);
    }

    public long getItemId(int position) {
        return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.element_parametre, parent, false);
            mViewHolder = new Adapter_parametre.MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (Adapter_parametre.MyViewHolder) convertView.getTag();
        }
        parametre currentListData = getItem(position);
        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        mViewHolder.nom.setText(currentListData.getNom());
        mViewHolder.vale.setText(""+currentListData.getValeur());
        return convertView;
    }
    private class MyViewHolder {
        TextView nom, vale;

        public MyViewHolder(View item) {
            nom = (TextView) item.findViewById(R.id.nom);
            vale = (TextView) item.findViewById(R.id.valeur);


        }
    }}
