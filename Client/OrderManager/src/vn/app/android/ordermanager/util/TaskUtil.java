package vn.app.android.ordermanager.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.app.android.ordermanager.MainActivity;
import vn.app.android.ordermanager.event.DrawerLayoutCallback;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.extra.TopNavigationInfo;
import vn.app.android.ordermanager.state.AppState;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.nineoldandroids.view.ViewHelper;
import com.order.ordermanager.R;

/**
 * Task Utilities
 * @author Mr Vuong
 */
public class TaskUtil {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Log cat tag.
	 */
	public static final String TAG = TaskUtil.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Build drawer layout for left and right menu. <br/>
	 * This should get the fragment container layout and DrawerLayout inside onCreate of the main activity first.<br/>
	 * Otherwise it will throw NullPointerException
	 * @param mainActivity
	 * @param drawerLayout
	 */
	@SuppressLint("RtlHardcoded")
	public static void buildDrawerLayout(final MainActivity mainActivity) {
		String error = TaskUtil.assertBuildDrawerLayout(mainActivity);
		if(!StringUtil.isEmpty(error)) {
			throw new NullPointerException(error);
		}

		//Set margin
		int leftMenuMargin = (int) mainActivity.getResources().getDimension(R.dimen.menu_left_margin);
		int rightMenuMargin = (int) mainActivity.getResources().getDimension(R.dimen.menu_right_margin);
		TaskUtil.setLeftMenuMargin(mainActivity, leftMenuMargin);
		TaskUtil.setRightMenuMargin(mainActivity, rightMenuMargin);

		final DrawerLayout drawerLayout = mainActivity.getDrawerLayout();
		final FrameLayout fragmentContainerLayout = mainActivity.getFragmentContainerLayout();

		LogUtil.d(TAG, "RightMenu: (" + mainActivity.getRightMenuLayout().getLeft() + ")");
		LogUtil.d(TAG, "FragmentContainter: (" + fragmentContainerLayout.getLeft() + ")");

		TaskUtil.setDrawerLayoutListenter(mainActivity);

		//Set dim (scrim) color
		drawerLayout.setScrimColor(Color.TRANSPARENT);

		//Set lock mode
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

		//Set Shadow
		Drawable drawable = mainActivity.getResources().getDrawable(R.drawable.sh_menu_shadow); 
		drawerLayout.setDrawerShadow(drawable, Gravity.LEFT);
		drawerLayout.setDrawerShadow(drawable, Gravity.RIGHT);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set DrawerLayout Listener.
	 * @param mainActivity
	 */
	public static void setDrawerLayoutListenter(final MainActivity mainActivity) {
		final DrawerLayout drawerLayout = mainActivity.getDrawerLayout();
		final FrameLayout fragmentContainerLayout = mainActivity.getFragmentContainerLayout();
		final DrawerLayoutCallback drawerLayoutCallback = mainActivity.getDrawerLayoutCallback();

		LogUtil.d(TAG, "RightMenu: (" + mainActivity.getRightMenuLayout().getLeft() + ")");
		LogUtil.d(TAG, "FragmentContainter: (" + fragmentContainerLayout.getLeft() + ")");

		//Set drawer listener
		drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			//State Changed
			@Override
			public void onDrawerStateChanged(int newState) {	//STATE_IDLE, STATE_DRAGGING or STATE_SETTLING.
				if(drawerLayoutCallback != null) {
					drawerLayoutCallback.onDrawerStateChanged(newState);
				}
			}
			//Sliding
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				float offset = drawerView.getId() == R.id.flLeftMenu ? slideOffset : -slideOffset;
				ViewHelper.setTranslationX(fragmentContainerLayout, offset * drawerView.getWidth());
				drawerLayout.bringChildToFront(drawerView);
				drawerLayout.requestLayout();
				if(drawerLayoutCallback != null) {
					drawerLayoutCallback.onDrawerSlide(drawerView, slideOffset);	
				}
			}
			//Opened
			@Override
			public void onDrawerOpened(View drawerView) {
				int state = drawerView.getId() == R.id.flLeftMenu ? 1 : 2;
				AppState.getInstance().putFlagSlidinMenuOpenState(state);
				if(drawerLayoutCallback != null) {
					drawerLayoutCallback.onDrawerOpened(drawerView);
				}
			}
			//Closed
			@Override
			public void onDrawerClosed(View drawerView) {
				AppState.getInstance().putFlagSlidinMenuOpenState(0);
				if(drawerLayoutCallback != null) {
					drawerLayoutCallback.onDrawerClosed(drawerView);
				}
				LogUtil.d(TAG, "DrawerView: (" + drawerView.getLeft() + ")");
				LogUtil.d(TAG, "FragmentContainter: (" + fragmentContainerLayout.getLeft() + ")");
			}
		});
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Assert can build drawer layout
	 * @param mainActivity
	 * @return
	 */
	private static String assertBuildDrawerLayout(final MainActivity mainActivity) {
		if(mainActivity == null) {
			return "Main activity is NULL";
		}
		if(mainActivity.getDrawerLayout() == null) {
			return "Drawer layout is NULL";
		}
		if(mainActivity.getFragmentContainerLayout() == null) {
			return "Fragment container layout is NULL";
		}
		return "";
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set left menu margin of DrawerLayout.
	 * @param mainActivity
	 * @param margin margin value in pixel
	 */
	public static void setLeftMenuMargin(final MainActivity mainActivity, final int margin) {
		Point screenSize = DisplayUtil.getDisplaySizeInPixel(mainActivity);
		FrameLayout leftMenuLayout = mainActivity.getLeftMenuLayout();
		DrawerLayout.LayoutParams leftMenuParams = (DrawerLayout.LayoutParams) leftMenuLayout.getLayoutParams();
		leftMenuParams.width = screenSize.x - margin;
		leftMenuLayout.setLayoutParams(leftMenuParams);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set right menu margin of DrawerLayout.
	 * @param mainActivity
	 * @param margin margin value in pixel
	 */
	public static void setRightMenuMargin(final MainActivity mainActivity, final int margin) {
		Point screenSize = DisplayUtil.getDisplaySizeInPixel(mainActivity);
		FrameLayout rightMenuLayout = mainActivity.getRightMenuLayout();
		DrawerLayout.LayoutParams rightMenuParams = (DrawerLayout.LayoutParams) rightMenuLayout.getLayoutParams();
		rightMenuParams.width = screenSize.x - margin;
		rightMenuLayout.setLayoutParams(rightMenuParams);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Open left menu
	 * @param mainActivity
	 */
	public static void openLeftMenu(final MainActivity mainActivity) {
		if(mainActivity != null) {
			mainActivity.getDrawerLayout().openDrawer(mainActivity.getLeftMenuLayout());
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Open Right menu
	 * @param mainActivity
	 */
	public static void openRightMenu(final MainActivity mainActivity) {
		if(mainActivity != null) {
			mainActivity.getDrawerLayout().openDrawer(mainActivity.getRightMenuLayout());
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set left menu.
	 * @param mainActivity
	 * @param leftMenuView Left Menu Layout View
	 */
	public static void setLeftMenu(final MainActivity mainActivity, final View leftMenuView) {
		if(mainActivity != null) {
			FrameLayout leftMenuLayout = mainActivity.getLeftMenuLayout();
			leftMenuLayout.removeAllViews();
			if(leftMenuView != null) {
				leftMenuLayout.addView(leftMenuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set right menu
	 * @param mainActivity
	 * @param rightMenuView Right Menu Layout View
	 */
	public static void setRightMenu(final MainActivity mainActivity, final View rightMenuView) {
		if(mainActivity != null) {
			FrameLayout rightMenuLayout = mainActivity.getRightMenuLayout();
			rightMenuLayout.removeAllViews();
			if(rightMenuView != null) {
				rightMenuLayout.addView(rightMenuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set sliding menu (left + right)
	 * @param mainActivity
	 * @param leftMenuView Left Menu Layout View
	 * @param rightMenuView Right Menu Layout View
	 */
	public static void setMenu(final MainActivity mainActivity, final View leftMenuView, final View rightMenuView) {
		TaskUtil.setLeftMenu(mainActivity, leftMenuView);
		TaskUtil.setRightMenu(mainActivity, rightMenuView);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Build back button
	 * @param backButton
	 */
	public static void buildBackButton(final MainActivity mainActivity, final View backButton) {
		if(mainActivity == null || backButton == null) {
			return;
		}
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(AppState.sCanGoBack) {
					if(KeyboardUtil.isKeyboardShown(mainActivity.getMainLayout())) {
						KeyboardUtil.forceHideSoftInput(mainActivity);
						LogUtil.d(TAG, "Closing soft input..");
						return;
					}
					FragmentUtil.popBackStack(mainActivity);
					LogUtil.d(TAG, "Pop Back Stack");
				}
				else {
					FragmentUtil.sDisableTransitionAnimation = true;
					mainActivity.finish();
					FragmentUtil.sDisableTransitionAnimation = false;
					LogUtil.d(TAG, "Exit");
				}
			}
		});
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Build Top Navigation Basic
	 * @param mainActivity
	 * @param rootLayout
	 */
	public static void buildTopNavigationBasic(final MainActivity mainActivity, final View rootLayout, TopNavigationInfo info) {
		if(mainActivity == null || rootLayout == null || info == null) {
			return;
		}
		//Back Button
//		View backButton = rootLayout.findViewById(R.id.flTopNavigationBack);
//		TaskUtil.buildBackButton(mainActivity, backButton);

		//Title
//		TextView tvTitle = (TextView) rootLayout.findViewById(R.id.tvTopNavigationTitle);
//		tvTitle.setText(info.title);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Build UI From Network Issue
	 * @param viewSwitcher
	 * @param message
	 */
	public static void buildUiFromNetworkIssue(final ViewSwitcher viewSwitcher, String message, int displayChildIndex) {
//		if(viewSwitcher == null) {
//			return;
//		}
//		TextView tvMessage = (TextView) viewSwitcher.findViewById(R.id.tvUiFromNetworkIssueMessage);
//		ProgressBar pbProgress = (ProgressBar) viewSwitcher.findViewById(R.id.pbUiFromNetworkIssueProgress);
//		if(pbProgress != null) {
//			pbProgress.setVisibility(View.GONE);
//		}
//		if(tvMessage != null) {
//			tvMessage.setText(message);
//			tvMessage.setVisibility(View.VISIBLE);
//		}
//		viewSwitcher.setDisplayedChild(displayChildIndex);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Build UI From Network Issue for PreExecute.
	 * @param viewSwitcher
	 * @param displayChildIndex
	 */
	public static void buildUiFromNetworkIssueForPreExecute(final ViewSwitcher viewSwitcher, int displayChildIndex) {
		if(viewSwitcher == null) {
			return;
		}
//		TextView tvMessage = (TextView) viewSwitcher.findViewById(R.id.tvUiFromNetworkIssueMessage);
//		ProgressBar pbProgress = (ProgressBar) viewSwitcher.findViewById(R.id.pbUiFromNetworkIssueProgress);
//		if(pbProgress != null) {
//			pbProgress.setVisibility(View.VISIBLE);
//		}
//		if(tvMessage != null) {
//			tvMessage.setVisibility(View.GONE);
//		}
		viewSwitcher.setDisplayedChild(displayChildIndex);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Create Bitmap Downloader
	 * @param context
	 * @return
	 */
//	public static BitmapDownloader createBitmapDownloader(Context context) {
//		return new BitmapDownloader(context, 5, AppInfo.CACHE_IMAGE_DIR_NAME, 30, null, 100).useDiskCache(true).useMemoryCache(true);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Close Right Menu
	 * @param mainActivity
	 */
	public static void closeRightMenu(final MainActivity mainActivity) {
		mainActivity.getDrawerLayout().closeDrawer(mainActivity.getRightMenuLayout());
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Prepare category keyword to register guest or edit categories.
	 * @param selectedCategories
	 * @return
	 */
//	public static String prepareCategoryKeyword(SortedArrayList<Category> selectedCategories) {
//		String keyword = "";
//		int selectedCount = selectedCategories.size();
//		for(int i = 0; i < selectedCount; i++) {
//			Category category = selectedCategories.get(i);
//			keyword += category.getId();	//Expand keyword by Category Id.
//			if(i < selectedCount - 1) {
//				keyword += ServerApi.SEPARATOR_URL_KEYWORD;	//
//			}
//		}
//		return keyword;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Prepare Setting Category Parameters.
	 * @return
	 */
//	public static ArrayList<NameValuePair> prepareSettingCategoryParameters(SortedArrayList<Category> categoryList) {
//		int selectedCount = categoryList.size();
//		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
//		for(int i = 0; i < selectedCount; ++i) {
//			parameters.add(new BasicNameValuePair("category[" + categoryList.get(i).getId() + "]", categoryList.get(i).getStatus()));
//		}
//		return parameters;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Bad Request Parameter Message from Response JSON data.
	 * @param responseJsonObj Response JSON data object.
	 * @param badParameter bad parameter key name of JSON data object
	 * @param messageKey message key name of bad parameter JSON object.
	 * @return
	 */
	public static String getBadRequestParamMessage(JSONObject responseJsonObj, String badParameter, String messageKey) {
		String message = "";
		try {
			if(responseJsonObj != null) {
				JSONArray badParamObjList = responseJsonObj.getJSONArray(badParameter);
				if(badParamObjList != null) {
					int badParamMsgCount = badParamObjList.length();
					for(int i = 0; i < badParamMsgCount; i++) {
						JSONObject badParamMsgObj = badParamObjList.getJSONObject(i);
						if(badParamMsgObj != null) {
							message += badParamMsgObj.optString(messageKey);
							if(i < badParamMsgCount - 1) {
								message = message + "\n";
							}
						}
					}//For
				}//
			}//Null Response JSON Object
		}
		catch(JSONException e) {
			message = "";
			LogUtil.d(TAG, e.getMessage(), e);
		}
		return message;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Bad Request Parameter and Header Messages.
	 * @param responseJsonObj
	 * @param headers
	 * @param parameters
	 * @param messageKey
	 * @return
	 */
//	public static String getBadRequestParamMessages(JSONObject responseJsonObj, ArrayList<NameValuePair> headers, ArrayList<NameValuePair> parameters, String messageKey) {
//		String message = "";
//		if(responseJsonObj != null) {
//			String badHeaderMessage = "";
//			for(NameValuePair pair : headers) {
//				String m = TaskUtil.getBadRequestParamMessage(responseJsonObj, pair.getName(), messageKey);
//				if(!StringUtil.isEmpty(m)) {
//					badHeaderMessage += m + "\n";
//				}
//			}
//			String badParameterMessage = "";
//			for(NameValuePair pair : parameters) {
//				String m = TaskUtil.getBadRequestParamMessage(responseJsonObj, pair.getName(), messageKey);
//				if(!StringUtil.isEmpty(m)) {
//					badParameterMessage += m + "\n";
//				}
//			}
//			if(!StringUtil.isEmpty(badHeaderMessage)) {
//				message += badHeaderMessage + "\n";
//			}
//			if(!StringUtil.isEmpty(badParameterMessage)) {
//				message += badParameterMessage + "\n";
//			}
//			int len = message.length();
//			if(len > 0) {
//				char endChar = message.charAt(len-1);
//				if(endChar == '\n') {
//					message = message.substring(0, len-2);
//				}
//			}
//		}
//		return message;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Register Guest User.
	 * @param callback
	 * @param settingCategoryParameters <code>category[i]</code> parameters.
	 */
//	public static void registerGuest(RestAsyncTaskCallback callback, ArrayList<NameValuePair> settingCategoryParameters) {
//		//Validate parameter and header
//		if(settingCategoryParameters == null) {
//			settingCategoryParameters = new SortedArrayList<NameValuePair>();
//		}
//		//Process
//		MainActivity mainActivity = AppState.getInstance().getMainActivity();
//		Context context = mainActivity.getApplicationContext();
//		if(NetworkUtil.isNetworkOnline(context)) {
//			LogUtil.d(TAG, "Registering Guest User..");
//			RegisterGuestRestClient client = new RegisterGuestRestClient(context, callback);
//			client.categoryParameters(settingCategoryParameters).execute();
//			AppState.getInstance().addRunningAsyncRestClient(client);
//		}
//		else {
//			TaskUtil.showInternetErrorPopUp(mainActivity);
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Register Member User.
	 * @param callback
	 * @param userId <code>user_id</code> parameter.
	 * @param loginHash <code>LOGIN-HASH</code> header.
	 * @param email <code>email</code> parameter.
	 * @param name <code>name</code> parameter.
	 * @param password <code>password</code> parameter.
	 * @param confirmation <code>confirmation</code> parameter.
	 */
//	public static void registerMember(RestAsyncTaskCallback callback, String userId, String loginHash, String email, String name, String password, String confirmation) {
//		//Validate parameter and header
//		userId = StringUtil.validateRequestParameter(userId);
//		loginHash = StringUtil.validateRequestParameter(loginHash);
//		email = StringUtil.validateRequestParameter(email);
//		name = StringUtil.validateRequestParameter(name);
//		password = StringUtil.validateRequestParameter(password);
//		confirmation = StringUtil.validateRequestParameter(confirmation);
//		//Process
//		MainActivity mainActivity = AppState.getInstance().getMainActivity();
//		Context context = mainActivity.getApplicationContext();
//		if(NetworkUtil.isNetworkOnline(context)) {
//			LogUtil.d(TAG, "Registering Member User..");
//			RegisterUserRestClient client = new RegisterUserRestClient(context, callback);
//			client.userId(userId).transferId(userId).loginHash(loginHash).email(email).name(name).password(password).confirmation(confirmation).execute();
//			AppState.getInstance().addRunningAsyncRestClient(client);
//		}
//		else {
//			TaskUtil.showInternetErrorPopUp(mainActivity);
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Edit categories.
	 * @param callback
	 * @param userId <code>user_id</code> parameter.
	 * @param loginHash <code>LOGIN-HASH</code> header.
	 * @param settingCategoryParameters <code>category[i]</code> parameters.
	 */
//	public static void editCategories(RestAsyncTaskCallback callback, String userId, String loginHash, ArrayList<NameValuePair> settingCategoryParameters) {
//		//Validate parameter and header
//		userId = StringUtil.validateRequestParameter(userId);
//		loginHash = StringUtil.validateRequestParameter(loginHash);
//		if(settingCategoryParameters == null) {
//			settingCategoryParameters = new SortedArrayList<NameValuePair>();
//		}
//		//Process
//		MainActivity mainActivity = AppState.getInstance().getMainActivity();
//		Context context = mainActivity.getApplicationContext();
//		if(NetworkUtil.isNetworkOnline(context)) {
//			LogUtil.d(TAG, "Editting Categories..");
//			EditCategoryRestClient client = new EditCategoryRestClient(context, callback);
//			client.userId(userId).loginHash(loginHash).categoryParameters(settingCategoryParameters).execute();
//			AppState.getInstance().addRunningAsyncRestClient(client);
//		}
//		else {
//			TaskUtil.showInternetErrorPopUp(mainActivity);
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Login Member User.
	 * @param callback
	 * @param loginHash <code>LOGIN-HASH</code> header.
	 * @param email <code>email</code> parameter.
	 * @param password <code>password</code> parameter.
	 */
//	public static void loginMember(RestAsyncTaskCallback callback, String loginHash, String email, String password) {
//		//Validate parameter and header
//		loginHash = StringUtil.validateRequestParameter(loginHash);
//		email = StringUtil.validateRequestParameter(email);
//		password = StringUtil.validateRequestParameter(password);
//		//Process
//		MainActivity mainActivity = AppState.getInstance().getMainActivity();
//		Context context = mainActivity.getApplicationContext();
//		if(NetworkUtil.isNetworkOnline(context)) {
//			LogUtil.d(TAG, "Logging In Member User..");
//			LoginUserRestClient client = new LoginUserRestClient(context, callback);
//			client.loginHash(loginHash).email(email).password(password).execute();
//			AppState.getInstance().addRunningAsyncRestClient(client);
//		}
//		else {
//			TaskUtil.showInternetErrorPopUp(mainActivity);
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Request Category List.
	 * @param callback
	 * @param settingCategoryParameters <code>category[i]</code> parameters.
	 */
//	public static void requestCategoryList(final MainActivity activity, RestAsyncTaskCallback callback) {
//		//Process
//		MainActivity mainActivity = AppState.getInstance().getMainActivity();
//		Context context = mainActivity.getApplicationContext();
//		if(NetworkUtil.isNetworkOnline(context)) {
//			LogUtil.d(TAG, "Requesting Category List..");
//			CategoryRestClient client = new CategoryRestClient(context, callback);
//			client.execute();
//			AppState.getInstance().addRunningAsyncRestClient(client);
//		}
//		else {
//			TaskUtil.showInternetErrorPopUp(mainActivity);
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Show Internet Error Pop Up.
	 * @param activity he Context the Dialog is to run it. In particular, it uses the window manager and theme in this 
	 * context to present its UI.
	 */
//	public static void showInternetErrorPopUp(final Activity activity) {
//		AppState.getInstance().showInternetErrorPopUp(activity);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Open Handled Internal URL in ViewPager.
	 * @param activity
	 * @param articleId
	 */
//	public static void openHandledInternalUrlInViewPager(final MainActivity activity, int displayType, String title, String url) {
//		String tag = WebPagerFragment.TAG;
//		FragmentTransitionInfo transitionInfo = TaskUtil.getRightToLeftTransition();
//		WebPagerFragment fragment = (WebPagerFragment) Fragment.instantiate(activity.getApplicationContext(), tag);
//		fragment.setStartDisplayType(displayType).setStartTitle(title).setStartUrl(url);
//		FragmentUtil.replaceFragment(activity, activity.getFragmentContainerId(), fragment, tag, transitionInfo);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Open Handled Article Detail URL In ViewPager
	 * @param activity
	 * @param articleId
	 */
//	public static void openHandledArticleDetailInViewPager(final MainActivity activity, String articleId) {
//		String tag = WebPagerFragment.TAG;
//		FragmentTransitionInfo transitionInfo = TaskUtil.getRightToLeftTransition();
//		WebPagerFragment fragment = (WebPagerFragment) Fragment.instantiate(activity.getApplicationContext(), tag);
//		fragment.setStartDisplayType(HandledWebModel.DISPLAY_TYPE_ARTICLE).setStartArticleId(articleId);
//		FragmentUtil.replaceFragment(activity, activity.getFragmentContainerId(), fragment, tag, transitionInfo);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Create Handled Web Model.
	 * @param displayType
	 * @param title
	 * @param url
	 * @return
	 */
//	public static HandledWebModel createHandledWebModel(final WebPagerFragment hostFragment, int displayType, String title, String url) {
//		return new HandledWebModel(hostFragment).setDisplayType(displayType).setTitle(title).setUrl(url);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Check and Download image using Picasso SSL.
	 * @param context Context for Picasso SSL
	 * @param url URL of Image that need to be downloaded.
	 * @param imageView ImageView to display the downloaded image
	 * @param width target image width
	 * @param height target image height 
	 * @param noImage No Image Pattern String. May be come from server response. 
	 * @param defaultHolderResource Place holder when no there no image to display on ImageView.
	 */
//	public static void checkAndDownloadImage(Context context, String url, final ImageView imageView, int width, int height, String noImage, int defaultHolderResource) {
//		if(StringUtil.isEmpty(url) || url.trim().equals(noImage)) {
//			url = "default";
//		}
//		if(width == 0 && height == 0) {
//			PicassoSsl.with(context).load(url).placeholder(defaultHolderResource).into(imageView);
//		}
//		else {
//			PicassoSsl.with(context).load(url).resize(width, height).placeholder(defaultHolderResource).into(imageView);
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Check and Download image using Picasso SSL.
	 * @param context Context for Picasso SSL
	 * @param url URL of Image that need to be downloaded.
	 * @param imageView ImageView to display the downloaded image
	 * @param width target image width
	 * @param height target image height 
	 * @param noImage No Image Pattern String. May be come from server response. 
	 * @param defaultHolderResource Place holder when no there no image to display on ImageView.
	 */
//	public static void checkAndDownloadArticleImage(Context context, String url, final ImageView imageView, int width, int height, String noImage, int defaultHolderResource) {
//		LogUtil.d(TAG, "Downloading Article Image..");
//		if(StringUtil.isEmpty(url) || url.trim().equals(noImage)) {
//			url = "default";
//		}
//		if(width == 0 && height == 0) {
//			PicassoSsl.with(context).load(url).placeholder(defaultHolderResource).into(imageView);
//		}
//		else {
//			LogUtil.d(TAG, "Resize: (" + width + "," + height + ")");
//			PicassoSsl.with(context).load(url).resize(width, height).centerCrop().placeholder(defaultHolderResource).into(imageView);
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Register Server Push Id.
	 * @param activity
	 */
//	public static void registerServerPushId(final MainActivity activity) {
//		final User user = AppState.getInstance().getUser();
//		if(user.isUnknownUser()) {
//			RestAsyncTaskCallback callback = new RestAsyncTaskCallback() {
//				@Override
//				public void doPostExecute(HashMap<String, Object> result) {
//					String error = ResultUtil.getError(result);
//					if(StringUtil.isEmpty(error)) {	//No errors
//						User user = (User) result.get("user");
//						LogUtil.d(TAG, user.toJsonObject().toString());
//						AppState.getInstance().putUser(user);
//						UserPreference.put(activity.getApplicationContext(), user);
//						GcmUtil.registerServerPushNotification(activity, user);
//					}
//				}
//			};
//			TaskUtil.registerGuest(callback, null);
//		}
//		else {
//			GcmUtil.registerServerPushNotification(activity, user);
//		}
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get URL domain.
	 * @param url
	 * @return
	 */
	public static String getUrlDomain(String url) {
		try {
			URL aURL = new URL(url);
			return aURL.getProtocol() + "://" + aURL.getHost();
		}
		catch(MalformedURLException e) {
			LogUtil.e(TAG, e.getMessage(), e);
			return "";
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Right to Left Fragment transition information.
	 * @return
	 */
	public static FragmentTransitionInfo getRightToLeftTransition() {
		FragmentTransitionInfo transitionInfo = new FragmentTransitionInfo();
		transitionInfo.enterAnimationId = R.anim.fragment_enter_right_to_left;
		transitionInfo.exitAnimationId = R.anim.fragment_exit_right_to_left;
		transitionInfo.popEnterAnimationId = R.anim.fragment_enter_left_to_right;
		transitionInfo.popExitAnimationId = R.anim.fragment_exit_left_to_right;
		return transitionInfo;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Left to Right Fragment transition information.
	 * @return
	 */
	public static FragmentTransitionInfo getLeftToRightTransition() {
		FragmentTransitionInfo transitionInfo = new FragmentTransitionInfo();
		transitionInfo.enterAnimationId = R.anim.fragment_enter_left_to_right;
		transitionInfo.exitAnimationId = R.anim.fragment_exit_left_to_right;
		transitionInfo.popEnterAnimationId = R.anim.fragment_enter_right_to_left;
		transitionInfo.popExitAnimationId = R.anim.fragment_exit_right_to_left;
		return transitionInfo;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get bottom to top fragment transition information without enter enimation.
	 * @return
	 */
	public static FragmentTransitionInfo getBottomToTopTransition() {
		FragmentTransitionInfo transitionInfo = new FragmentTransitionInfo();
		transitionInfo.enterAnimationId = R.anim.fragment_enter_bottom_up;
		transitionInfo.exitAnimationId = R.anim.fragment_standing;
		transitionInfo.popEnterAnimationId = R.anim.fragment_standing;
		transitionInfo.popExitAnimationId = R.anim.fragment_exit_top_down;
		return transitionInfo;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Show Loading Pop Up. Please reassign this pop up to passed Loading Pop for future process such as dismiss. etc..
	 * @param loadinPopUp
	 * @return
	 */
//	public static SimpleLoadingPopUp showLoadingPopUp(SimpleLoadingPopUp loadinPopUp) {
//		if(loadinPopUp == null) {
//			loadinPopUp = new SimpleLoadingPopUp(AppState.getInstance().getMainActivity(), null); 
//		}
//		loadinPopUp.show();
//		return loadinPopUp;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Hide Loading Pop Up
	 * @param loadingPopUp
	 * @return
	 */
//	public static boolean hideLoadingPopUp(SimpleLoadingPopUp loadingPopUp) {
//		if(loadingPopUp == null) {
//			return false;
//		}
//		loadingPopUp.dismiss();
//		return true;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Process Log Out (clear cache and information related to user).
	 * @param context
	 */
//	public static void processLogOut(Context context) {
//		User user = AppState.getInstance().getUser().reset();
//		UserPreference.put(context, user);
//		//SearchKeywordPreference.reset(context);
//		CachePreference.putIsEditedCategory(context, false);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Achieve the URL headers with name [X-HLC-LHASH]
	 * @param user
	 * @return
	 */
//	public static HashMap<String, String> achieveUrlHeaders(User user) {
//		HashMap<String, String> headers = new HashMap<String, String>();
//		String loginHash = user.getLoginHash();
//		if(StringUtil.isEmpty(loginHash)) {
//			loginHash = AppUrl.DEFAULT_LOGIN_HASH;
//		}
//		headers.put("X-HLC-LHASH", loginHash);
//		return headers;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Achieve the Facebook OAuth URL headers with name [LOGIN-HASH]
	 * @param user
	 * @return
	 */
//	public static HashMap<String, String> achieveFacebookUrlHeaders(User user) {
//		HashMap<String, String> headers = new HashMap<String, String>();
//		String loginHash = user.getLoginHash();
//		if(StringUtil.isEmpty(loginHash)) {
//			loginHash = "";
//		}
//		headers.put("LOGIN-HASH", loginHash);
//		return headers;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Facebook OAuth URL
	 * @param user
	 * @return
	 */
//	public static String getFacebookOAuthUrl(final User user) {
//		String url = AppUrl.OAUTH_FACEBOOK.replace("/{user_id}", "");
//		if(user.isUnknownUser()) {
//			return url;
//		}
//		if(user.getType() == User.GUEST) {
//			url = url + "?transfer_id=" + user.getId();
//		}
//		else {
//			url = url + "?associate_id=" + user.getId();
//		}
//		return url;
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Facebook OAuth URL to post Article Comment
	 * @param user
	 * @return
	 */
//	public static String getFacebookOAuthPostCommentUrl(final User user, String comment, String articleId) {
//		return AppUrl.OAUTH_FACEBOOK_POST_COMMENT.replace("{comment}", comment).replace("{article_id}", articleId);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Open User Detail By WebView.
	 * @param user
	 */
//	public static void openUserDetailByWebView(final MainActivity activity, final User user) {
//		TaskUtil.openUserDetailByWebView(activity, user.getId(), user.getGroup());
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Open User Detail By WebView.
	 * @param activity
	 * @param userId
	 */
//	public static void openUserDetailByWebView(final MainActivity activity, String userId, String group) {
//		String url = AppUrl.USER_DETAIL.replace("{user_id}", userId);
//		String title = activity.getResources().getString(R.string.user_detail_screen_title);
//		if(group.trim().equalsIgnoreCase(User.EXPERT + "")) {
//			title = activity.getResources().getString(R.string.user_expert_detail_screen_title);
//		}
//		TaskUtil.openHandledInternalUrlInViewPager(activity, HandledWebModel.DISPLAY_TYPE_NORMAL, title, url);
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Open OAuth Facebook Screen by WebView and process Redirect URL to OAuth and Post Article Comment onto Facebook.
	 * @param user
	 */
//	public static void openWebViewOAuthFacebookScreenToPostComment(final Fragment hostFragment, User user, String comment, String articleId) {
//		int animation = R.style.DialogBottomUpAnimation;
//		WebViewPopUp webViewPopUp = new WebViewPopUp(hostFragment);
//		FacebookWebClient webClient = new FacebookWebClient(webViewPopUp);
//		String url = TaskUtil.getFacebookOAuthPostCommentUrl(user, comment, articleId);
//		webViewPopUp.setURL(url).setHeaders(TaskUtil.achieveFacebookUrlHeaders(user)).setClearCache(true).setClearCookies(true).
//		setNavigationType(WebNavigationType.WEB_VIEW_SIMPLE).setWebViewClient(webClient).setAnimationStyle(animation).show();
//	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @return
	 */
//	public static String getAuthorizationValue() {
//		String authorization = ServerApi.AUTHORIZE_ID + ":" + ServerApi.AUTHORIZE_PASSWORD;
//		return "Basic " + new String(Base64.encode(authorization.getBytes(), 0));
//	}
	//--------------------------------------------------------------------------------------------------------------------
}
