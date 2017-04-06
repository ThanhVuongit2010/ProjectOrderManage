package vn.app.android.ordermanager.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Soft Keyboard Utilities 
 * @author thaonp
 */
public class KeyboardUtil {
	//--------------------------------------------------------------------------------------------------------------------
	public static final String TAG = KeyboardUtil.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Force hide soft keyboard with flag : InputMethodManager.HIDE_NOT_ALWAYS.
	 * @param context Context to get system service input method.
	 * @param viewBinder View has current focus. Can achived by View.getWindowToken().
	 */
	public static void hideKeyboard(Context context, View viewBinder) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(viewBinder.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Force hide soft keyboard with specific flag.
	 * @param context Context to get system service input method.
	 * @param viewBinder View has current focus. Can achived by View.getWindowToken().
	 * @param flags InputMethodManager flags.
	 */
	public static void hideKeyboard(Context context, View viewBinder, int flags) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(viewBinder.getWindowToken(), flags);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Toggle Soft Input.
	 * @param context
	 */
	public static void toggleSoftInput(Context context) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Toggle Soft Input.
	 * @param context
	 * @param showFlags
	 */
	public static void toggleSoftInput(Context context, int showFlags) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(showFlags, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Toggle Soft Input.
	 * @param context
	 * @param showFlags
	 * @param hideFlags
	 */
	public static void toggleSoftInput(Context context, int showFlags, int hideFlags) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(showFlags, hideFlags);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Force show soft keyboard with flag : InputMethodManager.SHOW_IMPLICIT.
	 * @param context Context to get system service input method.
	 * @param viewBinder View has current focus.
	 */
	public static void showKeyboard(Context context, View view) {
		if(view != null) {
			view.requestFocus();
		}
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Force show soft keyboard with specific flag.
	 * @param context Context to get system service input method.
	 * @param viewBinder View has current focus.
	 */
	public static void showKeyboard(Context context, View view, int flags) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(view, flags);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Force show soft keyboard with show flag = InputMethodManager.SHOW_FORCED and hide flag = 0.
	 * @param context Context to get system service input method.
	 * @param viewBinder View has current focus.
	 */
	public static void forceShowSoftInput(Context context, View view) {
		if(view != null) {
			view.requestFocus();
		}
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);	//Hide Flag = 0
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Force hide soft keyboard with hide flag = 0.
	 * @param context Context to get system service input method.
	 * @param viewBinder View has current focus.
	 */
	public static void forceHideSoftInput(Context context, View view) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Hide soft input from window.
	 * @param activity
	 */
	public static void forceHideSoftInput(Activity activity) {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
		catch(Exception e) {
			
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Check whether is keyboard showing or hiding
	 * @param rootView
	 * @return
	 */
	public static boolean isKeyboardShown(View rootView) {
		Rect rect = new Rect();
		rootView.getWindowVisibleDisplayFrame(rect);
	    /* 
	     * 128dp = 32dp * 4
	     * Minimum button height 32dp and generic 4 rows soft keyboard (can be more than 4 rows)
	     */
	    final int SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;
	    DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
	    return dm.heightPixels - rect.bottom > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density;
	}
	//--------------------------------------------------------------------------------------------------------------------
}
