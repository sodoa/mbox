package com.xinfan.blueblue.util;

import android.content.Context;
import android.widget.Toast;

import com.xinfan.blueblue.exception.AppException;

public class ToastUtil
{
  private static Toast toast;

  public static void cancel()
  {
    if (toast != null)
      toast.cancel();
  }

  public static void onStop()
  {
    cancel();
  }

  public static Toast showToast(Context paramContext, int paramInt, boolean paramBoolean)
  {
	  return null;
  }

  public static Toast showToast(Context paramContext, CharSequence paramCharSequence, boolean paramBoolean)
  {
	  return null;
  }

  public static Toast showToast(Context paramContext, AppException paramAppException)
  {
    return showToast(paramContext, paramAppException, "");
  }

  public static Toast showToast(Context paramContext, AppException paramAppException, String paramString)
  {
    Toast localToast = null;
    if (paramContext == null);
    {
      if ((paramAppException != null) && (paramAppException.hasStringResourceId()))
        localToast = showToastShort(paramContext, paramString + paramContext.getResources().getString(paramAppException.getStringResourceId()));
    }
    
    return localToast;
  }

  public static Toast showToastLong(Context paramContext, int paramInt)
  {
    return showToast(paramContext, paramInt, true);
  }

  public static Toast showToastLong(Context paramContext, String paramCharSequence)
  {
    return showToast(paramContext, paramCharSequence, true);
  }

  public static Toast showToastShort(Context paramContext, int paramInt)
  {
    return showToast(paramContext, paramInt, false);
  }

  public static Toast showToastShort(Context paramContext, CharSequence paramCharSequence)
  {
    return showToast(paramContext, paramCharSequence, false);
  }
}