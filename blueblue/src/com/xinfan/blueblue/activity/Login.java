package com.xinfan.blueblue.activity;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xinfan.blueblue.request.AnsynHttpRequest;
import com.xinfan.blueblue.request.ObserverCallBack;
import com.xinfan.blueblue.request.Response;

public class Login extends Activity {
	private EditText mUser; 
	private EditText mPassword; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		
/*		AnsynHttpRequest.requestSimpleByPost(this, new ObserverCallBack(){

			@Override
			public void call(Response data) {
				data.getCode();
				data.getValue();
			}}, "login", new HashMap());*/

	}

	public void login_mainweixin(View v) {
		
		if ("1".equals(mUser.getText().toString()) && "1".equals(mPassword.getText().toString())) 
																										
		{
			Intent intent = new Intent();
			intent.setClass(Login.this, LoadingActivity.class);
			startActivity(intent);
			
		} else if ("".equals(mUser.getText().toString()) || "".equals(mPassword.getText().toString())) 
																										
		{
			new AlertDialog.Builder(Login.this).setIcon(getResources().getDrawable(R.drawable.login_error_icon)).setTitle("登录失败")
					.setMessage("密码错误").create().show();
		} else {

			new AlertDialog.Builder(Login.this).setIcon(getResources().getDrawable(R.drawable.login_error_icon)).setTitle("登录失败")
					.setMessage("登录失败").create().show();
		}
	}

	public void login_back(View v) {
		this.finish();
	}

	public void login_pw(View v) {
		Uri uri = Uri.parse("http://3g.qq.com");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
		// Intent intent = new Intent();
		// intent.setClass(Login.this,Whatsnew.class);
		// startActivity(intent);
	}
}
