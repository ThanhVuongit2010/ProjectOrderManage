package vn.app.android.ordermanager.util;

import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.state.AppState;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment Utility
 * @author VuongNT
 */
public class FragmentUtil {
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Logcat Tag
	 */
	public static final String TAG = FragmentUtil.class.getName();
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Disable Fragment transition animation.
	 */
	public static boolean sDisableTransitionAnimation = false;
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add fragment
	 * @param fragmentActivity
	 * @param viewGroupId
	 * @param fragment
	 * @param tag Null or empty string means that be not pushed into back stack, otherwise is pushed with specific tag name
	 * @return
	 */
	public static boolean addFragment(final FragmentActivity fragmentActivity, int viewGroupId, final Fragment fragment, final String tag, FragmentTransitionInfo transitionInfo) {
		try {
			FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
			//Animation
			if(transitionInfo != null) {
				if(transitionInfo.enterAnimationId != -1 && transitionInfo.exitAnimationId != -1) {
					if(transitionInfo.popEnterAnimationId != -1 && transitionInfo.popExitAnimationId != -1) {	//Apply to Pop BackStack
						transaction.setCustomAnimations(transitionInfo.enterAnimationId, transitionInfo.exitAnimationId, transitionInfo.popEnterAnimationId, transitionInfo.popExitAnimationId);
					}
					else {	//Not Apply to Pop BackStack
						transaction.setCustomAnimations(transitionInfo.enterAnimationId, transitionInfo.exitAnimationId);	
					}
				}
			}
			else {
				transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
			}
			//Add
			transaction.add(viewGroupId, fragment, tag);
			if(StringUtil.isEmpty(tag)) {
				AppState.sCanGoBack = false;
			}
			else {
				transaction.addToBackStack(tag);
				AppState.sCanGoBack = true;
			}
			transaction.commit();
			return true;
		}
		catch(Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
			e.printStackTrace();
		}
		return false;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add Fragment Safely. If the fragment existed, it will add a duplicated itself.
	 * @param fragmentActivity
	 * @param viewGroupId
	 * @param fragmentClassNameTag
	 * @param addToBackStack
	 * @param transitionInfo
	 * @return
	 */
	public static boolean addFragmentSafely(final FragmentActivity fragmentActivity, int viewGroupId, final String fragmentClassNameTag, boolean addToBackStack, FragmentTransitionInfo transitionInfo) {
		Fragment fragment = FragmentUtil.findFragmentByTag(fragmentActivity, fragmentClassNameTag);
		if(fragment == null) {
			fragment = Fragment.instantiate(fragmentActivity.getApplicationContext(), fragmentClassNameTag);
		}
		return FragmentUtil.addFragment(fragmentActivity, viewGroupId, fragment, addToBackStack ? fragmentClassNameTag : null, transitionInfo);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Replace Fragment
	 * @param fragmentActivity
	 * @param viewGroupId
	 * @param fragment
	 * @param tag Null or empty string means that be not pushed into back stack, otherwise is pushed with specific tag name
	 * @return
	 */
	public static boolean replaceFragment(final FragmentActivity fragmentActivity, int viewGroupId, final Fragment fragment, final String tag, FragmentTransitionInfo transitionInfo) {
		try {
			FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
			//Animation
			if(transitionInfo != null) {
				if(transitionInfo.enterAnimationId != -1 && transitionInfo.exitAnimationId != -1) {
					if(transitionInfo.popEnterAnimationId != -1 && transitionInfo.popExitAnimationId != -1) {	//Apply to Pop BackStack
						transaction.setCustomAnimations(transitionInfo.enterAnimationId, transitionInfo.exitAnimationId, transitionInfo.popEnterAnimationId, transitionInfo.popExitAnimationId);
					}
					else {	//Not Apply to Pop BackStack
						transaction.setCustomAnimations(transitionInfo.enterAnimationId, transitionInfo.exitAnimationId);	
					}
				}
			}
			else {
				transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
			}
			//Replace
			transaction.replace(viewGroupId, fragment, tag);
			if(StringUtil.isEmpty(tag)) {
				AppState.sCanGoBack = false;
			}
			else {
				transaction.addToBackStack(tag);
				AppState.sCanGoBack = true;
			}
			transaction.commit();
			return true;
		}
		catch(Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return false;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Replace Fragment Safely. If the fragment existed, it will replace a duplicated itself.
	 * @param fragmentActivity
	 * @param viewGroupdId
	 * @param fragmentClassNameTag
	 * @param addToBackStack true add to back stack, otherwise is false
	 * @return
	 */
	public static boolean replaceFragmentSafely(final FragmentActivity fragmentActivity, int viewGroupdId, final String fragmentClassNameTag, boolean addToBackStack, FragmentTransitionInfo transitionInfo) {
//		Fragment fragment = FragmentUtil.findFragmentByTag(fragmentActivity, fragmentClassNameTag);
//		if(fragment == null) {
//			fragment = Fragment.instantiate(fragmentActivity.getApplicationContext(), fragmentClassNameTag);
//		}
//		return FragmentUtil.replaceFragment(fragmentActivity, viewGroupdId, fragment, addToBackStack ? fragmentClassNameTag : null, transitionInfo);
		Fragment fragment = Fragment.instantiate(fragmentActivity.getApplicationContext(), fragmentClassNameTag);
		return FragmentUtil.replaceFragment(fragmentActivity, viewGroupdId, fragment, addToBackStack ? fragmentClassNameTag : null, transitionInfo);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Find fragment by tab name.
	 * @param fragmentActivity
	 * @param tag
	 * @return
	 */
	public static Fragment findFragmentByTag(final FragmentActivity fragmentActivity, final String tag) {
		try {
			return fragmentActivity.getSupportFragmentManager().findFragmentByTag(tag);
		}
		catch(Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
			return null;
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear back stack
	 * @param fragmentActivity
	 */
	public static void clearBackStack(final FragmentActivity fragmentActivity) {
		FragmentUtil.sDisableTransitionAnimation = true;
		FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager(); 
		fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		FragmentUtil.sDisableTransitionAnimation = false;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear back stack without me.
	 * @param fragmentActivity
	 * @param myFragmentId The Fragment ID that no want to be removed.
	 */
	public static void clearBackStackWithoutMe(final FragmentActivity fragmentActivity, int myFragmentId) {
		FragmentUtil.sDisableTransitionAnimation = true;
		FragmentUtil.sDisableTransitionAnimation = false;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Pop back stack fragment
	 * @param fragmentActivity
	 */
	public static void popBackStack(final FragmentActivity fragmentActivity) {
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		fm.popBackStack();
	}
	//-------------------------------------------------------------------------------------------------------------------
}