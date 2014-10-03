package com.xinfan.blueblue.activity;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;



public class Register extends Activity {
	private EditText mMobile; 
	private EditText mPassword; 
	private EditText rePassword; 
	private EditText mRanCode; 
	private EditText mNickname;
	private  static String mobile,password,repassword,nickname,rancode;
	private  String gencode;
	private Handler handler;
	private final Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mMobile = (EditText)findViewById(R.id.register_mobile_edit);
        mPassword = (EditText)findViewById(R.id.register_passwd_edit);
        rePassword = (EditText)findViewById(R.id.register_repasswd_edit);
        mNickname = (EditText)findViewById(R.id.register_nickname_edit);
        mRanCode = (EditText)findViewById(R.id.register_rancode_edit);
       gencode= genRanCode(4);
       handler=new Handler(){
    	   public void handleMessage(Message msg){
    	        
    	   String message=(String)msg.obj;
    	   if(message.equals("���ͳɹ�")){
    		   new AlertDialog.Builder(Register.this)
  				.setIcon(getResources().getDrawable(R.drawable.login_editbox))
  				.setMessage("���ͳɹ�����ע����գ�")
  				.create().show();
   		}else{
   			 new AlertDialog.Builder(Register.this)
    			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
    			.setMessage("����ʧ�ܣ���֤�뷢����δ֪����")
    			.create().show();
   		} 								                                                     }
    	   
    	   };
    }
    public Handler getHandler(){
    	return this.handler;
    	}
    public void register(View v) {
		mobile=mMobile.getText().toString();
    	password=mPassword.getText().toString();
    	repassword=rePassword.getText().toString();
    	nickname=mNickname.getText().toString();
    	rancode=mRanCode.getText().toString();
    	System.out.println("nickname"+nickname);
    	System.out.println("password"+password);
    	 if(nickname==null||nickname.length()<=0){
          	new AlertDialog.Builder(Register.this)
  			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
  			.setTitle("�ǳƴ���")
  			.setMessage("��û����д�ǳƻ��ǳ��ѱ�ע�ᣡ")
  			.create().show();
          }
    	else if(password==null||password.length()<=0)   //�ж� �����Ƿ�Ϊ��
        {
    		new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("����δ����")
			.setMessage("���벻��Ϊ�գ�")
			.create().show();
          }
        else if(!(password.equals(repassword)))   //�ж� }�������Ƿ�һ��
        {
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("�������")
			.setMessage("}���������벻һ�£�")
			.create().show();
         }
        else if(!((p.matcher(mobile)).matches())){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("�ֻ�������")
			.setMessage("��û����д�ֻ������ʽ����")
			.create().show();
        }
        else if(mobile.equals("18674866463")){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("�ֻ�������")
			.setMessage("����ֻ�����Ѿ���ռ�ã�")
			.create().show();
        }
        else if(rancode==null||rancode.length()<=0){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("��֤�����")
			.setMessage("����д��֤�룡")
			.create().show();
        }
        else if(!(rancode.equals(gencode))){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("��֤�����")
			.setMessage("��֤�����!")
			.create().show();
        }
        else{
            Intent intent = new Intent();
            intent.setClass(Register.this,LoadingActivity.class);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
        }
    	
    	//��¼��ť
    	/*
      	Intent intent = new Intent();
		intent.setClass(Login.this,Whatsnew.class);
		startActivity(intent);
		Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
		this.finish();*/
      }  
    public void register_back(View v) {     //����8 ���ذ�ť
      	this.finish();
      }  
    
    //�����ֻ���֤��
    public   void  sendSMS(View v){   
    	mobile=mMobile.getText().toString();
    	if(!((p.matcher(mobile)).matches())){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("�ֻ�������")
			.setMessage("��û����д�ֻ������ʽ����")
			.create().show();
        	return ;
        }
    	//��Ϣ����
    	String msg="��ע�᲼³����֤����"+gencode;
    	  // ʹ��NameValuePair4����Ҫ���ݵ�Post����
    	  final List<NameValuePair> params = new ArrayList<NameValuePair>();
    	  // ���Ҫ���ݵĲ���
    	  NameValuePair pair1 = new BasicNameValuePair("name", "aasdkjasdkj");
    	  NameValuePair pair2 = new BasicNameValuePair("password","asdsjka");
    	  NameValuePair pair3 = new BasicNameValuePair("mobile", mobile);
    	  NameValuePair pair4 = new BasicNameValuePair("msg", msg);
    	  params.add(pair1);
    	  params.add(pair2);
    	  params.add(pair3);
    	  params.add(pair4);
		new Thread(new Runnable() {
		   @Override
        public void run() {
	    	try {
	    		HttpPost httpPost=new HttpPost("http://www.baidu.com");
	    		HttpClient httpClient=new DefaultHttpClient();
	    		HttpEntity httpEntity = new UrlEncodedFormEntity(params, "gb2312");
	    		httpPost.setEntity(httpEntity);
	    		HttpResponse httpResponse=httpClient.execute(httpPost);
				//HttpStatus.SC_OK��ʾl�ӳɹ�
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
			    {
					Message message = Message.obtain();
					message.obj="���ͳɹ�";
					handler.sendMessage(message);
			    } 
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   }
		}).start();
		Looper.loop();
    }
    
  		//�����֤��
	    public String genRanCode(int lenght){
		 String base = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";     
		    Random random = new Random();     
		    StringBuffer sb = new StringBuffer();     
		    for (int i = 0; i < lenght; i++) {     
		        int number = random.nextInt(base.length());     
		        sb.append(base.charAt(number));     
		    }      
		    return sb.toString();
	    }
}