package vn.app.android.ordermanager.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * PackageUtil 
 * @author thaonp
 */
public class PackageUtil {
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get the application version (Version Code)
	 * @param context
	 * @return
	 */
	public static int getApplicationVersion(Context context) {
		try {
	        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } 
		catch(NameNotFoundException e) {
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	//-------------------------------------------------------------------------------------------------------------------
}
