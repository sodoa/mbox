package com.xinfan.blueblue.activity.send;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinfan.blueblue.activity.R;

public class SendMessageActivity extends Activity implements OnClickListener {

	public Button message_more_btn;

	public EditText message_more_edit;

	public View time_select_layout;

	public TextView time_select_label;

	public static SendMessageActivity instance;

	private ArrayList<TimeListVo> timeList = new ArrayList<TimeListVo>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_message);
		message_more_btn = (Button) findViewById(R.id.message_more_btn);
		message_more_edit = (EditText) findViewById(R.id.message_more_edit);
		time_select_layout = (View) findViewById(R.id.time_select_layout);
		time_select_label = (TextView) findViewById(R.id.time_select_label);

		message_more_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				int vis = message_more_edit.getVisibility();
				if (vis == View.GONE) {
					message_more_edit.setVisibility(View.VISIBLE);
					message_more_btn.setText("关闭更多");
				} else {
					message_more_edit.setVisibility(View.GONE);
					message_more_btn.setText("更多输入");
				}
			}
		});
		time_select_layout.setOnClickListener(this);

		instance = this;
	}

	public void send_msg_back(View view) {
		finish();
	}

	public void updateTimeSelect(TimeListVo vo) {
		time_select_label.setText(vo.getText());
		time_select_label.setTag(vo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.time_select_layout:

			Intent intent = new Intent();
			intent.setClass(this, TimeSelectActivity.class);
			startActivity(intent);

			break;
		default:
			break;
		}
	}

}