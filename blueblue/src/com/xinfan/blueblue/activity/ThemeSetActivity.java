package com.xinfan.blueblue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ThemeSetActivity extends Activity{
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme_set);
	}

	public void send_msg_back(View view) {
		finish();
	}
	
	public void click_theme_add(View view){
		
		Intent intent = new Intent();
		intent.setClass(this, ThemeInputActivity.class);
		startActivity(intent);
		
	}

}