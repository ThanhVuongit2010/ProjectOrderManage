package vn.app.android.ordermanager.order.v101.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.order.ordermanager.R;

import vn.app.android.ordermanager.base.FragmentHelper;
import vn.app.android.ordermanager.collection.SortedArrayList;
import vn.app.android.ordermanager.custom.view.SupportedDrawerLayout;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.mode.Function;
import vn.app.android.ordermanager.order.v001.common.V001CommonConstant;
import vn.app.android.ordermanager.order.v101.adapter.V101CustomFunctionAdapter;
import vn.app.android.ordermanager.order.v201.V201MapFragment;
import vn.app.android.ordermanager.state.AppState;
import vn.app.android.ordermanager.util.DisplayUtil;
import vn.app.android.ordermanager.util.LogUtil;
import vn.app.android.ordermanager.util.TaskUtil;

/**
 * 
 * @author Mr Vuong
 *
 */
public class V101MainFragment extends Fragment{
	
	/**
	 * Log cat tag.
	 */
	public static final String TAG = V101MainFragment.class.getName();
	/**
	 * Width menu navigation
	 */
	protected int mCurrentDrawerWidth = 0;
	/**
	 * Display as TimeLine.
	 */
	public static final int TYPE_FEED = 1;
	/**
	 * Main layout fragment
	 */
	private View mFragmentLayout;
	/**
	 * Drawer layout using navigation
	 */
	private SupportedDrawerLayout mDrawerLayout;
	/**
	 * Content layout using add fragment
	 */
	private View mContentLayout;
	/**
	 * Menu left
	 */
	private View mMenuLeft;
	/**
	 * Menu right
	 */
	private View mMenuRight;
	/**
	 * Top navigation
	 */
	private View mTopNavigation;
	
