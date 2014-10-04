package com.xinfan.blueblue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserInfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
	}

	public void btn_back(View v) {
		this.finish();
	}

	public void btn_back_send(View v) {
		this.finish();
	}
	/*
	 * public void head_xiaohei(View v) { Intent intent = new Intent();
	 * intent.setClass(InfoXiaohei.this,InfoXiaoheiHead.class);
	 * startActivity(intent); }
	 */
}