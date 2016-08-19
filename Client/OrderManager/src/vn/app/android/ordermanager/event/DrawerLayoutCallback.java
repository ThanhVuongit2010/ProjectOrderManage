package vn.app.android.ordermanager.event;

import android.view.View;

/**
 * DrawerLayoutCallback
 * @author thaop
 */
public abstract class DrawerLayoutCallback {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * onDrawerClosed
	 * @param drawerView
	 */
	public void onDrawerClosed(View drawerView) {
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * onDrawerStateChanged
	 * @param newSate
	 */
	public void onDrawerStateChanged(int newState) {
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * onDrawerSlide
	 * @param drawerView
	 * @param slideOffset
	 */
	public void onDrawerSlide(View drawerView, float slideOffset) {
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * onDrawerOpened
	 * @param drawerView
	 */
	public void onDrawerOpened(View drawerView) {
	}
	//--------------------------------------------------------------------------------------------------------------------
}
