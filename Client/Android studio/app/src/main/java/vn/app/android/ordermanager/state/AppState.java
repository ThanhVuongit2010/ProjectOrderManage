package vn.app.android.ordermanager.state;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import vn.app.android.ordermanager.MainActivity;
import vn.app.android.ordermanager.collection.SortedArrayList;
import vn.app.android.ordermanager.mode.User;
import vn.app.android.ordermanager.popup.ErrorDialogFragmentPopUp;
import vn.app.android.ordermanager.popup.ErrorPopUp;
import vn.app.android.ordermanager.rest.AsyncRestClient;

/**
 * Application State (Singleton)
 * @author MrVuong
 */
public class AppState {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Log cat tag.
	 */
	public static final String TAG = AppState.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	public static boolean sCanGoBack = true;
	//--------------------------------------------------------------------------------------------------------------------
	public static boolean sStartAppByUser = true;
	//--------------------------------------------------------------------------------------------------------------------
	public static String sScreenOpenByPush = "";
	//--------------------------------------------------------------------------------------------------------------------
	public static String startMainFragment = "";
	//--------------------------------------------------------------------------------------------------------------------
	public static String sListCategoryFrom = "";
	//--------------------------------------------------------------------------------------------------------------------
	public static boolean sIsErrorPopUpOpened = false;
	//--------------------------------------------------------------------------------------------------------------------
	public static String sFacebookFrom = "";
	//--------------------------------------------------------------------------------------------------------------------
	public static String sBackArticleId = "";
	//--------------------------------------------------------------------------------------------------------------------
	private static AppState msInstance;
	//--------------------------------------------------------------------------------------------------------------------
	private static boolean msIsLoaded = false;
	//--------------------------------------------------------------------------------------------------------------------
	private Context mApplicationContext;
	//--------------------------------------------------------------------------------------------------------------------
	private HashMap<String, Object> mStateMap = new HashMap<String, Object>();
	//--------------------------------------------------------------------------------------------------------------------
	private HashMap<String, Object> mParameterMap = new HashMap<String, Object>();
	//--------------------------------------------------------------------------------------------------------------------
	private ArrayList<String> mSafetyCacheKeyList = new SortedArrayList<String>();
	private ArrayList<AsyncRestClient> mRunningTaskList = new SortedArrayList<AsyncRestClient>();
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Running AsyncRestClient List
	 */
	//--------------------------------------------------------------------------------------------------------------------
	private AppState() {}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Application State singleton object.
	 * @return Application State singleton
	 */
	public static synchronized AppState getInstance() {
		if(msInstance == null) {
			msInstance = new AppState();
		}
		if(msIsLoaded == false) {
			initLoad();
		}
		return msInstance;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set application context.<br/>
	 * Warning: only set application context here to prevent memory leak.
	 * @param applicationContext
	 * @return
	 */
	public AppState setApplicationContext(Context applicationContext) {
		mApplicationContext = applicationContext;
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Application Context.
	 * @return
	 */
	public Context getApplicationContext() {
		return mApplicationContext;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Remove some unused state.
	 * @return
	 */
	public AppState clean() {
		HashMap<String, Object> swapStateMap = new HashMap<String, Object>(mStateMap);
		for(Entry<String, Object> entry : swapStateMap.entrySet()) {
			if(entry.getValue() == null) {
				mStateMap.remove(entry.getKey());
			}
		}
		swapStateMap.clear();
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Removes all mappings from this hash map state, leaving it empty.
	 * @return
	 */
	public AppState clear() {
		mStateMap.clear();
		mParameterMap.clear();
		clearRunningAsyncRestClientList();
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Removes all mappings from this hash map state, leaving it empty. This also stop all running AsyncRestClients.
	 * @return
	 */
	public AppState clear(boolean mayInterruptIfRunning) {
		mStateMap.clear();
		mParameterMap.clear();
		cancelAllRunningAsyncRestClients(mayInterruptIfRunning);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Returns whether this map of state is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return mStateMap.isEmpty();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Add running AsyncRestClient
	 * @param asyncRestClient
	 * @return
	 */
	public ArrayList<AsyncRestClient> addRunningAsyncRestClient(AsyncRestClient asyncRestClient) {
		mRunningTaskList.add(asyncRestClient);
		return mRunningTaskList;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get running AsyncRestClient List.
	 * @return Running AsyncRestClient List
	 */
	public ArrayList<AsyncRestClient> getRunningAsyncRestClientList() {
		return mRunningTaskList;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Cancel All Running AsyncRestClient.
	 * @param mayInterruptIfRunning
	 */
	public void cancelAllRunningAsyncRestClients(boolean mayInterruptIfRunning) {
		for(AsyncRestClient asyncRestClient : mRunningTaskList) {
			if(asyncRestClient != null) {
				asyncRestClient.cancel(mayInterruptIfRunning);
			}
		}
		mRunningTaskList.clear();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear running AsyncRestClient list.
	 * @return
	 */
	public ArrayList<AsyncRestClient> clearRunningAsyncRestClientList() {
		mRunningTaskList.clear();
		return mRunningTaskList;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Remove all finished AsyncRestClients.
	 * @return
	 */
	public ArrayList<AsyncRestClient> removeAllFinishAsyncRestClients() {
		for(int index = 0; index < mRunningTaskList.size(); index++) {
			AsyncRestClient asyncRestClient = mRunningTaskList.get(index);
			if(asyncRestClient != null && asyncRestClient.getStatus() == Status.FINISHED) {
				mRunningTaskList.remove(index);
			}
		}
		return mRunningTaskList;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear unused cache safely.
	 */
	public AppState clearCacheSafety() {
		for(String key : mSafetyCacheKeyList) {
			mStateMap.put(key, null);
			mStateMap.remove(key);
		}
		mSafetyCacheKeyList.clear();
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put main activity
	 * @param mainActivity
	 * @return
	 */
	public AppState putMainActivity(final MainActivity mainActivity) {
		mStateMap.put("main_activity", mainActivity);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Main Activity instance
	 * @return
	 */
	public MainActivity getMainActivity() {
		return (MainActivity) mStateMap.get("main_activity");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put sliding menu current open state flag
	 * @param openState <br/> -1 : Unknown <br/> 0 : No Menu Opened <br/> 1 : Left Menu Opened <br/> 2 : Right Menu Opened
	 * @return
	 */
	public AppState putFlagSlidinMenuOpenState(int openState) {
		String key = "sliding_menu_open_state";
		mStateMap.put(key, openState);
		mSafetyCacheKeyList.add(key);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get sliding menu current open state flag
	 * @return
	 */
	public Integer getFlagSlidinMenuOpenState() {
		return (Integer) mStateMap.get("sliding_menu_open_state");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put flag use GCM or not.
	 * @param isUseGcm
	 * @return Singleton object
	 */
	public AppState putFlagUseGcm(boolean isUseGcm) {
		mStateMap.put("use_gcm", isUseGcm);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get flat use GCM
	 * @return 
	 */
	public Boolean getFlagUseGcm() {
		return (Boolean) mStateMap.get("use_gcm");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put current main fragment tag.
	 * @param currentMainFragmentTag
	 * @return Singleton object
	 */
	public AppState putCurrentMainFragmentTag(String currentMainFragmentTag) {
		String key = "current_main_fragment";
		mStateMap.put(key, currentMainFragmentTag);
		mSafetyCacheKeyList.add(key);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set current main fragment tag.
	 * @return
	 */
	public String getCurrentMainFragmentTag() {
		return (String) mStateMap.get("current_main_fragment");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put User.
	 * @param user
	 * @return
	 */
	public AppState putUser(User user) {
		mStateMap.put("user", user);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get User.
	 * @return
	 */
//	public User getUser() {
////		//Get from MemCache first
////		User user = (User) mStateMap.get("user");
////		if(user == null) {
////			//Get from DiskCache second
////			user = UserPreference.get(mApplicationContext);
////			mStateMap.put("user", user);
////		}
//		return getUser(mApplicationContext);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get User with specific context
	 * @param context
	 * @return
	 */
	public User getUser(Context context) {
		//Get from MemCache first
		User user = (User) mStateMap.get("user");
//		if(user == null) {
//			//Get from DiskCache second
//			user = UserPreference.get(context);
//			mStateMap.put("user", user);
//		}
		return user;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Initialize loading state
	 */
	private static void initLoad() {
		msIsLoaded = true;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Is Logged In.
	 * @return
	 */
//	public boolean isLoggedIn() {
//		boolean isLoggedIn = false;
//		User user = AppState.getInstance().getUser();
//		if(user != null) {
//			if((!StringUtil.isEmpty(user.getId())) && (user.getType() != User.GUEST)) {
//				isLoggedIn = true;
//			}
// 		}
//		return isLoggedIn;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Integer.
	 * @param key
	 * @param value
	 * @return
	 */
	public AppState putInteger(String key, int value) {
		mParameterMap.put(key, value);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Integer
	 * @param key
	 * @return
	 */
	public int getInteger(String key) {
		return ((Integer) mParameterMap.get(key)).intValue();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Boolean.
	 * @param key
	 * @param value
	 * @return
	 */
	public AppState putBoolean(String key, boolean value) {
		mParameterMap.put(key, value);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Boolean.
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		return ((Boolean) mParameterMap.get(key)).booleanValue();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put String.
	 * @param key
	 * @param value
	 * @return
	 */
	public AppState putString(String key, String value) {
		mParameterMap.put(key, value);
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get String.
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return (String) mParameterMap.get(key);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Show Internet Error Pop Up.
	 * @param activity
	 * @return
	 */
	public ErrorPopUp showErrorPopUp(final Activity activity,String title, String message) {
		ErrorPopUp mErrorPopUp;
		mErrorPopUp = new ErrorPopUp(activity, null,title, message);
		if(!mErrorPopUp.isShowing() && (!AppState.sIsErrorPopUpOpened)) {
			mErrorPopUp.show();
			AppState.sIsErrorPopUpOpened = true;
		}
		return mErrorPopUp;
	}
	/**
	 * Show Internet Error Dialog Fragment Pop Up.
	 * @param activity
	 * @return
	 */
	public ErrorDialogFragmentPopUp showErrorDiaLogFragmentPopUp(final Activity activity, String title, String message) {
		ErrorDialogFragmentPopUp mErrorPopUp;
		mErrorPopUp = new ErrorDialogFragmentPopUp(activity, title, message);
		Fragment dialogFragment = activity.getFragmentManager().findFragmentByTag("ErrorDialogFrament");
		FragmentManager fm = activity.getFragmentManager();
//		mErrorPopUp.show(fm,"ErrorDialogFrament");
		if(dialogFragment == null && (!AppState.sIsErrorPopUpOpened)) {
			mErrorPopUp.show(fm,"ErrorDialogFrament");
			AppState.sIsErrorPopUpOpened = true;
		}
		return mErrorPopUp;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Internal WevView Domain.
	 * @param url
	 * @return
	 */
//	public AppState putInternalWebViewDomain(String url) {
//		mStateMap.put("internal_url_domain", TaskUtil.getUrlDomain(url));
//		return this;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Internal WevView Domain.
	 * @return
	 */
	public String getInternalWebViewDomain() {
		String domain = (String) mStateMap.get("internal_url_domain");
		return domain != null ? domain : "";
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put List Article Data Source.
	 * @param dataSource
	 * @return
	 */
//	public AppState putListArticleDataSource(SortedArrayList<Article> dataSource) {
//		if(dataSource != null) {
//			mStateMap.put("list_article_data_source", dataSource);
//		}
//		return this;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get List Article Data Source.
	 * @return
	 */
	@SuppressWarnings("unchecked")
//	public SortedArrayList<Article> getListArticleDataSource() {
//		SortedArrayList<Article> domain = (SortedArrayList<Article>) mStateMap.get("list_article_data_source");
//		return domain != null ? domain : new SortedArrayList<Article>();
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put List Ranking Data Source.
	 * @param dataSource
	 * @return
	 */
//	public AppState putListRankingDataSource(SortedArrayList<Article> dataSource) {
//		if(dataSource != null) {
//			mStateMap.put("list_ranking_data_source", dataSource);
//		}
//		return this;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get List Article Data Source.
	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public SortedArrayList<Article> getListRankingDataSource() {
//		SortedArrayList<Article> domain = (SortedArrayList<Article>) mStateMap.get("list_ranking_data_source");
//		return domain != null ? domain : new SortedArrayList<Article>();
//	}
	
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put List Category Data Source.
	 * @param dataSource
	 * @return
	 */
//	public AppState putListCategoryDataSource(SortedArrayList<Category> dataSource) {
//		if(dataSource != null) {
//			mStateMap.put("list_category_data_source", dataSource);
//		}
//		return this;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get List Article Data Source.
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public SortedArrayList<Category> getListCategoryDataSource() {
//		SortedArrayList<Category> domain = (SortedArrayList<Category>) mStateMap.get("list_category_data_source");
//		return domain != null ? domain : new SortedArrayList<Category>();
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Internal WevView Domain.
	 * @param url
	 * @return
	 */
//	public AppState putRankingInfo(RankingInfo rankinInfo) {
//		mStateMap.put("selected_ranking_information", rankinInfo);
//		return this;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Internal WevView Domain.
	 * @return
	 */
//	public RankingInfo getRankingInfo() {
//		return (RankingInfo) mStateMap.get("selected_ranking_information");
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Put Writer Ids Parameters.
	 * @param writerIdsParameter
	 */
	public void putWriterIdsParameter(String writerIdsParameter) {
		mStateMap.put("writer_ids_parameter", writerIdsParameter);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Writer Ids Parameters.
	 * @return
	 */
	public String getWriterIdsParameter() {
		return (String) mStateMap.get("writer_ids_parameter");
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Category Map
	 * @return
	 */
//	public HashMap<String, Category> getCategoryMap() {
//		return mCategoryMap;
//	}
	//--------------------------------------------------------------------------------------------------------------------
}