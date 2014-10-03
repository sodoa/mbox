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
	private EditText mMobile; // 手机号码编辑框
	private EditText mPassword; // 密码编辑框
	private EditText rePassword; //重复 密码编辑框
	private EditText mRanCode; // 验证码编辑框
	private EditText mNickname; // 昵称
	private  static String mobile,password,repassword,nickname,rancode;
	private  String gencode;//生成的验证码
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
    	        
    	   String message=(String)msg.obj;//obj不一定是String类，可以是别的类，看用户具体的应用
    	   if(message.equals("发送成功")){
    		   new AlertDialog.Builder(Register.this)
  				.setIcon(getResources().getDrawable(R.drawable.login_editbox))
  				.setMessage("发送成功！请注意查收！")
  				.create().show();
   		}else{
   			 new AlertDialog.Builder(Register.this)
    			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
    			.setMessage("发送失败！验证码发送遇到未知错误！")
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
  			.setTitle("昵称错误")
  			.setMessage("您没有填写昵称或昵称已被注册！")
  			.create().show();
          }
    	else if(password==null||password.length()<=0)   //判断 密码是否为空
        {
    		new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("密码未设置")
			.setMessage("密码不能为空！")
			.create().show();
          }
        else if(!(password.equals(repassword)))   //判断 两次密码是否一样
        {
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("密码错误")
			.setMessage("两次输入密码不一致！")
			.create().show();
         }
        else if(!((p.matcher(mobile)).matches())){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("手机号码错误")
			.setMessage("您没有填写手机号码或格式错误！")
			.create().show();
        }
        else if(mobile.equals("18674866463")){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("手机号码错误")
			.setMessage("您的手机号码已经被占用！")
			.create().show();
        }
        else if(rancode==null||rancode.length()<=0){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("验证码错误")
			.setMessage("请填写验证码！")
			.create().show();
        }
        else if(!(rancode.equals(gencode))){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("验证码错误")
			.setMessage("验证码错误!")
			.create().show();
        }
        else{
            Intent intent = new Intent();
            intent.setClass(Register.this,LoadingActivity.class);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
        }
    	
    	//登录按钮
    	/*
      	Intent intent = new Intent();
		intent.setClass(Login.this,Whatsnew.class);
		startActivity(intent);
		Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
		this.finish();*/
      }  
    public void register_back(View v) {     //标题栏 返回按钮
      	this.finish();
      }  
    
    //发送手机验证码
    public   void  sendSMS(View v){   
    	mobile=mMobile.getText().toString();
    	if(!((p.matcher(mobile)).matches())){
        	new AlertDialog.Builder(Register.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("手机号码错误")
			.setMessage("您没有填写手机号码或格式错误！")
			.create().show();
        	return ;
        }
    	//消息内容
    	String msg="您注册布鲁的验证码是"+gencode;
    	  // 使用NameValuePair来保存要传递的Post参数
    	  final List<NameValuePair> params = new ArrayList<NameValuePair>();
    	  // 添加要传递的参数
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
				//HttpStatus.SC_OK表示连接成功
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
			    {
					Message message = Message.obtain();
					message.obj="发送成功";
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
    
  		//生成验证码
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