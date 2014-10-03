package com.xinfan.blueblue.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.xinfan.blueblue.util.LogUtil;

public class AnsynHttpRequest {

	public static final int REQUEST_TIMEOUT = 30 * 1000;// 设置请求超时10秒钟
	public static final int SO_TIMEOUT = 30 * 1000; // 设置等待数据超时时间10秒钟
	static final int POST = 1; // post 提交
	static final int GET = 2; // get 提交

	static String tag = AnsynHttpRequest.class.getSimpleName();

	public static DefaultHttpClient mHttpClient;

	/**
	 * 
	 * 访问网络初始化函数 支持GET请求方式
	 * 
	 * @param callBack
	 *            回调执行函数 支持线程
	 * @param url
	 *            每个执行url
	 * @param map
	 *            参数
	 * @param isCache
	 *            是否本地缓存
	 * @param isShowDialog
	 *            是否弹出提示等待框
	 */
	public static void requestByGet(Context context, final ObserverCallBack callBack, String url, Map<String, String> map, boolean isCache, boolean isShowDialog) {
		// 组织URL
		StringBuffer buffer = new StringBuffer();
		buffer.append(Constants.http.http_request_head)
		// .append(context.getResources().getString(url))
				.append("?");
		boolean listData = true;
		String selectUrl = "";
		if (map != null && map.size() > 0) {
			String value = "";
			if (map.containsKey("start")) {
				if (map.get("start").equals("0")) {
					listData = true;
					selectUrl = buffer.toString();
				} else
					listData = false;
				value = "start=" + map.get("start") + "&";
			}
			for (String key : map.keySet()) {
				if (key.equals("start")) {
					continue;
				}
				buffer.append(key).append("=").append(Encoder.encode(map.get(key))).append("&");
			}
			buffer.append(value);
		}

		String requestUrl = buffer.toString();
		requestUrl = requestUrl.substring(0, requestUrl.length() - 1);
		LogUtil.i("httpurl", requestUrl);

		String[] data = null;
		
		if (isCache) { // 如果是缓存 取数据库缓存中数据
			data = DBHelper.getInstance(context).getURLData(requestUrl);
		}

		switch (Network.checkNetWorkType(context)) {
		case /** 网络不可用 */
		Network.NONETWORK:
			if (data == null) {
				// sendBroadcastReceiverMessage(context,
				// R.string.network_show_connectionless);
				callBack.call(null);
				// ((BaseActivity) context).checkNetWorkShowLog(1);
				return;
			}
			break;
		case /** 是wifi连接 */
		Network.WIFI:
			if (data != null && data.length > 0 && listData && TimeCompare.compareHourTime(data[1]) > 3) {
				// if(data != null && data.length > 0 && listData &&
				// TimeCompare.compareHourTime(data[1]) > 1){
				data = null;
				DBHelper.getInstance(context).deleteURLData(selectUrl);
			}
			break;
		case /** 不是wifi连接 */
		Network.NOWIFI: // 是
			if (data != null && data.length > 0 && listData && TimeCompare.compareHourTime(data[1]) > 24 * 2) {
				// if(data != null && data.length > 0 && listData &&
				// TimeCompare.compareHourTime(data[1]) > 1){
				data = null;
				DBHelper.getInstance(context).deleteURLData(selectUrl);
			}
			break;
		default:
			break;
		}
		if (isCache && data != null && data.length > 0) { // 如果是缓存 取数据库缓存中数据
			Response response = JSONObject.parseObject(data[0], Response.class);
			callBack.call(response);
			return;
		}
		// 异步请求数据
		doAsynRequest(GET, null, context, callBack, requestUrl, isCache, isShowDialog, url);
	}

	/**
	 * 异步请求网络数据
	 * 
	 * @param sendType
	 *            请求类型
	 * @param map
	 *            参数
	 * @param context
	 * @param callBack
	 *            回调对象
	 * @param url
	 * @param isCache
	 *            是否缓存
	 * @param isShowDialog
	 *            是否有提示框
	 * @param intUrl
	 */
	private static void doAsynRequest(final int sendType, final Map<String, String> map, final Context context, final ObserverCallBack callBack,
			final String url, final boolean isCache, final boolean isShowDialog, final String intUrl) {

		// if(isShowDialog) ((Activity)context).showDialog(1); // 加载对话框提示等待。。
		// 请求
		ThreadPoolUtils.execute(new MyRunnable(sendType, map, context, callBack, url, isCache, isShowDialog, intUrl));
	}

	/**
	 * 
	 * 访问网络初始化函数 支持Post请求方式
	 * 
	 * @param context
	 * @param callBack
	 *            回调执行函数 支持线程
	 * @param url
	 *            每个执行url
	 * @param map
	 *            参数
	 * @param isCache
	 *            是否本地缓存
	 * @param isShowDialog
	 *            是否弹出提示等待框
	 */
	public static void requestByPost(Context context, final ObserverCallBack callBack, String uri, Map<String, String> map, boolean isCache,
			boolean isShowDialog) {

		// 组织URL
		StringBuffer buffer = new StringBuffer();
		buffer.append(Constants.http.http_request_head);
		// buffer.append(context.getResources().getString(url));

		String requestUrl = buffer.toString();
		// requestUrl = requestUrl.substring(0,requestUrl.length()-1);
		LogUtil.i("httpurl", requestUrl);
		// 异步请求数据
		doAsynRequest(POST, map, context, callBack, requestUrl, isCache, isShowDialog, uri);
	}

