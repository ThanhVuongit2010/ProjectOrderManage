package vn.app.android.ordermanager.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Play Store Helper
 * @author thaonp
 */
public class PlayStoreHelper {
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Open Android Application URL on Play store. <br/>
	 * Default will launch Play application to open URL. It will fall back to other application such as Web Browser...
	 * @param context
	 * @param playStoreUrl
	 * @return
	 */
	public static boolean openPlayStoreUrl(Context context, String playStoreUrl) {
		if(StringUtil.isEmpty(playStoreUrl)) {
			return false;
		}
		String tokens[] = playStoreUrl.split("[?]");
		if(tokens.length < 2) {
			return false;
		}
		String hostTokens[] = tokens[0].split("/");
		if(hostTokens.length < 2) {
			return false;
		}
		try {
			Uri uri = Uri.parse("market://" + hostTokens[hostTokens.length-1] + "?" + tokens[1]);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(intent);
		}
		catch(ActivityNotFoundException e) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl));
			context.startActivity(intent);
		}
		return true;
	}
	//-------------------------------------------------------------------------------------------------------------------
}
