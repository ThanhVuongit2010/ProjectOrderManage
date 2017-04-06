package vn.app.android.ordermanager.rest;


import vn.app.android.ordermanager.state.Flag;

/**
 * Server API
 * @author thaonp
 */
public class ServerApi {
	//--------------------------------------------------------------------------------------------------------------------
	public static final String TAG = ServerApi.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * GCM Sender ID for Development.
	 */
	public static final String GCM_SENDER_ID_DEV = "570580746909";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * GCM Sender ID for Production.
	 */
	public static final String GCM_SENDER_ID_PRO = "218295902366";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * GCM Sender ID.
	 */
	public static final String GCM_SENDER_ID = Flag.USED_SERVER_DEV ? GCM_SENDER_ID_DEV : GCM_SENDER_ID_PRO;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * URL Keyword Separator.
	 */
	public static final String SEPARATOR_URL_KEYWORD = ",";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Developer API Host.
	 */
	public static final String SERVER_HOST_DEV = "http://dev.healthnudge.jp";	//"http://54.65.63.47"
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Production API Host.
	 */
	public static final String SERVER_HOST_PRO = "http://healthnudge.jp";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * API Host.
	 */
	public static final String SERVER_HOST = "http://192.168.1.5:100";
//	public static final String SERVER_HOST = "http://192.168.43.203:100";
	//--------------------------------------------------------------------------------------------------------------------
	public static final String AUTHORIZE_ID_DEV = "healthnudge";
	//--------------------------------------------------------------------------------------------------------------------
	public static final String AUTHORIZE_ID_PRO = "healthnudge";
	//--------------------------------------------------------------------------------------------------------------------
	public static final String AUTHORIZE_PASSWORD_DEV = "425x9w4h";
	//--------------------------------------------------------------------------------------------------------------------
	public static final String AUTHORIZE_PASSWORD_PRO = "425x9w4h";
	//--------------------------------------------------------------------------------------------------------------------
	public static final String AUTHORIZE_ID = Flag.USED_SERVER_DEV ? AUTHORIZE_ID_DEV : AUTHORIZE_ID_PRO;
	//--------------------------------------------------------------------------------------------------------------------
	public static final String AUTHORIZE_PASSWORD = Flag.USED_SERVER_DEV ? AUTHORIZE_PASSWORD_DEV : AUTHORIZE_PASSWORD_PRO;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Register Guest <br/>
	 * Method: POST
	 */
	public static final String API_REGISTER_ACOUNT = SERVER_HOST + "/api/Register/PostRegisterAcount";
	public static final String API_LOGIN_USER = SERVER_HOST + "/api/Login/PostUserLogin";
	//--------------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------------
}
