package vn.app.android.ordermanager.util;

import android.graphics.Paint;
import android.util.Patterns;
import android.widget.TextView;

/**
 * String Utilities
 * @author MrVuong
 */
public class StringUtil {
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Check a string is whether empty or not.
	 * @param s The string need to check empty.
	 * @return [true] if string is empty or null. Otherwise is [false].
	 */
	public static boolean isEmpty(String s) {
		if(s != null) {
			s = s.trim();
		}
		return (s == null || "".equals(s));
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Generalize integer number with specific separator and segment length.
	 * @param number
	 * @param separator Eg. "," or "." ...
	 * @param segmentLenght
	 * @return
	 */
	public static String generalizeIntegerNumber(long number, String separator, int segmentLenght) {
		return generalizeIntegerNumber(String.valueOf(number), separator, segmentLenght);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Generalize string number with specific separator and segment length.
	 * @param numberString
	 * @param separator Eg. "," or "." ...
	 * @param segmentLenght
	 * @return
	 */
	public static String generalizeIntegerNumber(String numberString, String separator, int segmentLenght) {
		String genString = "";
		if(numberString != null) {
			for(int i = numberString.length() - 1; i >= 0; i-=segmentLenght) {
				if(i-segmentLenght >= 0) {
					genString = numberString.substring(i-segmentLenght+1, i+1) + (genString.isEmpty() ? "" : separator + genString);
				}
				else {
					genString = numberString.substring(0, i+1) + (genString.isEmpty()  ? "" : separator + genString);
				}
			}
		}
		return genString;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Validate email pattern
	 * @param email
	 * @return return true if valid, otherwise is false
	 */
	public static boolean validateEmail(String email) {
		if(StringUtil.isEmpty(email)) {
			return false;
		}
		return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Strike through text of TextView
	 * @param textView
	 * @return
	 */
	public static TextView strikeThroughTextView(TextView textView) {
		textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		return textView;

	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Remove strike through text view text flag.
	 * @param textView
	 * @return
	 */
	public static TextView removeStrikeThroughTextView(TextView textView) {
		textView.setPaintFlags(textView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		return textView;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Validate Request Parameter. If parameter is <code>null</code>, it be be assigned to "" (empty string).
	 * @param parameter
	 */
	public static String validateRequestParameter(String parameter) {
		return parameter==null ? "" : parameter;
	}
	//-------------------------------------------------------------------------------------------------------------------
}
