package vn.app.android.ordermanager.rest;

/**
 * Application Common URL
 * @author thaonp
 */
public class AppUrl {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Test Video.
	 */
	public static final String TEST_VIDEO = "http://broken-links.com/tests/video/";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Default Login Hash for WebView.
	 */
	public static final String DEFAULT_LOGIN_HASH = "9999";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Application Internal Domain (Server)
	 */
	public static final String APP_INTERNAL_DOMAIN = ServerApi.SERVER_HOST;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * About URL.
	 */
	public static final String ABOUT = APP_INTERNAL_DOMAIN + "/about";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Term of Use URL.
	 */
	public static final String USE_TERM = APP_INTERNAL_DOMAIN + "/rules";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Policy URL.
	 */
	public static final String POLICY = APP_INTERNAL_DOMAIN + "/policy";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Profile URL.
	 */
	public static final String PROFILE = APP_INTERNAL_DOMAIN + "/user/{user_id}";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Forget password
	 */
	public static final String FORGET_PASSWORD = APP_INTERNAL_DOMAIN + "/password/forget";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get list user notification from server.
	 */
	public static final String USER_NOTIFICATION = APP_INTERNAL_DOMAIN + "/notice";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get administrator notification detail from server. Remember replace {notification_id} by notification_id
	 */
	public static final String ADMIN_NOTIFICATION_DETAIL = APP_INTERNAL_DOMAIN + "/news/{notification_id}";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * User Detail URL. Please replace {user_id} by the user id of user that want to view detail.
	 */
	public static final String USER_DETAIL = APP_INTERNAL_DOMAIN + "/user/{user_id}";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Article Details. Please replace {article_id} by the article id of article that want to view detail.
	 */
	public static final String ARTICLE_DETAIL = APP_INTERNAL_DOMAIN + "/{article_id}";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * OAuth Facebook URL. Associate Facebook Information into User Account.
	 */
	public static final String OAUTH_FACEBOOK = APP_INTERNAL_DOMAIN + "/api/v1/oauth/facebook/{user_id}";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * OAuth Facebook to Post Comment URL.
	 */
	public static final String OAUTH_FACEBOOK_POST_COMMENT = APP_INTERNAL_DOMAIN + "/api/v1/oauth/share/facebook?comment={comment}&article_id={article_id}";
	//--------------------------------------------------------------------------------------------------------------------
}
