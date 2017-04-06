package vn.app.android.ordermanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.order.ordermanager.R;

import java.util.List;

import vn.app.android.ordermanager.base.FragmentHelper;
import vn.app.android.ordermanager.event.DrawerLayoutCallback;
import vn.app.android.ordermanager.order.v001.fragment.V001BeginScreenFragment;
import vn.app.android.ordermanager.preference.CachePreference;
import vn.app.android.ordermanager.state.AppState;
import vn.app.android.ordermanager.util.FragmentUtil;
import vn.app.android.ordermanager.util.KeyboardUtil;
import vn.app.android.ordermanager.util.LogUtil;
import vn.app.android.ordermanager.util.TaskUtil;

/**
 * MainActivity
 * @author Mr Vuong
 */
@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Log cat tag
	 */
	public static final String TAG = MainActivity.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	private Bundle mSavedInstanceState;
	//--------------------------------------------------------------------------------------------------------------------
	private int mFragmentContainerId;
	//--------------------------------------------------------------------------------------------------------------------
	private View mMainLayout;
	//--------------------------------------------------------------------------------------------------------------------
	private DrawerLayout mDrawerLayout;
	//--------------------------------------------------------------------------------------------------------------------
	private FrameLayout mLeftMenuLayout;
	//--------------------------------------------------------------------------------------------------------------------
	private FrameLayout mRightMenuLayout;
	//--------------------------------------------------------------------------------------------------------------------
	private FrameLayout mFragmentContainerLayout;
	//--------------------------------------------------------------------------------------------------------------------
	private DrawerLayoutCallback mDrawerLayoutCallback;
	//--------------------------------------------------------------------------------------------------------------------
	private boolean mIsStartByNewIntent = false;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get main activity layout
	 * @return
	 */
	public View getMainLayout() {
		return mMainLayout;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get DrawerLayout Callback.
	 * @return
	 */
	public DrawerLayoutCallback getDrawerLayoutCallback() {
		return mDrawerLayoutCallback;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set DrawerLayout Callback.
	 * @param drawerLayoutCallback
	 */
	public void setDrawerLayoutCallback(DrawerLayoutCallback drawerLayoutCallback) {
		mDrawerLayoutCallback = drawerLayoutCallback;
		TaskUtil.setDrawerLayoutListenter(this);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get fragment container layout resource ID.
	 * @return
	 */
	public int getFragmentContainerId() {
		return mFragmentContainerId;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Main Activity Left Menu Panel Layout
	 * @return
	 */
	public FrameLayout getLeftMenuLayout() {
		return mLeftMenuLayout;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Main Activity Right Menu Panel Layout
	 * @return
	 */
	public FrameLayout getRightMenuLayout() {
		return mRightMenuLayout;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Main Activity Fragment container
	 * @return
	 */
	public FrameLayout getFragmentContainerLayout() {
		return mFragmentContainerLayout;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Main Activity Drawer Layout.
	 * @return
	 */
	public DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	@SuppressLint("InflateParams")
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		mMainLayout = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
		setContentView(mMainLayout);
		mSavedInstanceState = savedInstanceState;
		//Put main activity to application state
		AppState.getInstance().setApplicationContext(getApplicationContext()).putMainActivity(this);
		//Get views
		getMandatoryViews();
		//Build drawer layout
		TaskUtil.buildDrawerLayout(this);
		if(getIntent().getExtras() == null) {
			Log.d(TAG, "Start App by User");
			addSlashScreenFragment(savedInstanceState);
		}

		FirebaseMessaging.getInstance().subscribeToTopic("testfcm");
		String token= FirebaseInstanceId.getInstance().getToken();
		CachePreference.putDeviceToken(getBaseContext(),token);

	}

	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public void onBackPressed() {
		LogUtil.d(TAG, "CanGoBack: " + AppState.sCanGoBack);
		LogUtil.d(TAG, "StartByUser: " + AppState.sStartAppByUser);
		LogUtil.d(TAG, "PushScreen: " + AppState.sScreenOpenByPush);
//		if (returnBackStackImmediate(getSupportFragmentManager())) {
//			return;
//		}
//		if("V101MainFragment".equals(AppState.startMainFragment)){
//			List<Fragment> fragments = getSupportFragmentManager().getFragments();
//			if(fragments != null && fragments.size() > 0){
//				boolean check = getFragmentManager().popBackStackImmediate(V101ListProductFragment.TAG,0);
//				super.onBackPressed();
//				LogUtil.d(TAG, "Pop Back Stack");
//				LogUtil.d(TAG,""+check);
//			}
//
//		}
//		List<Fragment> fragments = getSupportFragmentManager().getFragments();
//		for(Fragment fragment : fragments){
//			if(fragment != null && fragment.isVisible()){
//				FragmentManager childFm = fragment.getChildFragmentManager();
//				if (childFm.getBackStackEntryCount() > 0) {
//					childFm.popBackStack();
//					return;
//				}
//				return;
//			}
//		}

		if(!AppState.sCanGoBack) {
			FragmentHelper.sDisableTransitionAnimation = true;
			finish();
			FragmentHelper.sDisableTransitionAnimation = false;
			LogUtil.d(TAG, "Exit");
			return;
		}
		super.onBackPressed();
		LogUtil.d(TAG, "Pop Back Stack");
	}
	// HACK: propagate back button press to child fragments.
	// This might not work properly when you have multiple fragments adding multiple children to the backstack.
	// (in our case, only one child fragments adds fragments to the backstack, so we're fine with this)
	private boolean returnBackStackImmediate(FragmentManager fm) {
		List<Fragment> fragments = fm.getFragments();
		if (fragments != null && fragments.size() > 0) {
			for (Fragment fragment : fragments) {
				if (fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
//					if (fragment.getChildFragmentManager().popBackStackImmediate()) {
//						fragment.getChildFragmentManager().popBackStack();
//						return true;
//					} else {
//						return returnBackStackImmediate(fragment.getChildFragmentManager());
//					}
					fragment.getChildFragmentManager().popBackStack();
					return true;
				}
			}
		}
		return false;
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onResume() {
		AppState.sFacebookFrom = "";
		AppState.sBackArticleId = "";
		AppState.getInstance().putMainActivity(this);
//		if(!mIsStartByNewIntent) {
//			handleIntent(getIntent());
//		}
		LogUtil.d(TAG, "OnResume");
		super.onResume();
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onStart() {
		LogUtil.d(TAG, "onStart");
		super.onStart();
		CookieSyncManager.createInstance(getApplicationContext());
		//Google Analytics Tracking
		try {
//			HLCApplication.trackAppView(this, "Start App");
		}
		catch(Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onPause() {
		LogUtil.d(TAG, "onPause");
		super.onPause();

	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onStop() {
//		resetTemporaryCache();
//		AppState.getInstance().cancelAllRunningAsyncRestClients(true);
		LogUtil.d(TAG, "onStop");
		super.onStop();
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onDestroy() {
		LogUtil.d(TAG, "onDestroy");
		super.onDestroy();
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onNewIntent(Intent intent) {
		LogUtil.d(TAG, "OnNewIntent");
		super.onNewIntent(intent);
//		handleIntent(intent);
		mIsStartByNewIntent = true;
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		View view = getCurrentFocus();
		boolean ret = super.dispatchTouchEvent(event);
		if (view instanceof EditText) {	//Hide soft input when touch outside of EditText
			View w = getCurrentFocus();
			int scrcoords[] = new int[2];
			w.getLocationOnScreen(scrcoords);
			float x = event.getRawX() + w.getLeft() - scrcoords[0];
			float y = event.getRawY() + w.getTop() - scrcoords[1];
			if (event.getAction() == MotionEvent.ACTION_UP  && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) { 
				KeyboardUtil.forceHideSoftInput(this);
			}
		}
		return ret;
	}
	/**
	 * Get mandatory views
	 */
	public void getMandatoryViews() {
		mFragmentContainerId = R.id.flFragmentContainer;
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dlMainLayout);
		mLeftMenuLayout = (FrameLayout) findViewById(R.id.flLeftMenu);
		mRightMenuLayout = (FrameLayout) findViewById(R.id.flRightMenu);
		mFragmentContainerLayout = (FrameLayout) findViewById(mFragmentContainerId);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Add Slash Screen.
	 * @param savedInstanceState
	 */
	private void addSlashScreenFragment(Bundle savedInstanceState) {
		if(mFragmentContainerLayout != null) {
			if(savedInstanceState != null) {
				return;
			}
			AppState.sStartAppByUser = true;
			AppState.sScreenOpenByPush = "";
			FragmentUtil.addFragment(this, mFragmentContainerId, new V001BeginScreenFragment(), null, null);
			LogUtil.d(TAG, "Added SlashScreenFragment");
		}
		else {
			LogUtil.e(TAG, "Add SlashScreenFragment: FAIL");
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Reset Temporary Caches.
	 */
	public void resetTemporaryCache() {
		LogUtil.d(TAG, "Clearing Uneccessary Disk Cache..");
//		CachePreference.putCurrentListAritcleType(getApplicationContext(), ListArticleFragment.TYPE_FEED);
	}
	//--------------------------------------------------------------------------------------------------------------------
}
