package com.xinfan.blueblue.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.xinfan.blueblue.activity.adapter.ThemeSetAdapter;
import com.xinfan.blueblue.util.DialogTool;
import com.xinfan.blueblue.vo.ThemeVo;

public class ThemeSetActivity extends Activity {
	/** Called when the activity is first created. */

	private ListView themeList;

	private ArrayList list = new ArrayList();

	private ThemeSetAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme_set);

		ThemeVo first = new ThemeVo();
		first.setId("1");
		first.setText("如何实现长按列表弹出对话框 [复制链接111111111111]");

		ThemeVo first2 = new ThemeVo();
		first2.setId("2");
		first2.setText("如何实现长按列表弹出对话框 [复制链接12222222222]");

		list.add(first2);
		list.add(first);

		// list.add("如何实现长按列表弹出对话框 [复制链接]");

		themeList = (ListView) findViewById(R.id.theme_list);
		adapter = new ThemeSetAdapter(this, list);

		themeList.setAdapter(adapter);

		themeList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long arg3) {
				ThemeVo vo = (ThemeVo)  list.get(position);
				if (vo != null) {
					DialogTool.createNormalDialog(ThemeSetActivity.this, R.drawable.icon, "长按时间", vo.getText(), "确定",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									//ThemeSetActivity.this.finish();
									arg0.cancel();
								}
							}).show();
				}

				return false;
			}
		});

		adapter.notifyDataSetChanged();
	}

	public void send_msg_back(View view) {
		finish();
	}

	public void click_theme_add(View view) {

		Intent intent = new Intent();
		intent.setClass(this, ThemeInputActivity.class);
		startActivity(intent);

	}

}