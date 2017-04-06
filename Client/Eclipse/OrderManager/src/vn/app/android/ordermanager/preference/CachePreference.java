package vn.app.android.ordermanager.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * CachePreference Preference
 * @author thaonp
 */
public class CachePreference {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Log cat tag.
	 */
	public static final String TAG = CachePreference.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get User Preference
	 * @param context
	 * @return
	 */
	public static SharedPreferences getInstance(Context context) {
		return context.getSharedPreferences(TAG, Activity.MODE_PRIVATE);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Search Keyword.
	 * @param context
	 * @param keyword
	 */
	public static void putSearchKeyword(Context context, String keyword) {
		SharedPreferences preference = getInstance(context);
		Editor editor = preference.edit();
		editor.putString("search_keyword", keyword);
		editor.commit();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Search Keyword.
	 * @param context
	 * @return
	 */
	public static String getSearchKeyword(Context context) {
		SharedPreferences preference = getInstance(context);
		return preference.getString("search_keyword", "");
	}

	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put List Current Articles Type. </br></br>
	 * 1 : <code>vn.bsv.app.android.hlc.fragment.ListArticleFragment.TYPE_FEED</code> <br/>
	 * 2 : <code>vn.bsv.app.android.hlc.fragment.ListArticleFragment.TYPE_NEWLY</code> <br/>
	 * @param context
	 * @param keyword
	 */
	public static void putCurrentListAritcleType(Context context, int listArticleType) {
		SharedPreferences preference = getInstance(context);
		Editor editor = preference.edit();
		editor.putInt("current_list_articles_type", listArticleType);
		editor.commit();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put List Current Articles Type. </br>
	 * @param context
	 * @return
	 * 1 : <code>vn.bsv.app.android.hlc.fragment.ListArticleFragment.TYPE_FEED</code> <br/>
	 * 2 : <code>vn.bsv.app.android.hlc.fragment.ListArticleFragment.TYPE_NEWLY</code> <br/>
	 * Default : <code>vn.bsv.app.android.hlc.fragment.ListArticleFragment.TYPE_FEED</code>
	 */
//	public static int getCurrentListAritcleType(Context context) {
//		SharedPreferences preference = getInstance(context);
//		return preference.getInt("current_list_articles_type", V101ProductFragment.TYPE_FEED);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Email
	 * @param context
	 * @param email
	 */
	public static void putEmail(Context context, String email) {
		SharedPreferences preference = getInstance(context);
		Editor editor = preference.edit();
		editor.putString("user_email", email);
		editor.commit();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Email
	 * @param context
	 * @return
	 */
	public static String getEmail(Context context) {
		SharedPreferences preference = getInstance(context);
		return preference.getString("user_email", "");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Password
	 * @param context
	 * @param password
	 */
	public static void putPassword(Context context, String password) {
		SharedPreferences preference = getInstance(context);
		Editor editor = preference.edit();
		editor.putString("user_password", password);
		editor.commit();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Password
	 * @param context
	 * @return
	 */
	public static String getPassword(Context context) {
		SharedPreferences preference = getInstance(context);
		return preference.getString("user_password", "");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Expert User Id.
	 * @param context
	 * @param password
	 */
	public static void putExpertUserId(Context context, String expertUserId) {
		SharedPreferences preference = getInstance(context);
		Editor editor = preference.edit();
		editor.putString("expert_user_id", expertUserId);
		editor.commit();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Expert User Id.
	 * @param context
	 * @return
	 */
	public static String getExpertUserId(Context context) {
		SharedPreferences preference = getInstance(context);
		return preference.getString("expert_user_id", "");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Is Edited Category.
	 * @param context
	 */
	public static void putIsEditedCategory(Context context, boolean isEditedCategory) {
		SharedPreferences preference = getInstance(context);
		Editor editor = preference.edit();
		editor.putBoolean("is_edited_category", isEditedCategory);
		editor.commit();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Is Edited Category.
	 * @param context
	 * @return
	 */
	public static boolean getIsEditedCategory(Context context) {
		SharedPreferences preference = getInstance(context);
		return preference.getBoolean("is_edited_category", false);
	}
	//--------------------------------------------------------------------------------------------------------------------
}
