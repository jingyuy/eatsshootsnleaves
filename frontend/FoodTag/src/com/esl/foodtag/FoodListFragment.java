package com.esl.foodtag;

import java.util.HashMap;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class FoodListFragment extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.listfragment_food, container, false);
	}	
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		FoodItem item = (FoodItem)l.getItemAtPosition(position);
        Intent tagIntent = new Intent(getActivity(), TagActivity.class);
        tagIntent.putExtra("imageResourceId", item.icon);
        startActivity(tagIntent);
	}
	
}
