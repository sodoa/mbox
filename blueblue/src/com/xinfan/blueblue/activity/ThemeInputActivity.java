package com.xinfan.blueblue.activity;

import com.xinfan.blueblue.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ThemeInputActivity extends Activity {

	private TextView ok;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme_input);

		ok = (TextView) this.findViewById(R.id.click_ok);

		ok.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();

				ToastUtil.showMessage(ThemeInputActivity.this, "添加成功");
			}
		});

	}

	public void send_msg_back(View view) {
		finish();
	}

	public void click_ok(View view) {
		finish();
	}

}