package vn.app.android.ordermanager.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;
import com.order.ordermanager.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.app.android.ordermanager.MainActivity;
import vn.app.android.ordermanager.event.DrawerLayoutCallback;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.extra.TopNavigationInfo;
import vn.app.android.ordermanager.mode.Register;
import vn.app.android.ordermanager.rest.LoginUserRestClient;
import vn.app.android.ordermanager.rest.RegisterAccountRestClient;
import vn.app.android.ordermanager.state.AppState;
import vn.app.android.ordermanager.worker.RestAsyncTaskCallback;

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
	 */
	@SuppressLint("RtlHardcoded")
	public static void buildDrawerLayout(final MainActivity mainActivity) {
		String error = TaskUtil.assertBuildDrawerLayout(mainActivity);
		if(!StringUtil.isEmpty(error)) {
			throw new NullPointerException(error);
		}

		//Set margin
		int leftMenuMargin = (int) mainActivity.getResources().getDimension(R.dimen.h140);
		int rightMenuMargin = (int) mainActivity.getResources().getDimension(R.dimen.h140);
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
	/**
	 * Show Internet Error Pop Up.
	 * @param activity he Context the Dialog is to run it. In particular, it uses the window manager and theme in this
	 * context to present its UI.
	 */
	public static void showInternetErrorPopUp(final Activity activity) {
		String title = activity.getResources().getString(R.string.pop_up_error_internet_title);
		String message = activity.getResources().getString(R.string.pop_up_error_internet_message);
		AppState.getInstance().showErrorPopUp(activity,title,message);
	}
	public static void showDialogFragmentErrorPopUp(final Activity activity,String title, String message) {
		AppState.getInstance().showErrorPopUp(activity,title,message);
	}
	/**
	 * Login Member User.
	 * @param callback
	 * @param register <code>jsonRegister</code> header.
	 */
	public static void registerAccount(RestAsyncTaskCallback callback, Register register) {
		//Process
		MainActivity mainActivity = AppState.getInstance().getMainActivity();
		Context context = mainActivity.getApplicationContext();
		if(NetworkULRConnectionUtil.isNetworkOnline(context)) {
			LogUtil.d(TAG, "Register account..");
			RegisterAccountRestClient client = new RegisterAccountRestClient(context, callback);
			client.register(register).execute();
			AppState.getInstance().addRunningAsyncRestClient(client);
		}
		else {
			TaskUtil.showInternetErrorPopUp(mainActivity);
		}
	}
	/**
	 * Login Member User.
	 * @param callback
	 * @param compcode <code>jsonRegister</code> header.
	 * @param username <code>username</code> header.
	 * @param password <code>password</code> header.
	 */
	public static void loginUser(RestAsyncTaskCallback callback, String compcode, String username, String password, String deviceToken) {
		//Validate parameter and header
		compcode = StringUtil.validateRequestParameter(compcode);
		username = StringUtil.validateRequestParameter(username);
		password = StringUtil.validateRequestParameter(password);

		//Process
		MainActivity mainActivity = AppState.getInstance().getMainActivity();
		Context context = mainActivity.getApplicationContext();
		if(NetworkUtil.isNetworkOnline(context)) {
			LogUtil.d(TAG, "Logging In Member User..");
			LoginUserRestClient client = new LoginUserRestClient(context, callback);
			client.CompanyCode(compcode).userName(username).password(password).deviceToken(deviceToken).execute();
			AppState.getInstance().addRunningAsyncRestClient(client);
		}
		else {
			TaskUtil.showInternetErrorPopUp(mainActivity);
		}
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
	public static String getBadRequestParamMessages(JSONObject responseJsonObj, ArrayList<NameValuePair> headers, ArrayList<NameValuePair> parameters, String messageKey) {
		String message = "";
		if(responseJsonObj != null) {
			String badHeaderMessage = "";
			for(NameValuePair pair : headers) {
				String m = TaskUtil.getBadRequestParamMessage(responseJsonObj, pair.getName(), messageKey);
				if(!StringUtil.isEmpty(m)) {
					badHeaderMessage += m + "\n";
				}
			}
			String badParameterMessage = "";
			for(NameValuePair pair : parameters) {
				String m = TaskUtil.getBadRequestParamMessage(responseJsonObj, pair.getName(), messageKey);
				if(!StringUtil.isEmpty(m)) {
					badParameterMessage += m + "\n";
				}
			}
			if(!StringUtil.isEmpty(badHeaderMessage)) {
				message += badHeaderMessage + "\n";
			}
			if(!StringUtil.isEmpty(badParameterMessage)) {
				message += badParameterMessage + "\n";
			}
			int len = message.length();
			if(len > 0) {
				char endChar = message.charAt(len-1);
				if(endChar == '\n') {
					message = message.substring(0, len-2);
				}
			}
		}
		return message;
	}
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
}
