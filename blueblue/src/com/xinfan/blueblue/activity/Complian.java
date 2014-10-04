package com.xinfan.blueblue.activity;


import com.xinfan.blueblue.activity.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Complian extends Activity {
	private EditText mComplainText;//文本编辑框
	private String ComplainText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complain);
		mComplainText=(EditText) findViewById(id.complainText);
	}
	public  void complain_send(View v) {
		// TODO Auto-generated method stub
		ComplainText=mComplainText.getText().toString();
		if(ComplainText==null||ComplainText.equals("")){
			new AlertDialog.Builder(Complian.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setMessage("请先编辑内容！")
			.create().show();
		}
		else{
			new AlertDialog.Builder(Complian.this)
			.setIcon(getResources().getDrawable(R.drawable.login_editbox))
			.setMessage("发送成功！")
			.create().show();
		}
	}
	  public void ComplianBack(View v) {     //返回
	      	this.finish();
	      }  
	    

}
