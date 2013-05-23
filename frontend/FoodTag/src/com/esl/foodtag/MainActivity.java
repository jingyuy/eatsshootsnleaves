package com.esl.foodtag;

import com.esl.foodtag.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListFragment;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        FoodItem foodItems[] = new FoodItem[]
        {
            new FoodItem(R.drawable.s_2013_05_16_13, "2013-05-16 13.04.01"),
            new FoodItem(R.drawable.s_2013_05_10_18, "2013_05_10 18.28.24"),
            new FoodItem(R.drawable.s_2013_05_09_12, "2013_05_09 12.40.16"),
            new FoodItem(R.drawable.s_2013_04_25_12, "2013_04_25 12.41.53"),
            new FoodItem(R.drawable.s_2013_03_16_19, "2013_03_16 19.47.15")
        };
        
        FoodItemAdapter adapter = new FoodItemAdapter(this, 
                R.layout.food_item, foodItems);
        ListFragment listFragment = (ListFragment)getFragmentManager().findFragmentById(R.id.food_listfragment);
        listFragment.setListAdapter(adapter);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
