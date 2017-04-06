package vn.app.android.ordermanager.order.v101.fragment;

import vn.app.android.ordermanager.collection.SortedArrayList;
import vn.app.android.ordermanager.mode.TableOrder;
import vn.app.android.ordermanager.order.v101.adapter.V101CustomItemTableOrder;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.order.ordermanager.R;

public class V101ListTableOrderFragment extends Fragment{
	
	public static String TAG = V101ListTableOrderFragment.class.getName();
	private View mFragmentLayout;
	private GridView gvTableOrder;
	
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
		getMandatoryViews();
		return mFragmentLayout;
	}
	@SuppressWarnings("unused")
	private void initMainLayout(LayoutInflater inflater, ViewGroup container){
		mFragmentLayout = inflater.inflate(R.layout.v101_list_table_order_fragment, container, false);
	}
	private void getMandatoryViews(){
		gvTableOrder = (GridView) mFragmentLayout.findViewById(R.id.v101_gv_list_table);
	}
	private void buidListTableOrder(){
		SortedArrayList<TableOrder> listTableOrder = new SortedArrayList<TableOrder>();
		TableOrder tableOrder;
//		tableOrder = new TableOrder.Builder("001", "001", 0);
		
		V101CustomItemTableOrder adapter = new V101CustomItemTableOrder(getActivity().getApplicationContext(), listTableOrder);
	}
}