	public static void requestSimpleByPost(Context context, final ObserverCallBack callBack, String uri, Map<String, String> map) {

		// 组织URL
		StringBuffer buffer = new StringBuffer();
		buffer.append(Constants.http.http_request_head);

		String requestUrl = buffer.toString();
		// requestUrl = requestUrl.substring(0,requestUrl.length()-1);
		LogUtil.i("httpurl", requestUrl);
		// 异步请求数据
		doAsynRequest(POST, map, context, callBack, requestUrl, false, true, uri);
	}

	/**
	 * 
	 * 访问网络初始化函数 支持Post请求方式
	 * 
	 * @param context
	 * @param http_head
	 *            请求头传null或者在C.http类中配置
	 * @param callBack
	 *            回调执行函数 支持线程
	 * @param url
	 *            每个执行url
	 * @param map
	 *            参数
	 * @param isCache
	 *            是否本地缓存
	 * @param isShowDialog
	 *            是否弹出提示等待框
	 */
	public static void requestByPost(Context context, String http_head, final ObserverCallBack callBack, String url, Map<String, String> map, boolean isCache,
			boolean isShowDialog) {

		// 组织URL
		StringBuffer buffer = new StringBuffer();
		if (http_head != null)
			buffer.append(http_head);
		else
			buffer.append(Constants.http.http_request_head);

		String requestUrl = buffer.toString();
		// requestUrl = requestUrl.substring(0,requestUrl.length()-1);
		LogUtil.i("httpurl", requestUrl + map.toString());
		// 判断网络是否可用
		if (!Network.checkNetWork(context)) { // 如果不可用 看本地否有数据
			// sendBroadcastReceiverMessage(context,
			// R.string.network_show_connectionless);
			callBack.call(null);
			return;
		}

		// 异步请求数据
		doAsynRequest(POST, map, context, callBack, requestUrl, isCache, isShowDialog, url);
	}

}

class MyRunnable implements Runnable {
	final int sendType;
	final Map<String, String> map;
	final Context context;
	final ObserverCallBack callBack;
	final String url;
	final boolean isCache;
	final boolean isShowDialog;
	final String intUrl;

	public MyRunnable(final int sendType, final Map<String, String> map, final Context context, final ObserverCallBack callBack, final String url,
			final boolean isCache, final boolean isShowDialog, final String intUrl) {
		this.sendType = sendType;
		this.map = map;
		this.context = context;
		this.callBack = callBack;
		this.url = url;
		this.isCache = isCache;
		this.isShowDialog = isShowDialog;
		this.intUrl = intUrl;

	}

	@Override
	public void run() {
		String data = null;
		try {
			// 设置请求头超时请求参数
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, AnsynHttpRequest.REQUEST_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, AnsynHttpRequest.SO_TIMEOUT);
			if (AnsynHttpRequest.mHttpClient == null) {
				// AnsynHttpRequest.mHttpClient = new
				// DefaultHttpClient(httpParams);
				DefaultHttpClient client = new DefaultHttpClient(httpParams);
				ClientConnectionManager mgr = client.getConnectionManager();
				HttpParams params = client.getParams();
				client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
				AnsynHttpRequest.mHttpClient = client;
			}
			// HttpClient httpClient = HttpUtils.getNewHttpClient(context);
			HttpResponse response = null;
			switch (sendType) {
			case AnsynHttpRequest.GET: // get 方式提交
				HttpGet get = new HttpGet(url);
				// get.setHeader("User-Agent", sUserAgent.toString());
				response = AnsynHttpRequest.mHttpClient.execute(get);
				break;
			case AnsynHttpRequest.POST: // post 方式提交
				HttpPost post = new HttpPost(url);
				// post.setHeader("User-Agent", sUserAgent.toString());
				LogUtil.i("httpurl", url + map.toString());
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				if (map != null && map.size() > 0)
					for (String key : map.keySet()) {
						params.add(new BasicNameValuePair(key, map.get(key)));
					}
				HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
				post.setEntity(entity);
				response = AnsynHttpRequest.mHttpClient.execute(post);
				break;
			default:
				break;
			}

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// httpClient.getCookieStore().getCookies().g
				data = EntityUtils.toString(response.getEntity());
				if (isCache) {// 把数据缓存到本地
					DBHelper.getInstance(context).addOrUpdateURLData(url, data);
				}
			} else {
				if (!isCache) {
					// AnsynHttpRequest.sendBroadcastReceiverMessage(context,
					// "11111111111");
				}
				data = null;
			}
		} catch (Exception e) {
			LogUtil.e(AnsynHttpRequest.tag, e.getMessage());
			if (!isCache) {
				// AnsynHttpRequest.sendBroadcastReceiverMessage(context,
				// R.string.network_show_error);
			}
			data = null;
		}

		try { // 回调数据
			if (callBack != null) {
				if (data != null && data.length() > 0) {
					Response response = JSONObject.parseObject(data, Response.class);
					callBack.call(response);
				}
			}
		} catch (Exception e) {
			LogUtil.e(AnsynHttpRequest.tag, e.getMessage());
		}
		try {
			if (isShowDialog)
				mHandler.sendEmptyMessage(0); // 移除加载对话框
			return;
		} catch (Exception e) {
			LogUtil.e(AnsynHttpRequest.tag, e.getMessage());
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			((Activity) context).removeDialog(1);// 移除加载对话框
		};
	};

}
