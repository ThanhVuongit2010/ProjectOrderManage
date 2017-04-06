package vn.app.android.ordermanager.order.v201;

import vn.app.android.ordermanager.order.v101.fragment.V101ListTableOrderFragment;

import com.order.ordermanager.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class V201MapFragment extends Fragment{
	public static String TAG = V201MapFragment.class.getName();
	private View mFragmentLayout;
	
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initMainLayout(inflater,container);
		return mFragmentLayout;
	}
	@SuppressWarnings("unused")
	private void initMainLayout(LayoutInflater inflater, ViewGroup container){
		mFragmentLayout = inflater.inflate(R.layout.v201_map_fragment,container, false);
	}
}
