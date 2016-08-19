package vn.app.android.ordermanager.util;

import java.util.HashMap;

/**
 * Restful API Result Utilities.
 * @author thaonp
 */
public class ResultUtil {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Status Code.
	 * @param results
	 */
	public static Integer getStatusCode(HashMap<String, Object> results) {
		return (Integer) results.get("status_code");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Status Code.
	 * @param results
	 * @param statusCode
	 */
	public static void putStatusCode(HashMap<String, Object> results, int statusCode) {
		results.put("status_code", statusCode);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Error.
	 * @param results
	 */
	public static void putError(HashMap<String, Object> results, String error) {
		results.put("error", error);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Error.
	 * @param results
	 */
	public static String getError(HashMap<String, Object> results) {
		return (String) results.get("error");
	}
	//--------------------------------------------------------------------------------------------------------------------
}
