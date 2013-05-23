package com.esl.foodtag;

import com.esl.foodtag.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;

public class TagActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		int imageResourceId = getIntent().getIntExtra("imageResourceId", 0);
		ImageView imageView = (ImageView)this.findViewById(R.id.food_img);
		imageView.setImageResource(imageResourceId);
		
	}
}
