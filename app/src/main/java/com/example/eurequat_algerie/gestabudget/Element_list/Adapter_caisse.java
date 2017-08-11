package com.example.eurequat_algerie.gestabudget.Element_list;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.innodev.easy_relay.R;

import java.util.ArrayList;

/**
 * Created by Eurequat-Algerie on 25/04/2017.
 */
public class Adapter_caisse extends BaseAdapter {

    ArrayList<caisse> myList = new ArrayList<caisse>();
    LayoutInflater inflater;
    Context context;
    int firstColorValue = Color.parseColor("#dff0f7");
    int secondColorValue = Color.parseColor("#f8fefe");
    private int[] colors = new int[] { firstColorValue, secondColorValue};


    public Adapter_caisse(Context context, ArrayList<caisse> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }


    public int getCount() {
        return myList.size();
    }

    public caisse getItem(int position) {
        return myList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.element_caisse, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        caisse currentListData = getItem(position);

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        mViewHolder.id.setText("Caisse "+currentListData.getId());
        mViewHolder.mont.setText(""+currentListData.getMontant());
        mViewHolder.dd.setText(""+currentListData.getDated());
        mViewHolder.df.setText(""+currentListData.getDatef());

        return convertView;
    }

    private class MyViewHolder {
        TextView id,mont,dd,df;

        public MyViewHolder(View item) {
            id= (TextView) item.findViewById(R.id.caisse);
            mont = (TextView) item.findViewById(R.id.montant);
            dd = (TextView) item.findViewById(R.id.dated);
            df = (TextView) item.findViewById(R.id.datef);

        }
    }
}
