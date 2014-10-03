package com.xinfan.blueblue.request;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

public class ActivityUtils {

	public static void closeInput(Activity context) {

		try {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			imm.hideSoftInputFromWindow(context.getCurrentFocus()
					.getWindowToken(), 0);

		} catch (Exception e) {

		}

		/*
		 * // 得到InputMethodManager的实例 if (imm.isActive()) { // 如果开启 imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS); //
		 * 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的 }
		 */

	}

	public static String getPhone(Activity context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneId = tm.getLine1Number();
		return phoneId;
	}
}
