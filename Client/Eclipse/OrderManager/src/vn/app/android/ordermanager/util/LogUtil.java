package vn.app.android.ordermanager.util;

import java.util.ArrayList;

import android.util.Log;

import com.order.ordermanager.BuildConfig;

public class LogUtil {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Send a {@link #DEBUG} log message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static void d(String tag, String message) {
		if(BuildConfig.DEBUG) {
			Log.d(tag, message);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Send a {@link #DEBUG} log message and log the exception.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 * @param throwable An exception to log
	 */
	public static void d(String tag, String message, Throwable throwable) {
		if(BuildConfig.DEBUG) {
			Log.d(tag, message, throwable);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Send an {@link #ERROR} log message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 */
	public static void e(String tag, String message) {
		if(BuildConfig.DEBUG) {
			Log.e(tag, message);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Send a {@link #ERROR} log message and log the exception.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message you would like logged.
	 * @param throwable An exception to log
	 */
	public static void e(String tag, String message, Throwable throwable) {
		if(BuildConfig.DEBUG) {
			Log.e(tag, message, throwable);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Log all Name-Value Pair parameters
	 * @param parameters
	 * @param tag
	 * @param prefix
	 */
//	public static void logNameValuePairParameter(String tag, ArrayList<NameValuePair> parameters, String prefix) {
//		for(NameValuePair pair : parameters) {
//			LogUtil.d(tag, (prefix==null ? "" : prefix) + pair.getName() + "=" + pair.getValue());
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
}
