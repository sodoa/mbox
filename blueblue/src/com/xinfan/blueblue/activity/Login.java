package com.xinfan.blueblue.activity;

import com.xinfan.blueblue.request.AnsynHttpRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {
	private EditText mUser; 
	private EditText mPassword; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);

	}

	public void login_mainweixin(View v) {
		if ("admin".equals(mUser.getText().toString()) && "1".equals(mPassword.getText().toString())) // �ж�
																										// �ʺź�����
		{
			Intent intent = new Intent();
			intent.setClass(Login.this, LoadingActivity.class);
			startActivity(intent);
			// Toast.makeText(getApplicationContext(), "��¼�ɹ�",
			// Toast.LENGTH_SHORT).show();
		} else if ("".equals(mUser.getText().toString()) || "".equals(mPassword.getText().toString())) // �ж�
																										// �ʺź�����
		{
			new AlertDialog.Builder(Login.this).setIcon(getResources().getDrawable(R.drawable.login_error_icon)).setTitle("��¼����")
					.setMessage("΢���ʺŻ������벻��Ϊ�գ�\n��������ٵ�¼��").create().show();
		} else {

			new AlertDialog.Builder(Login.this).setIcon(getResources().getDrawable(R.drawable.login_error_icon)).setTitle("��¼ʧ��")
					.setMessage("΢���ʺŻ������벻��ȷ��\n������������룡").create().show();
		}

		// ��¼��ť
		/*
		 * Intent intent = new Intent();
		 * intent.setClass(Login.this,Whatsnew.class); startActivity(intent);
		 * Toast.makeText(getApplicationContext(), "��¼�ɹ�",
		 * Toast.LENGTH_SHORT).show(); this.finish();
		 */
	}

	public void login_back(View v) { // ����8 ���ذ�ť
		this.finish();
	}

	public void login_pw(View v) { // ������밴ť
		Uri uri = Uri.parse("http://3g.qq.com");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
		// Intent intent = new Intent();
		// intent.setClass(Login.this,Whatsnew.class);
		// startActivity(intent);
	}
}
