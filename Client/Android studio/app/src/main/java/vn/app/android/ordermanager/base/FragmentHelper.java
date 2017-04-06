package vn.app.android.ordermanager.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.state.AppState;

/**
 * Fragment Utility
 * @author thaonp
 */
public class FragmentHelper {
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get class tag
	 * @return Full quality class name
	 */
	public static String classTag() {
		return FragmentHelper.class.getName();
	}
	/**
	 * Disable Fragment transition animation.
	 */
	public static boolean sDisableTransitionAnimation = false;
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Set custom animations to transaction
	 * @param transaction transaction needs to set animations
	 * @param transition transition animations
	 */
	public static void setCustomAnimations(final FragmentTransaction transaction, FragmentTransitionInfo transition) {
		if(transition != null) {
			if(transition.enterAnimationId != -1 && transition.exitAnimationId != -1) {
				if(transition.popEnterAnimationId != -1 && transition.popExitAnimationId != -1) {	//Apply to Pop BackStack
					transaction.setCustomAnimations(transition.enterAnimationId, transition.exitAnimationId, transition.popEnterAnimationId, transition.popExitAnimationId);
				}
				else {	//Not Apply to Pop BackStack
					transaction.setCustomAnimations(transition.enterAnimationId, transition.exitAnimationId);	
				}
			}
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add fragment to Fragment Activity
	 * @param fragmentActivity Fragment Activity needs to add fragment
	 * @param fragment Fragment will be added
	 * @param viewGroupId Fragment container ID
	 * @param tag Null or empty string means that be not pushed into back stack, otherwise is pushed with specific tag name
	 * @param transition Transition animations
	 * @return <code>true</code> if added successfully, otherwise is <code>false</code>
	 */
	public static boolean addFragment(final FragmentActivity fragmentActivity, final Fragment fragment, int viewGroupId, String tag, FragmentTransitionInfo transition) {
		try {
			FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
			FragmentHelper.setCustomAnimations(transaction, transition);
			transaction.add(viewGroupId, fragment, tag);
			if(tag != null && !tag.isEmpty()) {
				transaction.addToBackStack(tag);
				AppState.sCanGoBack = true;
			}else{
				AppState.sCanGoBack = false;
			}
			transaction.commit();
			return true;
		}
		catch(Exception e) {
			Log.e(classTag(), e.getMessage());
			return false;
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add fragment to Fragment Activity by fragment class tag name.
	 * @param fragmentActivity Fragment Activity needs to add fragment
	 * @param viewGroupId Fragment container ID
	 * @param fragmentClassNameTag Full quality fragment class name
	 * @param addToBackStack <code>true</code> is not pushed into back stack, otherwise is pushed with specific <code>fragmentClassNameTag</code> tag name
	 * @param data Passed data 
	 * @param transition Transition animations
	 * @return <code>true</code> if added successfully, otherwise is <code>false</code>
	 */
	public static boolean addFragment(final FragmentActivity fragmentActivity, int viewGroupId, String fragmentClassNameTag, boolean addToBackStack, Bundle data, FragmentTransitionInfo transition) {
		Fragment fragment = Fragment.instantiate(fragmentActivity.getApplicationContext(), fragmentClassNameTag);
		if(data != null) {
			fragment.setArguments(data);
		}
		return FragmentHelper.addFragment(fragmentActivity, fragment, viewGroupId, addToBackStack ? fragmentClassNameTag : null, transition);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add child fragment to parent fragment
	 * @param parent Parent fragment
	 * @param child Child fragment
	 * @param viewGroupId Fragment container ID
	 * @param tag Null or empty string means that be not pushed into back stack, otherwise is pushed with specific tag name
	 * @param transition Transition animations
	 * @return <code>true</code> if added successfully, otherwise is <code>false</code>
	 */
	public static boolean addChildFragment(final Fragment parent, final Fragment child, int viewGroupId, String tag, FragmentTransitionInfo transition) {
		try {
			FragmentTransaction transaction = parent.getChildFragmentManager().beginTransaction();
			FragmentHelper.setCustomAnimations(transaction, transition);
			transaction.add(viewGroupId, child, tag);
			if(tag != null && !tag.isEmpty()) {
				transaction.addToBackStack(tag);
				AppState.sCanGoBack = true;
			}else{
				AppState.sCanGoBack = false;
			}
			transaction.commit();
			return true;
		}
		catch(Exception e) {
			Log.e(classTag(), e.getMessage());
			return false;
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add child fragment to parent fragment by child fragment class tag name
	 * @param parent Parent fragment
	 * @param viewGroupId Fragment container ID
	 * @param childFragmentClassNameTag Full quality child fragment class name
	 * @param addToBackStack <code>true</code> is not pushed into back stack, otherwise is pushed with specific <code>childFragmentClassNameTag</code> tag name
	 * @param data Passed data 
	 * @param transition Transition animations
	 * @return <code>true</code> if added successfully, otherwise is <code>false</code>
	 */
	public static boolean addChildFragment(final Fragment parent, int viewGroupId, String childFragmentClassNameTag, boolean addToBackStack, Bundle data, FragmentTransitionInfo transition) {
		Fragment child = Fragment.instantiate(parent.getActivity().getApplicationContext(), childFragmentClassNameTag);
		if(data != null) {
			child.setArguments(data);
		}
		return FragmentHelper.addChildFragment(parent, child, viewGroupId, addToBackStack ? childFragmentClassNameTag : null, transition);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Replace fragment from Fragment Activity of specific ViewGroup ID
	 * @param fragmentActivity Fragment Activity needs to replace fragment
	 * @param fragment Fragment will be replaced
	 * @param viewGroupId Fragment container ID
	 * @param tag Null or empty string means that be not pushed into back stack, otherwise is pushed with specific tag name
	 * @param transition Transition animations
	 * @return <code>true</code> if replaced successfully, otherwise is <code>false</code>
	 */
	public static boolean replaceFragment(final FragmentActivity fragmentActivity, final Fragment fragment, int viewGroupId, final String tag, FragmentTransitionInfo transition) {
		try {
			FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
			FragmentHelper.setCustomAnimations(transaction, transition);
			transaction.replace(viewGroupId, fragment, tag);
			if(tag != null && !tag.isEmpty()) {
				transaction.addToBackStack(tag);
				AppState.sCanGoBack = true;
			}else{
				AppState.sCanGoBack = false;
			}
			transaction.commit();
			return true;
		}
		catch(Exception e) {
			Log.e(classTag(), e.getMessage());
		}
		return false;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Replace fragment from Fragment Activity of specific ViewGroup ID by fragment class tag name.
	 * @param fragmentActivity Fragment Activity needs to replace fragment
	 * @param viewGroupId Fragment container ID
	 * @param fragmentClassNameTag Full quality fragment class name
	 * @param addToBackStack <code>true</code> is not pushed into back stack, otherwise is pushed with specific <code>fragmentClassNameTag</code> tag name
	 * @param data Passed data 
	 * @param transition Transition animations
	 * @return <code>true</code> if replaced successfully, otherwise is <code>false</code>
	 */
	public static boolean replaceFragment(final FragmentActivity fragmentActivity, int viewGroupId, String fragmentClassNameTag, boolean addToBackStack, Bundle data, FragmentTransitionInfo transition) {
		Fragment fragment = Fragment.instantiate(fragmentActivity.getApplicationContext(), fragmentClassNameTag);
		if(data != null) {
			fragment.setArguments(data);
		}
		return FragmentHelper.replaceFragment(fragmentActivity, fragment, viewGroupId, addToBackStack ? fragmentClassNameTag : null, transition);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Replace fragment from parent fragment of specific ViewGroup ID
	 * @param parent Parent fragment
	 * @param child Child fragment
	 * @param viewGroupId Fragment container ID
	 * @param tag Null or empty string means that be not pushed into back stack, otherwise is pushed with specific tag name
	 * @param transition Transition animations
	 * @return <code>true</code> if replaced successfully, otherwise is <code>false</code>
	 */
	public static boolean replaceChildFragment(final Fragment parent, final Fragment child, int viewGroupId, String tag, FragmentTransitionInfo transition) {
		try {
			FragmentTransaction transaction = parent.getChildFragmentManager().beginTransaction();
			FragmentHelper.setCustomAnimations(transaction, transition);
			transaction.replace(viewGroupId, child, tag);
			if(tag != null && !tag.isEmpty()) {
				transaction.addToBackStack(tag);
				AppState.sCanGoBack = true;
			}else{
				AppState.sCanGoBack = false;
			}
			transaction.commit();
			return true;
		}
		catch(Exception e) {
			Log.e(classTag(), e.getMessage());
			return false;
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Replace fragment from parent fragment of specific ViewGroup ID by child fragment class tag name
	 * @param parent Parent fragment
	 * @param viewGroupId Fragment container ID
	 * @param childFragmentClassNameTag Full quality child fragment class name
	 * @param addToBackStack <code>true</code> is not pushed into back stack, otherwise is pushed with specific <code>childFragmentClassNameTag</code> tag name
	 * @param data Passed data 
	 * @param transition Transition animations
	 * @return <code>true</code> if replaced successfully, otherwise is <code>false</code>
	 */
	public static boolean replaceChildFragment(final Fragment parent, int viewGroupId, String childFragmentClassNameTag, boolean addToBackStack, Bundle data, FragmentTransitionInfo transition) {
		Fragment child = Fragment.instantiate(parent.getActivity().getApplicationContext(), childFragmentClassNameTag);
		if(data != null) {
			child.setArguments(data);
		}
		return FragmentHelper.replaceChildFragment(parent, child, viewGroupId, addToBackStack ? childFragmentClassNameTag : null, transition);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Find fragment from Fragment Activity by tab name.
	 * @param fragmentActivity Fragment Activity needs to find fragment
	 * @param tag Tag of fragment when add or replace by transaction
	 * @return Found <code>Fragment</code> or <code>null</code> if not found
	 */
	public static Fragment findFragmentByTag(final FragmentActivity fragmentActivity, final String tag) {
		try {
			return fragmentActivity.getSupportFragmentManager().findFragmentByTag(tag);
		}
		catch(Exception e) {
			Log.e(classTag(), e.getMessage());
			return null;
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Find child fragment from parent fragment by tab name.
	 * @param parent Parent Fragment needs to find child fragment
	 * @param tag Tag of child fragment when add or replace to parent fragment by transaction
	 * @return Found <code>Fragment</code> or <code>null</code> if not found
	 */
	public static Fragment findChildFragmentByTag(final Fragment parent, final String tag) {
		try {
			return parent.getChildFragmentManager().findFragmentByTag(tag);
		}
		catch(Exception e) {
			Log.e(classTag(), e.getMessage());
			return null;
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear fragment back stack
	 * @param fragmentActivity
	 */
	public static void clearBackStack(final FragmentActivity fragmentActivity) {
		fragmentActivity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); 
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear children fragment back stack
	 * @param parent Parent Fragment
	 */
	public static void clearChildBackStack(final Fragment parent) {
		parent.getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); 
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear fragment back stack by Iterator
	 * @param fragmentActivity
	 */
	public static void clearBackStackByIterator(final FragmentActivity fragmentActivity) {
		FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager(); 
		int backStackCount = fragmentManager.getBackStackEntryCount();
		for (int i = 0; i < backStackCount; i++) {
		    int backStackId = fragmentManager.getBackStackEntryAt(i).getId(); // Get the back stack fragment id.
		    fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Clear children fragment back stack by Iterator
	 * @param parent Parent Fragment
	 */
	public static void clearChildrenBackStackByIterator(final Fragment parent) {
		FragmentManager fragmentManager = parent.getChildFragmentManager(); 
		int backStackCount = fragmentManager.getBackStackEntryCount();
		for (int i = 0; i < backStackCount; i++) {
			int backStackId = fragmentManager.getBackStackEntryAt(i).getId(); // Get the back stack fragment id.
			fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Pop back stack fragment
	 * @param fragmentActivity
	 */
	public static void popBackStack(final FragmentActivity fragmentActivity) {
		fragmentActivity.getSupportFragmentManager().popBackStack();
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Pop back stack fragment
	 * @param parent Parent Fragment
	 */
	public static void popBackStack(final Fragment parent) {
		parent.getChildFragmentManager().popBackStack();
	}
	//-------------------------------------------------------------------------------------------------------------------
}
