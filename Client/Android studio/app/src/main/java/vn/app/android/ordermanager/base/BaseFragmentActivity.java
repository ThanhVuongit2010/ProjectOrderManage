package vn.app.android.ordermanager.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import vn.app.android.ordermanager.extra.FragmentTransitionInfo;

/**
 * Base Fragment Activity for the application
 * @author thaonp
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
	//----------------------------------------------------------------------------------------------------
	/**
	 * Waiting pop up for waiting doing background task
	 */
//	protected SimpleWaitingPopUp mWaitingPopUp;
	//----------------------------------------------------------------------------------------------------
	/**
	 * Get class tag
	 * @return Full quality class name
	 */
	public static String classTag() {
		return BaseFragmentActivity.class.getName();
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Get Activity Layout resource ID
	 * @return
	 */
	protected abstract int getActivityLayoutId();
	//----------------------------------------------------------------------------------------------------
	/**
	 * Get mandatory Views for later using of this activity
	 * @param savedInstanceState
	 */
	protected abstract void getMandatoryViews(Bundle savedInstanceState);
	//----------------------------------------------------------------------------------------------------
	/**
	 * Inflate layout and get mandatory Views
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getActivityLayoutId());
		getMandatoryViews(savedInstanceState);
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Should not use this method directly and should implement <code>getActivityLayoutId()</code> to 
	 * return the View layout to <code>setContentView()</code> <br/>
	 * Set the activity content from a layout resource. The resource will be inflated, adding all top-level views to the activity.
	 * @param layoutResID Resource ID to be inflated.
	 */
	@Override
	@Deprecated
	final public void setContentView(int layoutResID) {
		super.setContentView(getActivityLayoutId());
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Clear activity transition
	 */
	public void clearTransitionAnimation() {
		getWindow().setWindowAnimations(0);
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Set activity transition
	 * @param transitionAnimationResourceId animation resource id
	 */
	public void setTransitionAnimation(int transitionAnimationResourceId) {
		getWindow().setWindowAnimations(transitionAnimationResourceId);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add fragment to Fragment Activity
	 * @param fragment Fragment will be added
	 * @param viewGroupId Fragment container ID
	 * @param tag Null or empty string means that be not pushed into back stack, otherwise is pushed with specific tag name
	 * @param transition Transition animations
	 * @return <code>true</code> if added successfully, otherwise is <code>false</code>
	 */
	public boolean addFragment(final Fragment fragment, int viewGroupId, String tag, FragmentTransitionInfo transition) {
		return FragmentHelper.addFragment(this, fragment, viewGroupId, tag, transition);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add fragment to Fragment Activity by fragment class tag name.
	 * @param viewGroupId Fragment container ID
	 * @param fragmentClassNameTag Full quality fragment class name
	 * @param addToBackStack <code>true</code> is not pushed into back stack, otherwise is pushed with specific <code>fragmentClassNameTag</code> tag name
	 * @param data Passed data 
	 * @param transition Transition animations
	 * @return <code>true</code> if added successfully, otherwise is <code>false</code>
	 */
	public boolean addFragment(int viewGroupId, String fragmentClassNameTag, boolean addToBackStack, Bundle data, FragmentTransitionInfo transition) {
		return FragmentHelper.addFragment(this, viewGroupId, fragmentClassNameTag, addToBackStack, data, transition);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Replace fragment from Fragment Activity of specific ViewGroup ID
	 * @param fragment Fragment will be replaced
	 * @param viewGroupId Fragment container ID
	 * @param tag Null or empty string means that be not pushed into back stack, otherwise is pushed with specific tag name
	 * @param transition Transition animations
	 * @return <code>true</code> if replaced successfully, otherwise is <code>false</code>
	 */
	public boolean replaceFragment(final Fragment fragment, int viewGroupId, final String tag, FragmentTransitionInfo transition) {
		return FragmentHelper.replaceFragment(this, fragment, viewGroupId, tag, transition);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Replace fragment from Fragment Activity of specific ViewGroup ID by fragment class tag name.
	 * @param viewGroupId Fragment container ID
	 * @param fragmentClassNameTag Full quality fragment class name
	 * @param addToBackStack <code>true</code> is not pushed into back stack, otherwise is pushed with specific <code>fragmentClassNameTag</code> tag name
	 * @param data Passed data 
	 * @param transition Transition animations
	 * @return <code>true</code> if replaced successfully, otherwise is <code>false</code>
	 */
	public boolean replaceFragment(int viewGroupId, String fragmentClassNameTag, boolean addToBackStack, Bundle data, FragmentTransitionInfo transition) {
		return FragmentHelper.replaceFragment(this, viewGroupId, fragmentClassNameTag, addToBackStack, data, transition);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Find fragment from Fragment Activity by tab name.
	 * @param tag Tag of fragment when add or replace by transaction
	 * @return Found <code>Fragment</code> or <code>null</code> if not found
	 */
	public Fragment findFragmentByTag(final String tag) {
		return FragmentHelper.findFragmentByTag(this, tag);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear fragment back stack
	 */
	public void clearBackStack() {
		FragmentHelper.clearBackStack(this);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear fragment back stack by Iterator
	 */
	public void clearBackStackByIterator() {
		FragmentHelper.clearBackStackByIterator(this);
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Clear back stack without any transition animation
	 */
	public void clearBackStackWithoutTransitionAnimation() {
//		AppState.getInstance().disableFragmentTranstitionAnimation();
		FragmentHelper.clearBackStack(this);
//		AppState.getInstance().enableFragmentTranstitionAnimation();
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Pop Fragment Back Stack
	 */
	public void popFragmentBackStack() {
		getSupportFragmentManager().popBackStack();
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Open URL by Default Browser from Activity
	 */
	public void openUrlByDefaultBrowser(String url) {
		if (url == null || url.trim().isEmpty()) {
			Toast.makeText(getApplicationContext(), "Can not open null or empty URL", Toast.LENGTH_SHORT).show();
			return;
		}
		Uri uri = Uri.parse(url);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Show waiting pop up
	 */
	public void showWaitingPopUp() {
//		mWaitingPopUp = new SimpleWaitingPopUp(this, null);
//		mWaitingPopUp.show();
	}
	//----------------------------------------------------------------------------------------------------
	/**
	 * Hide waiting pop up
	 */
	public void hideWaitingPopUp() {
//		if(mWaitingPopUp != null) {
//			mWaitingPopUp.dismiss();
//		}
	}
	//----------------------------------------------------------------------------------------------------
}
