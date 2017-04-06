package vn.app.android.ordermanager;

import vn.app.android.ordermanager.event.DrawerLayoutCallback;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.order.v101.fragment.V101MainFragment;
import vn.app.android.ordermanager.state.AppState;
import vn.app.android.ordermanager.util.FragmentUtil;
import vn.app.android.ordermanager.util.KeyboardUtil;
import vn.app.android.ordermanager.util.LogUtil;
import vn.app.android.ordermanager.util.TaskUtil;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.order.ordermanager.R;

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
			LogUtil.d(TAG, "Start App by User");
			addSlashScreenFragment(savedInstanceState);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public void onBackPressed() {
		LogUtil.d(TAG, "CanGoBack: " + AppState.sCanGoBack);
		LogUtil.d(TAG, "StartByUser: " + AppState.sStartAppByUser);
		LogUtil.d(TAG, "PushScreen: " + AppState.sScreenOpenByPush);
		if(!AppState.sStartAppByUser) {
			LogUtil.d(TAG, "StartBy: Push Notification");
			FragmentManager fm = getSupportFragmentManager();
			int backStackCount = fm.getBackStackEntryCount();
			LogUtil.d(TAG, "BackStackCount: " + backStackCount);
			if(AppState.sScreenOpenByPush.equals("artcile-detail") || 
					AppState.sScreenOpenByPush.equals("push-notification") ||
					AppState.sScreenOpenByPush.equals("app-notification") && (backStackCount <= 0)) {
				LogUtil.d(TAG, "Back to Dashboard screen.");
				FragmentTransitionInfo transitionInfo = TaskUtil.getLeftToRightTransition();
//				FragmentUtil.replaceFragmentSafely(this, mFragmentContainerId, ListArticleFragment.TAG, false, transitionInfo);
				AppState.sScreenOpenByPush = "";
				AppState.sStartAppByUser = true;
				return;
			}
		}
//		if(AppState.getInstance().getCurrentMainFragmentTag().equals(WebPagerFragment.TAG)) {
//			WebPagerFragment fragment = (WebPagerFragment) FragmentUtil.findFragmentByTag(this, WebPagerFragment.TAG);
//			ViewPager viewPager = fragment.getViewPager();
//			int currentItem = viewPager.getCurrentItem();
//			WebPagerAdapter adapter = (WebPagerAdapter) viewPager.getAdapter();
//			ArrayList<HandledWebModel> dataSource = (ArrayList<HandledWebModel>) adapter.getDataSource();
//			HandledWebModel webPage = dataSource.get(currentItem);
//			webPage.back();
//			return;
//		}
		if(!AppState.sCanGoBack) {
			FragmentUtil.sDisableTransitionAnimation = true;
			finish();
			FragmentUtil.sDisableTransitionAnimation = false;
			LogUtil.d(TAG, "Exit");
			return;
		}
		super.onBackPressed();
		LogUtil.d(TAG, "Pop Back Stack");
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
		super.onPause();
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onStop() {
		resetTemporaryCache();
//		AppState.getInstance().cancelAllRunningAsyncRestClients(true);
		super.onStop();
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onDestroy() {
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
			FragmentUtil.addFragment(this, mFragmentContainerId, new V101MainFragment(), null, null);
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
