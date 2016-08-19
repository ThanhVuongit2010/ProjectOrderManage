package vn.app.android.ordermanager.order.v100.fragment;

import vn.app.android.ordermanager.state.AppState;
import vn.app.android.ordermanager.util.FragmentUtil;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.order.ordermanager.R;
/**
 * @author Mr Vuong
 *
 */
public class BeginScreenbFragment extends Fragment {
	/**
	 * Log cat tag.
	 */
	public static final String TAG = BeginScreenbFragment.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Slash screen duration in milliseconds. Default is 0.5s (500 milliseconds).
	 */
	public static final int SLASH_DURATION = 500;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Fragment Layout
	 */
	private View mFragmentLayout;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get fragment layout
	 */
	public View getFragmentLayout() {
		return mFragmentLayout;
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getMainLayout(inflater, container);
		getMandatoryViews();
		//Root layout
		return mFragmentLayout;
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public void onStart() {
		AppState.getInstance().putCurrentMainFragmentTag(TAG);
//		checkAndShowSuitableScreen();
		super.onStart();
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public void onPause() {
//		AppState.getInstance().cancelAllRunningAsyncRestClients(true);
		super.onPause();
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		if (FragmentUtil.sDisableTransitionAnimation) {
			Animation animation = new Animation() {};
			animation.setDuration(0);
			return animation;
		}
		return super.onCreateAnimation(transit, enter, nextAnim);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Main Layout.
	 * @param inflater
	 * @param container
	 */
	private void getMainLayout(LayoutInflater inflater, ViewGroup container) {
		mFragmentLayout = inflater.inflate(R.layout.v100_login_begin_screen_fragment, container, false);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get mandatory views. This should be called first after layout inflated.
	 */
	public void getMandatoryViews() {
	}
	
}
