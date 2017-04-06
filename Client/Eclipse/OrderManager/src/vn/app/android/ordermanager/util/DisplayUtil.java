package vn.app.android.ordermanager.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Display Utilities.
 * @author thaonp
 */
public class DisplayUtil {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Convert from dip unit to pixel unit.
	 * @param context Context to get resource display metrics.
	 * @param dp dp value needs to converted.
	 * @return pixel value.
	 */
	public static int dpToPx(Context context, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Convert from pixel unit to dip unit.
	 * @param context Context to get resource display metrics.
	 * @param px pixel value needs to converted.
	 * @return dp value.
	 */
	public static int pxToDp(Context context, int px) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return (int) (px/displayMetrics.density);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Convert from pixel unit to physical dip unit.
	 * @param context Context to get resource display metrics.
	 * @param px pixel value needs to converted.
	 * @param isX true means that convert X value, otherwise is Y value
	 * @return dp value
	 */
	public static int pxToPhysicalDp(Context context, int px, boolean isX) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		float baseline = displayMetrics.densityDpi/displayMetrics.density;	//This is about 160dpi
		if(isX) {
			return (int) ((px/displayMetrics.xdpi) * baseline);
		}
		else {
			return (int) ((px/displayMetrics.ydpi) * baseline);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get screen display size in pixel unit.
	 * @param activity Activity to get default display.
	 * @return Display size inn pixel unit.
	 */
	public static Point getDisplaySizeInPixel(Activity activity) {
		DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
		return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get screen display size in dip unit.
	 * @param activity Activity to get default display.
	 * @return Display size inn dip unit.
	 */
	public static Point getDisplaySizeInDp(Activity activity) {
		DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
		int width = (int) (displayMetrics.widthPixels/displayMetrics.density);
		int height = (int) (displayMetrics.heightPixels/displayMetrics.density);
		return new Point(width, height);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get screen display size in physical dip unit.
	 * @param activity Activity to get default display.
	 * @return Display size in physical dip unit.
	 */
	public static Point getDisplaySizeInPhysicalDp(Activity activity) {
		DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
		float baseline = displayMetrics.densityDpi/displayMetrics.density;	//This is about 160dpi
		int width = (int) ((displayMetrics.widthPixels/displayMetrics.xdpi) * baseline);
		int height = (int) ((displayMetrics.heightPixels/displayMetrics.ydpi) * baseline);
		return new Point(width, height);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get status bar height
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int statusBarHeight = 0;
		Resources resource = context.getResources();
		int resourceId = resource.getIdentifier("status_bar_height", "dimen", "android");
	    if (resourceId > 0) {
	    	statusBarHeight = resource.getDimensionPixelSize(resourceId);
	    }
	    return statusBarHeight;
	}
	//--------------------------------------------------------------------------------------------------------------------
}
