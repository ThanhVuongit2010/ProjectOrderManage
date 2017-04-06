package vn.app.android.ordermanager.order.v101.adapter;

import com.order.ordermanager.R;

import vn.app.android.ordermanager.collection.SortedArrayList;
import vn.app.android.ordermanager.mode.Function;
import vn.app.android.ordermanager.mode.TableOrder;
import vn.app.android.ordermanager.order.v101.adapter.V101CustomFunctionAdapter.ViewHolder;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class V101CustomItemTableOrder extends BaseAdapter {

	public static class ViewHolder{
		ImageView iconTableOrder;
		TextView txtTableOrderName;
		View layout;
		int position;
	}
	public static String TAG = V101CustomItemTableOrder.class.getName();
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected SortedArrayList<TableOrder> mDataSource = new SortedArrayList<TableOrder>();
	private int mSlected;
	
	public int getmSlected() {
		return mSlected;
	}
	public void setmSlected(int mSlected) {
		this.mSlected = mSlected;
	}
	public V101CustomItemTableOrder(Context context, SortedArrayList<TableOrder> dataSource){
		this.mContext = context;
		setDataSource(dataSource);
		mInflater = LayoutInflater.from(context);
	}
	/**
	 * set data source 
	 * @param dataSource
	 */
	public void setDataSource(SortedArrayList<TableOrder> dataSource){
		if(dataSource != null){
			mDataSource = dataSource;
		}
	}
	public void setDataSourceAndNotifyDataSetChange(SortedArrayList<TableOrder>dataSource){
		if(dataSource != null){
			mDataSource = dataSource;
		}
		if(this != null){
			notifyDataSetChanged();
		}
	}
	
	@Override
	public int getCount() {
		return mDataSource.size();
	}
	@Override
	public Object getItem(int position) {
		return mDataSource.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_table_order, null);
			viewHolder = new ViewHolder();
			viewHolder.position = position;
			viewHolder.iconTableOrder = (ImageView) convertView.findViewById(R.id.item_menu_img_function_icon);
			viewHolder.txtTableOrderName = (TextView) convertView.findViewById(R.id.item_menu_txt_function_name);
			viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.item_menu_ln_function_layout);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		TableOrder tableOrder = mDataSource.get(position);
		if(tableOrder != null){
			viewHolder.position = position;
			viewHolder.txtTableOrderName.setText(tableOrder.getmName());
			viewHolder.iconTableOrder.setImageResource((tableOrder.getIsUsing() ==0) ? R.drawable.icon_table : R.drawable.icon_table_using);
		}
		if(getmSlected() == position){
			viewHolder.layout.setBackgroundColor(Color.BLUE);
		}else{
			viewHolder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.menu_list_writing_left_background));
		}
		return convertView;
	}

}
