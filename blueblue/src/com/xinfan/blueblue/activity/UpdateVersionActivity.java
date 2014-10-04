package com.xinfan.blueblue.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class UpdateVersionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_dialog);
	}


	public void ComplianBack(View v) { // 返回
		this.finish();
	}

}
