package vn.app.android.ordermanager.custom.view;

import vn.app.android.ordermanager.event.OnSwipeTouchListener;
import vn.app.android.ordermanager.util.LogUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;

/**
 * Support Drawer Layouto
 * @author Mr Vuong
 */
@SuppressLint({ "ClickableViewAccessibility", "RtlHardcoded" })
public class SupportedDrawerLayout extends DrawerLayout {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Log cat tag.
	 */
	public static final String TAG = SupportedDrawerLayout.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	public SupportedDrawerLayout(Context context) {
		super(context);
	}
	//--------------------------------------------------------------------------------------------------------------------	
	public SupportedDrawerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//--------------------------------------------------------------------------------------------------------------------
	public SupportedDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		LogUtil.d(TAG, "KeyCode: " + keyCode);
		LogUtil.d(TAG, "IsDrawerOpenLeft: " + isDrawerOpen(Gravity.LEFT));
		LogUtil.d(TAG, "IsDrawerOpenRight: " + isDrawerOpen(Gravity.RIGHT));
		return super.onKeyDown(keyCode, event);
	}
	//--------------------------------------------------------------------------------------------------------------------
	public boolean isOpened() {
		return isDrawerOpen(Gravity.LEFT) || isDrawerOpen(Gravity.RIGHT);
	}
	//--------------------------------------------------------------------------------------------------------------------
	protected OnSwipeTouchListener getOnTouchListener(Context context) {
		return new OnSwipeTouchListener(context) {
			@Override
			public void onSwipeLeft() {
				LogUtil.d(TAG, "Swipe Left");
				closeDrawer(Gravity.LEFT);
			}
			@Override
			public void onSwipeRight() {
				LogUtil.d(TAG, "Swipe Right");
			}
		};
	}
	//--------------------------------------------------------------------------------------------------------------------
}