	private View mContentFuctionLayout;

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
	public void onCreate(@Nullable Bundle savedInstanceState) {
		FragmentTransitionInfo transitionInfo = TaskUtil.getLeftToRightTransition();
		Bundle bundle = getArguments();
		AppState.startMainFragment = "V101MainFragment";
		if(V001CommonConstant.V001_CALL_FIRT_MAINFRAGMENT.equals(bundle.getString(V001CommonConstant.V001_CALL_FIRT_MAINFRAGMENT))){
			FragmentHelper.addChildFragment(this,new V101ListTableOrderFragment(),R.id.v101_fr_all_funtion,V101ListTableOrderFragment.TAG,transitionInfo);
			bundle.clear();
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		inflateMainLayout(inflater,container);
		getMandatoryViews();
		buildDrawerLayout();
		buildTopNavigation();
		buildSlidingMenu();
		return mFragmentLayout;
	}

	@Override
	public void onStop() {
		super.onStop();
		AppState.startMainFragment = "";
	}

	/**
	 * Inflate Main Layout
	 * 
	 * @param inflater
	 * @param container
	 */
	@SuppressLint("InflateParams")
	private void inflateMainLayout(LayoutInflater inflater, ViewGroup container) {
		mFragmentLayout = inflater.inflate(
				R.layout.v101_all_funtion_frame_fragment, container, false);
	}
	/**
	 * Get mandatory views. This should be called first after layout inflated.
	 */
	private void getMandatoryViews(){
		mDrawerLayout = (SupportedDrawerLayout) mFragmentLayout.findViewById(R.id.v101_all_funtion_layout);
		mContentLayout = mDrawerLayout.findViewById(R.id.v101_ln_detail_funtion_layout);
		mTopNavigation = mDrawerLayout.findViewById(R.id.v101_rl_product_top_navigation);
		mMenuLeft = mDrawerLayout.findViewById(R.id.v101_fr_left_menu);
		mMenuRight = mDrawerLayout.findViewById(R.id.v101_fr_right_menu);
		mContentFuctionLayout =  mDrawerLayout.findViewById(R.id.v101_fr_all_funtion);
	}
	/**
	 * Build Drawer Layout.
	 */
	private void buildDrawerLayout() {
		setDrawerLayoutListenter();
		setDrawerLayoutMargin();
		Drawable drawable = getResources().getDrawable(R.drawable.sh_menu_shadow); 
		mDrawerLayout.setDrawerShadow(drawable, Gravity.LEFT);
		mDrawerLayout.setDrawerShadow(drawable, Gravity.RIGHT);
		mDrawerLayout.setScrimColor(getResources().getColor(R.color.black_opaque_30));
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
	}
	/**
	 * Set DrawerLayout Listener.
	 */
	private void setDrawerLayoutListenter() {
		//Set drawer listener
		mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			//State Changed
			@Override
			public void onDrawerStateChanged(int newState) {	//STATE_IDLE, STATE_DRAGGING or STATE_SETTLING.
			}
			//Sliding
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				float offset = drawerView.getId() == R.id.v101_fr_left_menu ? slideOffset : -slideOffset;
				ViewHelper.setTranslationX(mContentLayout, offset * drawerView.getWidth());
				mDrawerLayout.bringChildToFront(drawerView);
				mDrawerLayout.requestLayout();

			}
			//Opened
			@Override
			public void onDrawerOpened(View drawerView) {
				mCurrentDrawerWidth = drawerView.getWidth();
				mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			}
			//Closed
			@Override
			public void onDrawerClosed(View drawerView) {
				mCurrentDrawerWidth = 0;
				mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//				KeyboardUtil.hideKeyboard(getActivity().getApplicationContext(), mAutoCompleteSearchExpand);
				//Set lock mode
				LogUtil.d(TAG, "DrawerView: (" + drawerView.getLeft() + ")");
				LogUtil.d(TAG, "ContentContainter: (" + mContentLayout.getLeft() + ")");
			}
		});
	}
	
	/**
	 * Set Drawer Layout Margin.
	 */
	private void setDrawerLayoutMargin() {
		//Get margin values
		int leftMenuMargin = (int) getResources().getDimension(R.dimen.h140);
		int rightMenuMargin = (int) getResources().getDimension(R.dimen.h140);
		//Get Screen Size
		Point screenSize = DisplayUtil.getDisplaySizeInPixel(getActivity());
		//Set left margin
		DrawerLayout.LayoutParams leftMenuParams = (DrawerLayout.LayoutParams) mMenuLeft.getLayoutParams();
//		leftMenuParams.width = 400;
		leftMenuParams.width = screenSize.x - leftMenuMargin;
		mMenuLeft.setLayoutParams(leftMenuParams);
		//Set right margin
		DrawerLayout.LayoutParams rightMenuParams = (DrawerLayout.LayoutParams) mMenuRight.getLayoutParams();
		rightMenuParams.width = screenSize.x - rightMenuMargin;
//		rightMenuParams.width = 200;
		mMenuRight.setLayoutParams(rightMenuParams);
	}
	/**
	 * Build Top Navigation.
	 */
	private void buildTopNavigation() {
		final Context context = getActivity().getApplicationContext();
		//Search Button
		View functionIcon = mTopNavigation.findViewById(R.id.v101_fr_function_icon);
		functionIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//Set lock mode
				mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
				mDrawerLayout.openDrawer(mMenuLeft);
			}
		});
		//Menu Button
		View menuIcon = mTopNavigation.findViewById(R.id.v101_fr_search_icon);
		menuIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
				mDrawerLayout.openDrawer(mMenuRight);
			}
		});
		
	}
	/**
	 * Build Sliding Menu.
	 */
	private void buildSlidingMenu() {
		buildSlidingMenuLeft();
		buildSlidingMenuRight();
	}
	/**
	 * Build Sliding Menu Left
	 */
	private void buildSlidingMenuLeft(){
		
	}
	/**
	 * Build Sliding Menu Right
	 */
	private void buildSlidingMenuRight(){
		
		final SortedArrayList<Function> listFunction = new SortedArrayList<Function>();
		
		Function function;
		function = new Function.Builder("V101", "Order").UrlImage("url1").build();
		listFunction.add(function);
		function = new Function.Builder("V201", "Map").UrlImage("url2").build();
		listFunction.add(function);
		function = new Function.Builder("V301", "Infomation Company").UrlImage("url3").build();
		listFunction.add(function);
		ListView lvFunction = (ListView)mMenuLeft.findViewById(R.id.menu_left_lv_all_funtion);
		
		final V101CustomFunctionAdapter adapter = new V101CustomFunctionAdapter(getActivity().getApplicationContext(), listFunction);
		adapter.setmSlected(0);
		lvFunction.setAdapter(adapter);
		
		lvFunction.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				adapter.setmSlected(position);
				adapter.notifyDataSetChanged();
				View customView =  parent.getChildAt(position);
				TextView txtName = (TextView)customView.findViewById(R.id.item_menu_txt_function_name);
				Toast.makeText(getActivity().getApplicationContext(), "Select Function "+txtName.getText() , Toast.LENGTH_SHORT).show();
				
				Function function = listFunction.get(position);
				String code = function.getmCode();
				String fragmentTag = null;
				if("V101".equals(code)){
					fragmentTag = V101ListTableOrderFragment.TAG;
					
				}else if("V201".equals(code)){
					fragmentTag = V201MapFragment.TAG;
				}else if("V301".equals(code)){
					fragmentTag = V101ListTableOrderFragment.TAG;
				}
				Fragment functionFragment = null;
				if("V101".equals(code)){
					functionFragment = new V101ListTableOrderFragment();

				}else if("V201".equals(code)){
					functionFragment = new V201MapFragment();
				}else if("V301".equals(code)){
					functionFragment = new V101ListTableOrderFragment();
				}
				mDrawerLayout.closeDrawers();
//				FragmentTransaction childFragmentManager = getChildFragmentManager().beginTransaction();
//				childFragmentManager.add(R.id.v101_fr_all_funtion,functionFragment,fragmentTag);
//				childFragmentManager.addToBackStack(null);
//				childFragmentManager.commit();
				FragmentTransitionInfo transitionInfo = TaskUtil.getLeftToRightTransition();
//				FragmentHelper.addChildFragment(new V101MainFragment(),R.id.v101_fr_all_funtion,fragmentTag, true, null,transitionInfo);
				FragmentHelper.replaceChildFragment(new V101MainFragment(),R.id.v101_fr_all_funtion,fragmentTag,true,null,transitionInfo);

//				FragmentUtil.replaceFragmentSafely(getActivity(), R.id.v101_fr_all_funtion, fragmentTag, false, null);
				
			}
		});
		
		
	}
}
