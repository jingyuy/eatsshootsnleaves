package com.esl.foodtag;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodItemAdapter extends ArrayAdapter<FoodItem>{
    Context context; 
    int layoutResourceId;    
    FoodItem data[] = null;
    
    public FoodItemAdapter(Context context, int layoutResourceId, FoodItem[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FoodItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new FoodItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.food_icon);
            holder.txtDate = (TextView)row.findViewById(R.id.food_date);
            row.setTag(holder);
        }
        else
        {
            holder = (FoodItemHolder)row.getTag();
        }
        
        FoodItem foodItem = data[position];
        holder.txtDate.setText(foodItem.date);
        holder.imgIcon.setImageResource(foodItem.icon);
        
        return row;
    }
    
    static class FoodItemHolder
    {
        ImageView imgIcon;
        TextView txtDate;
    }

}
